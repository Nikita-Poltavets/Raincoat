<template>
    <v-layout align-content-space-around justify-start column>
        <advert-form :adverts="adverts" :advertAttr="advert"/>
        <advert-row v-for="advert in sortedAdverts"
                    :key="advert.id"
                    :advert="advert"
                    :editAdvert="editAdvert"
                    :deleteAdvert="deleteAdvert"
                    :adverts="adverts"/>

    </v-layout>
</template>

<script>

import AdvertRow from 'components/adverts/AdvertRow.vue'
import AdvertForm from 'components/adverts/AdvertForm.vue'
import advertApi from 'api/adverts'

export default {
    props: ['adverts'],
    components: {
        AdvertRow,
        AdvertForm
    },
    data() {
        return {
            advert: null
        }
    },
    computed: {
        sortedAdverts(){
            return this.adverts.sort((a, b) => -(a.id - b.id))
        }
    },
    methods: {
        editAdvert(advert) {
            this.advert = advert
        },
        deleteAdvert(advert) {
            advertApi.remove(advert.id).then(result => {
                if (result.ok) {
                    this.adverts.splice(this.adverts.indexOf(this.advert), 1)
                }
            })
        }

    }
}
</script>

<style scoped>

</style>