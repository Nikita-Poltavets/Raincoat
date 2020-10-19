<template>
    <router-link
            class="routerLink"
            :to="`/advert-details/${advert.id}`"
    >
        <div class="pt-3">
            <b><span>{{ advert.title }}</span></b>
        </div>
    </router-link>
</template>

<script>
export default {
    name: "AdvertLink",
    props: ['advert'],

}
</script>

<style scoped>
    .routerLink{
        text-decoration: none;
    }
    .routerLink:hover{
        color: #05de05;
    }
</style>