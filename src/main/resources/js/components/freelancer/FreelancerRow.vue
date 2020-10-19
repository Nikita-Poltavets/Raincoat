<template>

    <v-layout justify-center>
        <v-card class="my-2 mx-16" width="1200">
            <v-layout row class="mx-5 my-4">
                <v-row no-gutters>

                    <v-col>
                        <v-flex>
                            <v-img :src="advert.author.userpic" width="164"></v-img>
                        </v-flex>
                    </v-col>


                    <v-col cols="10">
                        <v-flex class="text-h5 blue--text">
                            <div
                                    @click="showAuthorProfile(advert.author.id)"
                                    class="hoverName"
                            >
                                {{advert.author.name}}</div>


                        </v-flex>
                        <v-flex class="mt-1">Last Visit: {{formatDate(advert.author.lastVisit)}}</v-flex>

                        <v-flex class="mt-1 text-h6">
                            <advert-link :advert="advert">
                            </advert-link>
                        </v-flex>
                    </v-col>
                </v-row>


            </v-layout>

            <v-layout column class="ml-5">

                <v-flex>
                    <div class="text-h6"> {{ advert.details }} </div>
                </v-flex>

                <v-flex>
                    <div>{{ advert.description }}</div>
                </v-flex>

                <v-flex>
                    <v-layout class="mr-2" justify-end>Advert Created: {{formatDate(advert.creationDate)}}</v-layout>
                </v-flex>

            </v-layout>

            <v-card-actions v-if="$route.path === `/user-freelance-adverts/${advert.author.id}`">
                <v-btn value="Edit" @click="showEditForm(advert.id)" flat rounded>Edit</v-btn>
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
    name: "FreelancerRow",
    components: { UserLink, AdvertLink, CommentList },
    props: ['advert'],

    methods: {
        ...mapActions(['removeAdvertAction']),
        showEditForm(advertId){
            this.$router.push(`/advert-edit/${advertId}`)
        },
        del(){
            this.removeAdvertAction(this.advert)
        },
        showAuthorProfile(advertAuthorId){
            this.$router.push(`/user/${advertAuthorId}`)
        },
        formatDate(userDOB) {
            const dob = new Date(userDOB);

            const monthNames = [
                'January', 'February', 'March', 'April', 'May', 'June', 'July',
                'August', 'September', 'October', 'November', 'December'
            ];

            const day = dob.getDate()
            const monthIndex = dob.getMonth()
            const year = dob.getFullYear()
            const hour = dob.getHours()
            const minute = dob.getMinutes()


            return `${day} ${monthNames[monthIndex]} ${year} in ${hour}:${minute}`
        }
    }
}
</script>

<style scoped>
.hoverName:hover{
    cursor: pointer;
    color: #05de05;
}
</style>