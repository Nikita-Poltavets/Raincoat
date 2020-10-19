<template>
    <v-container>
        <v-layout>
            <v-row>
                <v-col offset="3">
                    <v-card class="ml-16" width="430" height="60">
                        <v-btn
                                class="ml-8 mt-3"
                                width="150"
                                color="info"
                                :disabled="$route.path === `/advert-details/${advert.id}`"
                                @click="showAdvertDetails(advert.id)"
                        >
                            Task
                        </v-btn>

                        <v-btn
                                class="ml-16 mt-3"
                                width="150"
                                color="info"
                                :disabled="$route.path === `/discussion/${advert.id}`"
                        >
                            Discussion
                        </v-btn>
                    </v-card>
                </v-col>

            </v-row>
        </v-layout>

        <comment-list
                :comments="advert.comments"
                :advert-id="advert.id"
        ></comment-list>
    </v-container>

</template>

<script>

import CommentList from '../components/comment/CommentList.vue'

import advertApi from '../api/adverts'

export default {
    name: "CommentDiscussion",
    components: {
        CommentList
    },
    data(){
        return{
            advert: {}
        }
    },
    watch: {
        '$route'(){
            this.updateDiscussion()
        }
    },
    methods: {
        async updateDiscussion(){
            const id = this.$route.params.id

            const data = await advertApi.getAdvert(id)
            this.advert = await data.json()

            this.$forceUpdate()
        },
        showAdvertDetails(advertId){
            this.$router.push(`/advert-details/${advertId}`)
        }
    },
    beforeMount() {
        this.updateDiscussion()
    }
}
</script>

<style scoped>

</style>