import Vue from 'vue'
import VueRouter from 'vue-router'
import AdvertsList from '../pages/AdvertList.vue'
import Auth from '../pages/Auth.vue'
import Profile from '../pages/Profile.vue'
import AdvertForm from '../pages/AdvertForm.vue'
import AdvertDetails from '../pages/AdvertDetails.vue'
import FreelancersList from '../pages/FreelancersList.vue'
import CommentDiscussion from '../pages/CommentDiscussion.vue'
import UserAdverts from '../pages/UserAdverts.vue'
import UserFreelanceAdverts from '../pages/UserFreelanceAdverts.vue'
import AdvertEditForm from '../pages/AdvertEditForm.vue'

Vue.use(VueRouter)

const routes = [
    { path: '/', component: AdvertsList },
    { path: '/freelancers', component: FreelancersList },
    { path: '/auth', component: Auth },
    { path: '/user/:id?', component: Profile },
    { path: '/user-adverts/:id', component: UserAdverts },
    { path: '/user-freelance-adverts/:id', component: UserFreelanceAdverts },
    { path: '/discussion/:id', component: CommentDiscussion },
    { path: '/advert-details/:id', component: AdvertDetails },
    { path: '/postNewAd', component: AdvertForm },
    { path: '/advert-edit/:id', component: AdvertEditForm },
    { path: '*', component: AdvertsList },
]

export default new VueRouter({
    mode: 'history',
    routes
})