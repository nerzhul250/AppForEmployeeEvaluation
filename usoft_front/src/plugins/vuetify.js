// src/plugins/vuetify.js

import Vue from 'vue'
import Vuetify from 'vuetify'
import 'vuetify/dist/vuetify.min.css'

Vue.use(Vuetify)

const opts = {
    theme: {
        themes: {
            light: {
                primary: '#3D02F3',//colors.red.darken1, // #E53935
                secondary: '#7709DB', //colors.red.lighten4, // #FFCDD2
                accent: '090DDB', //colors.indigo.base, // #3F51B5
            },
        },
    },
}

export default new Vuetify(opts)