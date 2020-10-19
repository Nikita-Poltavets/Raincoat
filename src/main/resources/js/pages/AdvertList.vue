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


            <advert-row v-for="advert in filteredAdverts"
                        :key="advert.id"
                        :advert="advert"
                        :editAdvert="editAdvert"
            />
            <lazy-loader></lazy-loader>

        </v-container>
</template>

<script>
import { mapGetters } from 'vuex'
import AdvertRow from '../components/adverts/AdvertRow.vue'
import store from '../store/store';
import LazyLoader from '../components/LazyLoader.vue';


export default {
    components: {
        LazyLoader,
        AdvertRow
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
                return advert.title.toLowerCase().match(this.search.toLowerCase()) && (advert.job === false)
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