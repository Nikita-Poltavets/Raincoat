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
import { getIndex } from 'util/collections'


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
            let index = getIndex(this.adverts, data.id)
            if(index > -1){
                this.adverts.splice(index, 1, data)
            } else {
                this.adverts.push(data)
            }
        })
    }
}
</script>

<style>

</style>