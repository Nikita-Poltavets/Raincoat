<template>
    <v-app>
        <v-app-bar app>
            <v-toolbar-title class="blue--text text-h4 pl-16">Raincoat</v-toolbar-title>
            <v-btn class="mx-3"
                   flat
                   :disabled="$route.path === '/'"
                   @click="showAdverts">
                Job
            </v-btn>
            <v-btn class="mx-3"
                   flat
                   :disabled="$route.path === '/freelancers'"
                   @click="showFreelancers">
                Freelancers
            </v-btn>
            <v-btn flat
                   v-if="profile"
                   :disabled="$route.path === '/postNewAd'"
                   @click="showAdvertForm">
                Post New Advert
            </v-btn>

            <v-spacer></v-spacer>

            <div class="text-center mr-5" v-if="profile">
                <v-menu offset-y>
                    <template v-slot:activator="{ on, attrs }">
                        <v-btn
                                color="primary"
                                dark
                                v-bind="attrs"
                                v-on="on"
                        >
                            My Offers
                        </v-btn>
                    </template>
                    <v-list>
                        <v-list-item
                                v-for="(item, index) in items"
                                :key="index"
                        >
                            <v-list-item-title @click="showUserAdverts(item, profile.id)">{{ item }}</v-list-item-title>
                        </v-list-item>
                    </v-list>
                </v-menu>
            </div>

            <v-btn flat
                   v-if="!profile"
                   :disabled="$route.path === '/auth'"
                   @click="showLoginForm">
                Login
            </v-btn>

            <div class="mr-16">
            <v-btn flat
                   v-if="profile"
                   :disabled="$route.path === '/user'"
                   @click="showProfile">
                {{ profile.name }}
            </v-btn>

            <v-btn v-if="profile" icon href="/logout">
                <v-icon>exit_to_app</v-icon>
            </v-btn>
            </div>

        </v-app-bar>
        <v-main>
            <router-view></router-view>
        </v-main>


    </v-app>
</template>

<script>
import { mapState, mapMutations } from 'vuex'
import { addHandler } from '../util/ws'


export default {
    computed: mapState(['profile']),
    data() {
        return {
            items: ['Advert Offer', 'Freelance Offer']
        }
    },
    methods: {
        ...mapMutations([
            'addAdvertMutation',
            'updateAdvertMutation',
            'removeAdvertMutation',
            'addCommentMutation'
        ]),
        showAdverts(){
            this.$router.push('/')
        },
        showFreelancers(){
            this.$router.push('/freelancers')
        },
        showProfile(){
            this.$router.push('/user')
        },
        showAdvertForm(){
            this.$router.push('/postNewAd')
        },
        showLoginForm(){
            this.$router.push('/auth')
        },
        showUserAdverts(item, userId){
            if(item === 'Advert Offer'){
                this.$router.push(`/user-adverts/${userId}`)
            } else {
                this.$router.push(`/user-freelance-adverts/${userId}`)
            }
        }
    },

    created() {
        addHandler(data => {
            if(data.objectType === 'ADVERT') {
                switch (data.eventType){
                    case 'CREATE':
                        this.addAdvertMutation(data.body)
                        break
                    case 'UPDATE':
                        this.updateAdvertMutation(data.body)
                        break
                    case 'REMOVE':
                        this.removeAdvertMutation(data.body)
                        break
                    default:
                        console.error(`"${data.eventType}" event type is unknown`)
                }
            } else if(data.objectType === 'COMMENT') {
                switch (data.eventType){
                    case 'CREATE':
                        this.addCommentMutation(data.body)
                        break
                    default:
                        console.error(`"${data.eventType}" event type is unknown`)
                }
            } else {
                console.error(`"${data.objectType}" object type is unknown`)
            }
        })
    },
    beforeMount() {
        if(!this.profile){
            this.$router.replace('/auth')
        }
    }
}
</script>

<style scoped>

</style>