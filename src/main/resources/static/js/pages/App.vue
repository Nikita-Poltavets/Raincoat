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
                <adverts-list :adverts="adverts"/>
            </v-container>
        </v-main>


    </v-app>
</template>

<script>
import AdvertsList from 'components/adverts/AdvertList.vue'
import { addHandler } from 'util/ws'


export default {
    components: {
        AdvertsList
    },
    data() {
        return{
            adverts: frontendData.adverts,
            profile: frontendData.profile
        }
    },
    created() {
        addHandler(data => {
            if(data.objectType === 'ADVERT') {
                const index = this.adverts.findIndex(item => item.id === data.body.id)
                switch (data.eventType){
                    case 'CREATE':
                    case 'UPDATE':
                        if(index > -1){
                            this.adverts.splice(index, 1, data.body)
                        } else {
                            this.adverts.push(data.body)
                        }
                        break
                    case 'REMOVE':
                        this.adverts.splice(index, 1)
                        break
                    default:
                        console.error(`"${data.eventTyp}" event type is unknown`)
                }
            } else {
                console.error(`"${data.eventTyp}" object type is unknown`)
            }
        })
    }
}
</script>

<style>

</style>