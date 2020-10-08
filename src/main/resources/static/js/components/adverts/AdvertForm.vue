<template>
    <div>
        <input type="text" placeholder="Advert title" v-model="title"/>
        <input type="text" placeholder="Advert details" v-model="details"/>
        <input type="text" placeholder="Advert description" v-model="description"/>
        <input type="button" value="Save" @click="save"/>
    </div>
</template>

<script>
import { sendMessage } from 'util/ws'

export default {
    props: ['adverts', 'advertAttr'],
    data() {
        return {
            title: '',
            description: '',
            details: '',
            id: ''
        }
    },
    watch: {
        advertAttr: function (newVal, oldVal) {
            this.title = newVal.title
            this.description = newVal.description
            this.details = newVal.details
            this.id = newVal.id
        }
    },
    methods: {
        save() {
            sendMessage({id: this.id, title: this.title, details: this.details, description: this.description})

            this.title = ''
            this.details = ''
            this.description = ''
            this.id = ''

            // const advert = { title: this.title, details: this.details, description: this.description}
            //
            // if(this.id){
            //     advertApi.update({id: this.id}, advert).then(result =>
            //             result.json().then(data => {
            //                 const index = getIndex(this.adverts, data.id)
            //                 this.adverts.splice(index, 1, data)
            //
            //                 this.title = ''
            //                 this.details = ''
            //                 this.description = ''
            //                 this.id = ''
            //             })
            //     )
            // } else {
            //     this.$resource('/advert{/id}').save({}, advert).then(result =>
            //             result.json().then(data => {
            //                 this.adverts.push(data)
            //
            //                 this.title = ''
            //                 this.details = ''
            //                 this.description = ''
            //             })
            //     )
            // }
        }
    }
}
</script>

<style scoped>

</style>