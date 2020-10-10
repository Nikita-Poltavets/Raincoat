import Vue from 'vue'
import Vuex from 'vuex'
import advertApi from 'api/adverts'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        adverts: frontendData.adverts,
        profile: frontendData.profile
    },
    getters: {
        sortedAdverts: state => state.adverts.sort((a, b) => -(a.id - b.id))
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
    }
})