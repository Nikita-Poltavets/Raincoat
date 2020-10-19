<template>
    <validation-observer
            ref="observer"
            v-slot=""
    >
        <v-form class="ml-16 mt-16">
            <v-container>
                <v-col md="5" offset-md="1">
                    <div class="text-h2 mb-10">Submit an advert on Raincoat</div>

                    <v-card class="pa-4 mb-10" width="700">

                        <div class="text-h4">Title</div>

                        <v-row>
                            <v-col cols="6" md="11" class="my-7">

                                <validation-provider
                                        v-slot="{ errors }"
                                        name="Title"
                                        rules="required"
                                >
                                    <v-text-field
                                            v-model="title"
                                            label="Title"
                                            @keyup.enter="save"
                                            :rules="titleRules"
                                            :error-messages="errors"
                                            required
                                    >
                                    </v-text-field>
                                </validation-provider>
                            </v-col>
                        </v-row>

                        <div class="text-h4">Details</div>
                        <v-row>
                            <v-col cols="6" md="9">
                                <validation-provider
                                        v-slot="{ errors }"
                                        name="Details"
                                        rules="required"
                                >
                                    <v-text-field
                                            v-model="details"
                                            label="Details"
                                            @keyup.enter="save"
                                            :rules="detailsRules"
                                            :error-messages="errors"
                                            required
                                    >
                                    </v-text-field>
                                </validation-provider>
                            </v-col>
                        </v-row>

                    </v-card>


                    <v-card class="pa-4 mb-10" width="700">

                        <div class="text-h4">Offering/looking for job?</div>

                        <v-row>
                            <v-col cols="6" md="11" class="my-7">

                                <validation-provider
                                        v-slot="{ errors }"
                                        name="job"
                                        rules="required"
                                >
                                    <v-select
                                            v-model="job"
                                            :items="items"
                                            dense
                                            @keyup.enter="save"
                                            :error-messages="errors"
                                            required
                                    ></v-select>
                                </validation-provider>
                            </v-col>
                        </v-row>

                    </v-card>


                    <v-card class="pa-4 mb-10" width="700">

                        <div class="text-h4">Description</div>

                        <v-row>
                            <v-col cols="6" md="11" class="my-7">
                                <validation-provider
                                        v-slot="{ errors }"
                                        name="Description"
                                        rules="required"
                                >
                                    <v-textarea v-model="description" label="Description"
                                                filled
                                                auto-grow
                                                rows="4"
                                                row-height="30"
                                                shaped
                                                @keyup.enter="save"
                                                :rules="descriptionRules"
                                                :error-messages="errors"
                                                required
                                    ></v-textarea>
                                </validation-provider>
                            </v-col>
                        </v-row>

                    </v-card>


                    <v-btn @click="save">
                        Submit
                    </v-btn>
                </v-col>

            </v-container>

        </v-form>
    </validation-observer>

</template>

<script>
import { mapActions } from 'vuex'
import { required } from 'vee-validate/dist/rules'
import { extend, ValidationObserver, ValidationProvider, setInteractionMode } from 'vee-validate'

setInteractionMode('eager')

extend('required', {
    ...required,
    message: '{_field_} can not be empty',
})

export default {
    props: ['advertAttr'],
    components:{
        ValidationProvider,
        ValidationObserver
    },
    data() {
        return {
            title: '',
            description: '',
            details: '',
            job: '',
            errors: null,
            id: null,
            items: ['Offer', 'Looking for'],
            titleRules: [
                v => !!v || 'Title is required',
                v => v.length > 3 || 'Name must be mote than 3 characters'
            ],
            detailsRules: [
                v => !!v || 'Details is required',
                v => v.length > 3 || 'Details must be mote than 3 characters'
            ],
            descriptionRules: [
                v => !!v || 'Description is required',
                v => v.length > 10 || 'Description must be mote than 10 characters'
            ],
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
            if(this.$refs.observer.validate()) {

                const advert = {
                    id: this.id,
                    title: this.title,
                    details: this.details,
                    description: this.description,
                    job: this.setVariableJob(this.job)
                }

                if (this.id) {
                    this.updateAdvertAction(advert)
                } else {
                    this.addAdvertAction(advert)
                }
                this.title = ''
                this.details = ''
                this.description = ''
                this.job = ''
                this.id = null
            }
        },
        setVariableJob(job) {
            if (job === 'Offer') {
                job = false
                return job
            } else {
                job = true
                return job
            }
        },

    }
}
</script>

<style scoped>

</style>