<template>
    <v-layout row>
        <v-text-field
                v-model="title"
                label="Title"
                @keyup.enter="save"
        />
        <v-text-field
                v-model="details"
                label="Details"
                @keyup.enter="save"
        />
        <v-select
                v-model="job"
                :items="items"
                dense
                @keyup.enter="save"
        />

        <v-textarea v-model="description" label="Description"
                    filled
                    auto-grow
                    rows="4"
                    row-height="30"
                    shaped
                    @keyup.enter="save"
        ></v-textarea>

        <v-btn @click="save" >
            Save
        </v-btn>

    </v-layout>
</template>

<script>
import {mapActions} from 'vuex';

export default {
    name: "FormAdvert",
    props: ['advertAttr'],
    data() {
        return {
            title: '',
            description: '',
            details: '',
            job: '',
            id: null,
            items: ['Offer', 'Looking for']
        }
    },
    watch: {
        advertAttr: function (newVal, oldVal) {
            this.title = newVal.title
            this.description = newVal.description
            this.details = newVal.details
            this.job = this.setVariableJob(newVal.job)
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
                description: this.description,
                job: this.setVariableJob(this.job)
            }

            if(this.id){
                this.updateAdvertAction(advert)
            } else {
                this.addAdvertAction(advert)
            }
            this.title = ''
            this.details = ''
            this.description = ''
            this.job = ''
            this.id = null
        },
        setVariableJob(job) {
            if (job === 'Offer') {
                job = false
                return job
            } else {
                job = true
                return job
            }
        }
    }
}
</script>

<style scoped>

</style>