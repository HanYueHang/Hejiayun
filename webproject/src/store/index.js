import Vue from 'vue'
import Vuex from 'vuex'

import app from './modules/app'
import user from './modules/user'
// default router permission control
import permission from './modules/permission'
import propertyAdd from './modules/property-add'
import oneStep from '@/store/modules/oneStep'
// dynamic router permission control (Experimental)
// import permission from './modules/async-router'
import getters from './getters'
import twoStep from '@/store/modules/twoStep'

Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    app,
    user,
    permission,
    propertyAdd,
      oneStep,
      twoStep
  },
  state: {

  },
  mutations: {

  },
  actions: {

  },
  getters
})
