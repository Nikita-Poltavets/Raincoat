<template>
    <v-app>
        <v-app-bar app>
            <v-toolbar-title>Raincoat</v-toolbar-title>
            <v-spacer></v-spacer>
            <span v-if="profile">{{ profile.name }}</span>
            <v-btn v-if="profile" icon href="/logout">
                <v-icon>exit_to_app</v-icon>
            </v-btn>
        </v-app-bar>
        <v-main>
            <v-container v-if="!profile">Необходимо авторизоваться через
                <a href="/login"> Google</a>
            </v-container>
            <v-container v-if="profile">
                <adverts-list />
            </v-container>
        </v-main>


    </v-app>
</template>

<script>
import { mapState, mapMutations } from 'vuex'
import AdvertsList from 'components/adverts/AdvertList.vue'
import { addHandler } from 'util/ws'


export default {
    components: {
        AdvertsList
    },
    computed: mapState(['profile']),
    methods: mapMutations(['addAdvertMutation', 'updateAdvertMutation', 'removeAdvertMutation']),
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
            } else {
                console.error(`"${data.eventType}" object type is unknown`)
            }
        })
    }
}
</script>

<style>

</style>