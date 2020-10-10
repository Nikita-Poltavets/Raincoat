<template>
    <div>
        <input type="text" placeholder="Advert title" v-model="title"/>
        <input type="text" placeholder="Advert details" v-model="details"/>
        <input type="text" placeholder="Advert description" v-model="description"/>
        <input type="button" value="Save" @click="save"/>
    </div>
</template>

<script>
import { mapActions } from 'vuex'

export default {
    props: ['advertAttr'],
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
        ...mapActions(['addAdvertAction' ,'updateAdvertAction']),
        save() {
            const advert = {
                id: this.id,
                title: this.title,
                details: this.details,
                description: this.description
            }

            if(this.id){
                this.updateAdvertAction(advert)
            } else {
                this.addAdvertAction(advert)
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