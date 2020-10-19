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
                        >
                            Task
                        </v-btn>

                        <v-btn
                                class="ml-16 mt-3"
                                width="150"
                                color="info"
                                :disabled="$route.path === `/discussion/${advert.id}`"
                                @click="showDiscussion(advert.id)"
                        >
                            Discussion
                        </v-btn>
                    </v-card>
                </v-col>

            </v-row>
        </v-layout>

        <v-layout justify-center>
            <v-card class="pa-4 mt-10" width="1200">
                <v-row>
                    <v-col>
                        <v-img :src="advert.author.userpic" width="200"></v-img>
                    </v-col>

                    <v-col cols="4" class="text-h5">
                        <u>{{ advert.title }}</u>
                    </v-col>

                    <v-col cols="4" class="ml-16">
                        {{formatDate(advert.creationDate)}}
                    </v-col>

                    <v-col
                            cols="9"
                            class="mr-6 text-h5 blue--text"
                            @click="showAuthorProfile(advert.author.id)"
                    >
                        <div class="hoverName"><u>{{ advert.author.name }}</u></div>
                    </v-col>
                </v-row>

                <v-row>
                    <v-col class="ml-1 text-h6">
                        <i>{{advert.details}}</i>
                    </v-col>
                </v-row>

                <v-row>
                    <v-col class="ml-1">
                        {{advert.description}}
                    </v-col>
                </v-row>

            </v-card>
        </v-layout>


    </v-container>

</template>

<script>


import advertApi from '../api/adverts'

export default {

    name: 'AdvertDetails',
    data(){
        return{
            advert: {}
        }
    },
    watch: {
        '$route'(){
            this.updateAdvert()
        }
    },
    methods: {
        async updateAdvert(){
            const id = this.$route.params.id

            const data = await advertApi.getAdvert(id)
            this.advert = await data.json()

            this.$forceUpdate()
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


            return `Created ${day} ${monthNames[monthIndex]} ${year} in ${hour}:${minute}`
        },
        showDiscussion(advertId){
            this.$router.push(`/discussion/${advertId}`)
        },
        showAuthorProfile(advertAuthorId){
            this.$router.push(`/user/${advertAuthorId}`)
        }
    },
    beforeMount() {
        this.updateAdvert()
    }
}
</script>

<style scoped>
.hoverName:hover{
    cursor: pointer;
    color: #05de05;
}
</style>