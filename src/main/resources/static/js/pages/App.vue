<template>
    <v-app>
        <v-app-bar app>
            <v-toolbar-title class="cyan--text text--lighten-1 text-h4">Raincoat</v-toolbar-title>
            <v-btn class="mx-3"
                   flat
                   v-if="profile"
                   :disabled="$route.path === '/'"
                   @click="showAdverts">
                All Adverts
            </v-btn>
            <v-btn flat
                   v-if="profile"
                   :disabled="$route.path === '/postNewAd'"
                   @click="showAdvertForm">
                Post New Advert
            </v-btn>
            <v-spacer></v-spacer>
            <v-btn flat
                   v-if="profile"
                   :disabled="$route.path === '/profile'"
                   @click="showProfile">
                {{ profile.name }}
            </v-btn>

            <v-btn v-if="profile" icon href="/logout">
                <v-icon>exit_to_app</v-icon>
            </v-btn>
        </v-app-bar>
        <v-main>
            <router-view></router-view>
        </v-main>


    </v-app>
</template>

<script>
import { mapState, mapMutations } from 'vuex'
import { addHandler } from 'util/ws'


export default {
    computed: mapState(['profile']),
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
        showProfile(){
            this.$router.push('/profile')
        },
        showAdvertForm(){
            this.$router.push('/postNewAd')
        },
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

<style>

</style>