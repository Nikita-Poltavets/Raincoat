<template>
    <v-container>
        <v-layout justify-space-around>
            <v-flex :xs6="!$vuetify.breakpoint.xsOnly">
                <div class="title mb-3">User profile</div>
                <v-layout row justify-space-between>
                    <v-flex class="px-1">
                        <v-img :src="profile.userpic"></v-img>
                    </v-flex>
                    <v-flex class="px-1">
                        <v-layout column>
                            <v-flex>{{ profile.name }}</v-flex>
                            <v-flex>{{ profile.locale }}</v-flex>
                            <v-flex>{{ profile.gender }}</v-flex>
                            <v-flex>{{ profile.lastVisit }}</v-flex>
                        </v-layout>
                    </v-flex>
                </v-layout>
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>

import profileApi from '../api/profile'

export default {

    name: 'Profile',
    data(){
        return{
            profile: {}
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
        }
    },
    beforeMount() {
        this.updateProfile()
    }
}
</script>
<!-- Так как стоит scoped, все стили применяются только к текущей страничке-->
<style scoped>
   img {
       max-width: 100%;
       height: auto;
   }
</style>