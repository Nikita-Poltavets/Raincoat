<template>
    <v-card class="my-2 mx-16">
        <v-card-title @click="showAdvertDetails(advert.id)">
            <div>
                <v-avatar
                        v-if="advert.author && advert.author.userpic"
                        size="48px"
                >
                    <img
                            :src="advert.author.userpic"
                            :alt="advert.author.name"
                    >
                </v-avatar>

                <v-avatar
                        v-else
                        size="48px"
                        color="indigo"
                >
                    <v-icon dark>
                        mdi-account-circle
                    </v-icon>
                </v-avatar>
                <span v-if="advert.job === true" class="pl-3">{{ authorName }}</span>
            </div>
            <div class="pt-3">
                <b><span>{{ advert.title }}</span></b>
            </div>
        </v-card-title>
        <v-card-text>
            <div class="text-h6"> {{ advert.details }} </div>

            <div>{{ advert.description }}</div>
        </v-card-text>
        <v-card-actions>
            <v-btn value="Edit" @click="edit">Edit</v-btn>
            <v-btn depressed color="error" @click="del">
                Delete
            </v-btn>
        </v-card-actions>
        <comment-list
                :comments="advert.comments"
                :advert-id="advert.id"
        >

        </comment-list>
    </v-card>
</template>

<script>

import { mapActions } from 'vuex'
import CommentList from './comment/CommentList.vue'

export default {
    components: { CommentList },
    props: ['advert', 'editAdvert'],
    computed: {
        authorName() {
            return this.advert.author ? this.advert.author.name : 'unknown'
        }
    },
    methods: {
        ...mapActions(['removeAdvertAction']),
        edit(){
            this.editAdvert(this.advert)
        },
        del(){
            this.removeAdvertAction(this.advert)
        },
        showAdvertDetails(advertId){
            this.$router.push({ path: `advert-details/${advertId}` })
        },
    }
}
</script>

<style scoped>
b span:hover {
    background: #26C6DA;
    color: white;
    cursor: pointer;
}
</style>