<template>
    <v-layout justify-center>
    <v-list two-line width="1200">
        <v-subheader>
            Comments
        </v-subheader>

        <template
                v-for="(item, index) in comments"
        >
            <v-divider
                    v-if="index > 0"
                    :key="index"
            ></v-divider>

            <comment-item
                    :comment="item"
                    :key="'item' + index"
            ></comment-item>
        </template>


        <comment-form
                v-if="$store.state.profile"
                :advert-id="advertId"
        >
        </comment-form>
    </v-list>
    </v-layout>

</template>

<script>
import CommentItem from './CommentItem.vue'
import CommentForm from './CommentForm.vue'

export default {
    name: "CommentList",
    components: {CommentForm, CommentItem},
    props: ['comments', 'advertId']
}
</script>

<style scoped>

</style>