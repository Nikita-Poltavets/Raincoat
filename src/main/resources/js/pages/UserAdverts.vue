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
        />
        <lazy-loader></lazy-loader>

    </v-container>
</template>

<script>
import profileApi from '../api/profile'
import {mapGetters} from 'vuex'
import LazyLoader from '../components/LazyLoader.vue'
import AdvertRow from '../components/adverts/AdvertRow.vue'


export default {
    name: "UserAdverts",
    components: {
        LazyLoader,
        AdvertRow
    },
    data(){
        return{
            profile: {},
            search: '',
            userAdverts: []
        }
    },
    watch: {
        '$route'(){
            this.updateProfile()
        }
    },
    methods: {
        async updateProfile(){
            const id = this.$route.params.id || this.$store.state.profile.id

            const data = await profileApi.get(id)
            this.profile = await data.json()

            this.$forceUpdate()
        },
        sortedUserAdverts(){
            return this.userAdverts.sort((a, b) => -(a.id - b.id))
        }

    },
    computed: {
        ...mapGetters(['sortedAdverts']),
        filteredAdverts() {
            return this.sortedUserAdverts().filter((advert) => {
                return advert.title.toLowerCase().match(this.search.toLowerCase()) && (advert.job === false)
            })
        }
    },
    async beforeMount() {
        await this.updateProfile()
        const resp = await profileApi.advertList(this.$store.state.profile.id)
        this.userAdverts = await resp.json()
    }
}
</script>

<style scoped>

</style>