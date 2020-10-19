<template>
    <v-container>

        <v-row class="justify-center mt-5">
            <v-col md="5">
                <v-text-field
                        type="text"
                        v-model="search"
                        outlined
                        label="Search by title"
                        clearable
                />
            </v-col>

        </v-row>
        <freelancer-row v-for="advert in filteredAdverts"
                    :key="advert.id"
                    :advert="advert"
                    :editAdvert="editAdvert"
        />


    </v-container>
</template>

<script>
import { mapGetters } from 'vuex'
import AdvertRow from '../components/adverts/AdvertRow.vue'
import store from '../store/store'
import FreelancerRow from '../components/freelancer/FreelancerRow.vue'

export default {
    components: {
        AdvertRow,
        FreelancerRow
    },
    data() {
        return {
            advert: null,
            search: ''
        }
    },
    computed: {
        ...mapGetters(['sortedAdverts']),
        filteredAdverts() {
            return store.getters.sortedAdverts.filter((advert) => {
                return advert.title.toLowerCase().match(this.search.toLowerCase()) && (advert.job === true)
            })
        }
    },
    methods: {
        editAdvert(advert) {
            this.advert = advert
        }
    }

}
</script>

<style scoped>

</style>