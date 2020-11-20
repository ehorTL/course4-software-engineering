import Vue from "vue";
import Vuex from "vuex";

import user from "./modules/user";
import book from "./modules/book";
import global_configs from "./modules/global_configs";
import publication from "./modules/publication";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {},
  mutations: {},
  actions: {},
  modules: {
    user: user,
    book: book,
    global_configs: global_configs,
    publication: publication,
  },
});
