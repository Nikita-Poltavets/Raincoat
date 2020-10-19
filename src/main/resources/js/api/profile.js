import Vue from 'vue'

const profile = Vue.resource('/profile{/id}')

export default {
    get: id => profile.get({id}),
    advertList: userId => Vue.http.get(`/profile/adverts/${userId}`),
}