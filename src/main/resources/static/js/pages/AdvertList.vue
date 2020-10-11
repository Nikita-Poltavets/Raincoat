<template>
    <v-container>
        <v-layout align-content-space-around justify-start column>
            <advert-form :advertAttr="advert"/>
            <advert-row v-for="advert in sortedAdverts"
                        :key="advert.id"
                        :advert="advert"
                        :editAdvert="editAdvert"/>

        </v-layout>
    </v-container>
</template>

<script>
import { mapGetters } from 'vuex'
import AdvertRow from 'components/adverts/AdvertRow.vue'
import AdvertForm from 'components/adverts/AdvertForm.vue'

export default {
    components: {
        AdvertRow,
        AdvertForm
    },
    data() {
        return {
            advert: null
        }
    },
    computed: mapGetters(['sortedAdverts']),
    methods: {
        editAdvert(advert) {
            this.advert = advert
        }
    }
}
</script>

<style scoped>

</style>