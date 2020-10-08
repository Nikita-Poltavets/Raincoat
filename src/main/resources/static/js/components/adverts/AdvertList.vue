<template>
    <div style="position: relative; width: 300px;">
        <advert-form :adverts="adverts" :advertAttr="advert"/>
        <advert-row v-for="advert in adverts"
                    :key="advert.id"
                    :advert="advert"
                    :editAdvert="editAdvert"
                    :deleteAdvert="deleteAdvert"
                    :adverts="adverts"/>

    </div>
</template>

<script>

import AdvertRow from "components/adverts/AdvertRow.vue"
import AdvertForm from "components/adverts/AdvertForm.vue"

export default {
    props: ['adverts'],
    components: {
        AdvertRow,
        AdvertForm
    },
    data() {
        return {
            advert: null
        }
    },
    methods: {
        editAdvert(advert) {
            this.advert = advert
        },
        deleteAdvert(advert) {
            this.$resource('/advert{/id}').remove({id: advert.id}).then(result => {
                if (result.ok) {
                    this.adverts.splice(this.adverts.indexOf(this.advert), 1)
                }
            })
        }

    }
}
</script>

<style scoped>

</style>