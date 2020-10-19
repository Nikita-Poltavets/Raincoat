<template>
    <v-layout justify-center>
        <v-card class="my-2 mx-16" width="1000">

            <v-card-title>

                <advert-link :advert="advert">
                </advert-link>


            </v-card-title>

            <v-card-text>

                <div class="text-h6"> {{ advert.details }} </div>

                <div>{{ advert.description }}</div>
            </v-card-text>

            <v-layout class="mr-2" justify-end>{{advert.creationDate}}</v-layout>

            <v-card-actions v-if="$route.path === `/user-adverts/${advert.author.id}`">
                <v-btn value="Edit" @click="showEditForm(advert.id)" small flat rounded>Edit</v-btn>
                <v-btn icon @click="del" small>
                    <v-icon>delete</v-icon>
                </v-btn>
            </v-card-actions>

        </v-card>
    </v-layout>
</template>

<script>

import { mapActions } from 'vuex'
import CommentList from '../comment/CommentList.vue'
import AdvertLink from '../AdvertLink.vue';
import UserLink from '../UserLink.vue';

export default {
    components: {UserLink, AdvertLink, CommentList },
    props: ['advert', 'editAdvert'],

    methods: {
        ...mapActions(['removeAdvertAction']),
        showEditForm(advertId){
            this.$router.push(`/advert-edit/${advertId}`)
        },
        del(){
            this.removeAdvertAction(this.advert)
        },
    }
}
</script>

<style scoped>

</style>