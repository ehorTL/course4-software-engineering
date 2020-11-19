import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";

// bootstrap configs
import { BootstrapVue, IconsPlugin } from "bootstrap-vue";
Vue.use(BootstrapVue);
Vue.use(IconsPlugin);
import "bootstrap/dist/css/bootstrap.css";
import "bootstrap-vue/dist/bootstrap-vue.css";

// bootstrap icons
import { BootstrapVueIcons } from "bootstrap-vue";
Vue.use(BootstrapVueIcons);

// This imports the dropdown and table plugins
import { DropdownPlugin, TablePlugin } from "bootstrap-vue";
Vue.use(DropdownPlugin);
Vue.use(TablePlugin);

// zmdi icons imports
import MenuIcon from "vue-material-design-icons/Menu.vue";
Vue.component("menu-icon", MenuIcon);
import "vue-material-design-icons/styles.css";

// axios configs
import axios from "axios";
Vue.prototype.$axios = axios;

// configuring vue-search-select
// components imported just in place they used!
import "vue-search-select/dist/VueSearchSelect.css";

// https://www.npmjs.com/package/vue-sweetalert2
import VueSweeralert2 from "vue-sweetalert2";
import "sweetalert2/dist/sweetalert2.min.css";
Vue.use(VueSweeralert2);

// datepicker
//https://github.com/mengxiong10/vue2-datepicker#readme

//vue-mask
// https://www.npmjs.com/package/v-mask
import VueMask from "v-mask";
Vue.use(VueMask);

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  axios: axios,
  render: (h) => h(App),
}).$mount("#app");
