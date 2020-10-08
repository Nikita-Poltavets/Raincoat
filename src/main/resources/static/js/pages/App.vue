<template>
    <div>
        <div v-if="!profile">Необходимо авторизоваться через
            <a href="/login"> Google</a>
        </div>
        <div v-else>
            <div>{{ profile.name }}&nbsp;<a href="/logout">Выйти</a></div>
            <adverts-list :adverts="adverts"/>
        </div>
    </div>
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
        return {
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