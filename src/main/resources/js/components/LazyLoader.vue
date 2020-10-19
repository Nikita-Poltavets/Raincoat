<template>
    <span></span>
</template>

<script>

import { mapActions } from 'vuex'

export default {
    name: "LazyLoader",
    methods: mapActions(['loadPageAction']),
    mounted() {
        window.onscroll = () => {
            const el = document.documentElement
            const isBottomOfScreen = el.scrollTop + window.innerHeight === el.offsetHeight // Проверяем что текущая позиция + высота окна эквивалентна общей выстоте документа

            if (isBottomOfScreen) {
                this.loadPageAction()
            }
        }
    },
    beforeDestroy() {
        window.onscroll = null
    }
}
</script>

<style scoped>

</style>