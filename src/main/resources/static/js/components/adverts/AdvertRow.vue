<template>
    <div>
    <i>({{ advert.id }}) </i>{{ advert.title }}
     {{ advert.details }} {{ advert.description }}
    <span style="position: absolute; right: 0">
               <input type="button" value="Edit" @click="edit" />
               <input type="button" value="X" @click="del" />
           </span>
    </div>
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

<style scoped>

</style>