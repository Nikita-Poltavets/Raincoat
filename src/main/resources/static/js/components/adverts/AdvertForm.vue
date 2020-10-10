<template>
    <div>
        <input type="text" placeholder="Advert title" v-model="title"/>
        <input type="text" placeholder="Advert details" v-model="details"/>
        <input type="text" placeholder="Advert description" v-model="description"/>
        <input type="button" value="Save" @click="save"/>
    </div>
</template>

<script>
import advertApi from 'api/adverts'

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


            const advert = {
                id: this.id,
                title: this.title,
                details: this.details,
                description: this.description
            }

            if(this.id){
                advertApi.update(advert).then(result =>
                        result.json().then(data => {
                            const index = this.adverts.findIndex(item => item.id === data.id)
                            this.adverts.splice(index, 1, data)

                        })
                )
            } else {
                advertApi.add(advert).then(result =>
                        result.json().then(data => {

                            const index = this.adverts.findIndex(item => item.id === data.id)

                            if(index > -1){
                                this.adverts.splice(index, 1, data)
                            } else {
                                this.adverts.push(data)
                            }
                        })
                )
            }
            this.title = ''
            this.details = ''
            this.description = ''
            this.id = ''
        }
    }
}
</script>

<style scoped>

</style>