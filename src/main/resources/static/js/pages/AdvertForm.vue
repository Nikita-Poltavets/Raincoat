<template>
    <v-form class="ml-16 mt-16">
        <v-container>
            <v-col md="5" offset-md="1">
                <div class="text-h2 mb-10">Submit an advert on Raincoat</div>

                <v-card class="pa-4 mb-10" width="700">

                    <div class="text-h4">Title</div>

                    <v-row>
                        <v-col cols="6" md="11" class="my-7">
                            <v-text-field
                                    v-model="title"
                                    label="Title"
                                    @keyup.enter="save"
                            >
                            </v-text-field>
                        </v-col>
                    </v-row>

                    <div class="text-h4">Details</div>
                    <v-row>
                        <v-col cols="6" md="9">
                            <v-text-field
                                    v-model="details"
                                    label="Details"
                                    @keyup.enter="save"
                            >
                            </v-text-field>
                        </v-col>
                    </v-row>

                </v-card>


                <v-card class="pa-4 mb-10" width="700">

                    <div class="text-h4">Offering/looking for job?</div>

                    <v-row>
                        <v-col cols="6" md="11" class="my-7">
                            <v-select
                                    v-model="job"
                                    :items="items"
                                    dense
                                    @keyup.enter="save"
                            ></v-select>
                        </v-col>
                    </v-row>

                </v-card>


                <v-card class="pa-4 mb-10" width="700">

                    <div class="text-h4">Description</div>

                    <v-row>
                        <v-col cols="6" md="11" class="my-7">

                            <v-textarea v-model="description" label="Description"
                                        filled
                                        auto-grow
                                        rows="4"
                                        row-height="30"
                                        shaped
                                        @keyup.enter="save"
                            ></v-textarea>
                        </v-col>
                    </v-row>

                </v-card>


                <v-btn @click="save">
                    Submit
                </v-btn>
            </v-col>

        </v-container>

    </v-form>





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