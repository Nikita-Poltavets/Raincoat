import Vue from 'vue'

const adverts = Vue.resource('/advert{/id}')

export default {
    add: advert => adverts.save({}, advert),
    update: advert => adverts.update({id: advert.id}, advert),
    remove: id => adverts.remove({id}),
    getAdvert: id => Vue.http.get(`/advert/details/${id}`),
    page: page => Vue.http.get('/advert', {params: { page }}),
}