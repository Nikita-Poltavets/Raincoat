import Vue from 'vue'

const adverts = Vue.resource('api/advert{/id}')

export default {
    add: advert => adverts.save({}, advert),
    update: advert => adverts.update({id: advert.id}, advert),
    remove: id => adverts.remove({id}),
    page: page => Vue.http.get('api/advert', {params: { page }}),
}