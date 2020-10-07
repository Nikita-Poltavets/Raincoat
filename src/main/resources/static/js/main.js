function getIndex(list, id) {
    for (let i = 0; i < list.length; i++ ) {
        if (list[i].id === id) {
            return i;
        }
    }
    return -1;
}

var advertApi = Vue.resource('/advert{/id}');


Vue.component('advert-form', {
    props: ['adverts', 'advertAttr'],
    data: function (){
        return {
            title: '',
            description: '',
            details: '',
            id: ''
        }
    },
    watch: {
        advertAttr: function(newVal, oldVal){
            this.title = newVal.title;
            this.description = newVal.description;
            this.details = newVal.details;
            this.id = newVal.id;
        }
    },
    template:
        '<div>' +
                '<input type="text" placeholder="Advert title" v-model="title" /> ' +
                '<input type="text" placeholder="Advert details" v-model="details" /> ' +
                '<input type="text" placeholder="Advert description" v-model="description" />' +
                '<input type="button" value="Save" @click="save" />' +
        '</div>',
    methods: {
        save: function () {
            let advert = { title: this.title, details: this.details, description: this.description};

            if(this.id){
                advertApi.update({id: this.id}, advert).then(result =>
                    result.json().then(data => {
                        let index = getIndex(this.adverts, data.id);
                        this.adverts.splice(index, 1, data);

                        this.title = ''
                        this.details = ''
                        this.description = ''
                        this.id = ''
                    })
                )
            } else {
                advertApi.save({}, advert).then(result =>
                    result.json().then(data => {
                        this.adverts.push(data);

                        this.title = ''
                        this.details = ''
                        this.description = ''
                    })
                )
            }
        }
    }
});

Vue.component('advert-row', {
    props: ['advert', 'editMethod', 'adverts'],
    template:
        '<div>' +
            '<i>({{ advert.id }}) </i>{{ advert.title }}' +
            ' {{ advert.details }} {{ advert.description }} ' +
            '<span style="position: absolute; right: 0">' +
                '<input type="button" value="Edit" @click="edit" />' +
                '<input type="button" value="X" @click="del" />' +
            '</span>' +
        '</div>',
    methods: {
        edit: function (){
            this.editMethod(this.advert);
        },
        del: function (){
            advertApi.remove({id: this.advert.id}).then(result => {
                if(result.ok){
                    this.adverts.splice(this.adverts.indexOf(this.advert), 1)
                }
            })
        }
    }
});

Vue.component('adverts-list', {
    props: ['adverts'],
    data: function(){
        return {
            advert: null
        }
    },
    template:
        '<div style="position: relative; width: 300px;">' +
            '<advert-form :adverts="adverts" :advertAttr="advert" />' +
            '<advert-row v-for="advert in adverts" :key="advert.id" :advert="advert" ' +
                ':editMethod="editMethod" :adverts="adverts" />' +
        '</div>',
    methods: {
        editMethod: function (advert) {
            this.advert = advert;
        }
    }
});

let app = new Vue({
    el: '#app',
    template:
        '<div>' +
            '<div v-if="!profile">Необходимо авторизоваться через <a href="/login">Google</a></div>' +
            '<div v-else>' +
                    '<div>{{profile.name}}&nbsp;<a href="/logout">Выйти</a></div>' +
                '<adverts-list :adverts="adverts"/>' +
            '</div>' +
        '</div>',
    data: {
        adverts: frontendData.adverts,
        profile: frontendData.profile
    },
    created: function (){
        // advertApi.get().then(result =>
        //     result.json().then(data =>
        //         data.forEach(advert => this.adverts.push(advert))
        //     )
        // )
    },
});