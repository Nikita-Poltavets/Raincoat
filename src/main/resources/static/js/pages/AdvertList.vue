<template>
    <v-container>
        <v-layout align-content-space-around justify-start column>

            <v-row class="ml-14">
                <v-col md="2">
                    <v-text-field
                            type="text"
                            v-model="search"
                            outlined
                            label="Search by title"
                            clearable
                    />
                </v-col>

                <v-col>
                    <v-btn class="mx-3 mt-2"
                           flat
                           @click="showFreelancerList">
                        All Freelancers
                    </v-btn>
                </v-col>
            </v-row>
            <advert-row v-for="advert in filteredAdverts"
                        :key="advert.id"
                        :advert="advert"
                        :editAdvert="editAdvert"
            />
            <lazy-loader></lazy-loader>

        </v-layout>
    </v-container>
</template>

<script>
import { mapGetters } from 'vuex'
import AdvertRow from 'components/adverts/AdvertRow.vue'
import store from 'store/store';
import LazyLoader from 'components/adverts/LazyLoader.vue';


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
        },
        showFreelancerList(){
            this.$router.push('/freelancers')
        },
    }
}
</script>

<style scoped>

</style>