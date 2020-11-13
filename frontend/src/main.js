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

// This imports the dropdown and table plugins
import { DropdownPlugin, TablePlugin } from "bootstrap-vue";
Vue.use(DropdownPlugin);
Vue.use(TablePlugin);

// zmdi icons imports
import MenuIcon from "vue-material-design-icons/Menu.vue";
Vue.component("menu-icon", MenuIcon);
import "vue-material-design-icons/styles.css";

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount("#app");
