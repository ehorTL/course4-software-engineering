import Vue from "vue";
import Vuex from "vuex";

import user from "./modules/user";
import global_configs from "./modules/global_configs";
import publication from "./modules/publication";
import loan_card from "./modules/loan_card";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {},
  mutations: {},
  actions: {},
  modules: {
    user: user,
    global_configs: global_configs,
    publication: publication,
    loan_card: loan_card,
  },
});
