<template>
    <v-card class="my-2 mx-16">
        <v-card-title>
            <i>({{ advert.id }}) </i>
            {{ advert.title }}
        </v-card-title>
        {{ advert.details }} {{ advert.description }}
        <v-card-actions>
            <v-btn value="Edit" @click="edit">Edit</v-btn>
            <v-btn depressed color="error" @click="del">
                Delete
            </v-btn>
        </v-card-actions>
    </v-card>
</template>

<script>
export default {
    props: ['advert', 'editAdvert', 'deleteAdvert', 'adverts'],

    methods: {
        edit(){
            this.editAdvert(this.advert)
        },
        del(){
            this.deleteAdvert(this.advert)
        }
    }
}
</script>

<style>

</style>