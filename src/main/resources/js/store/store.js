import Vue from 'vue'
import Vuex from 'vuex'
import advertApi from 'api/adverts'
import commentApi from 'api/comment'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        adverts,
        profile,
        ...frontendData
    },
    getters: {
        sortedAdverts: state => (state.adverts || []).sort((a, b) => -(a.id - b.id))
    },
    mutations: {
        addAdvertMutation(state, advert) {
            state.adverts = [
                ...state.adverts,
                advert
            ]
        },
        updateAdvertMutation(state, advert) {
            const updateIndex = state.adverts.findIndex(item => item.id === advert.id)

            state.adverts = [
                ...state.adverts.slice(0, updateIndex),
                advert,
                ...state.adverts.slice(updateIndex + 1)
            ]

        },
        removeAdvertMutation(state, advert) {
            const deletionIndex = state.adverts.findIndex(item => item.id === advert.id)

            if(deletionIndex > -1) {
                state.adverts = [
                    ...state.adverts.slice(0, deletionIndex),
                    ...state.adverts.slice(deletionIndex + 1)
                ]
            }
        },
        addCommentMutation(state, comment) {
            const updateIndex = state.adverts.findIndex(item => item.id === comment.advert.id)
            const advert = state.adverts[updateIndex]

            if(!advert.comments.find(it => it.id === comment.id)) {
                state.adverts = [
                    ...state.adverts.slice(0, updateIndex),
                    {
                        ...advert,
                        comments: [
                            ...advert.comments,
                            comment
                        ]

                    },
                    ...state.adverts.slice(updateIndex + 1)
                ]
            }
        },
        addAdvertPageMutation(state, adverts){
            const targetAdverts = state.adverts
                .concat(adverts)
                .reduce((res, val) => { // Делаем map, чтобы избавиться от дубликатов
                    res[val.id] = val
                    return res
                }, {})

            state.adverts = Object.values(targetAdverts)
        },
        updateTotalPagesMutation(state, totalPages){
            state.totalPages = totalPages;
        },
        updateCurrentPageMutation(state, currentPage){
            state.currentPage = currentPage;
        }
    },
    actions: {
        async addAdvertAction({commit, state}, advert){
            const result = await advertApi.add(advert)
            const data = await result.json()
            const index = state.adverts.findIndex(item => item.id === data.id)

            if(index > -1){
                commit('updateAdvertMutation', data)
            } else {
                commit('addAdvertMutation', data)
            }
        },
        async updateAdvertAction({commit}, advert){
            const result = await advertApi.update(advert)
            const data = await result.json()

            commit('updateAdvertMutation', data)
        },
        async removeAdvertAction({commit}, advert){
            const result = await advertApi.remove(advert.id)

            if (result.ok) {
                commit('removeAdvertMutation', advert)
            }
        },
        async addCommentAction({commit, state}, comment){
            const response = await commentApi.add(comment) //Отправляем на сервер наш коммент.
            const data = await response.json() //Получаем данные, которые пришли с сервера

            commit('addCommentMutation', data) //Отправляем вышенаписанное в мутацию
        },
        async loadPageAction({commit, state}){
            const response = await advertApi.page(state.currentPage + 1)
            const data = await response.json()

            commit('addAdvertPageMutation', data.adverts)
            commit('updateTotalPagesMutation', data.totalPages)
            commit('updateCurrentPageMutation', Math.min(data.currentPage, data.totalPages - 1))
        }
    }
})