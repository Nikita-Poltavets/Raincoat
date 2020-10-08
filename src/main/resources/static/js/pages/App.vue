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
import AdvertsList from "components/adverts/AdvertList.vue";

export default {
    components: {
        AdvertsList
    },
    data() {
        return {
            adverts: frontendData.adverts,
            profile: frontendData.profile
        }
    }
}
</script>

<style>

</style>