import Vue from 'vue'
import VueRouter from 'vue-router'
import AdvertsList from 'pages/AdvertList.vue'
import Auth from 'pages/Auth.vue'
import Profile from 'pages/Profile.vue'

Vue.use(VueRouter)

const routes = [
    { path: '/', component: AdvertsList },
    { path: '/auth', component: Auth },
    { path: '/profile', component: Profile },
    { path: '*', component: AdvertsList },
]

export default new VueRouter({
    mode: 'history',
    routes
})