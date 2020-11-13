import Vue from "vue";
import VueRouter from "vue-router";
import Main from "../views/Main";
import LoginPage from "../views/LoginPage";
import UserProfile from "../views/user/UserProfile.vue";
import Admin from "../views/Admin";
import Manager from "../views/Manager";
import Error from "../views/Error";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Main",
    component: Main,
    children: [],
  },
  {
    path: "/error",
    name: "Error",
    component: Error,
    children: [],
  },
  {
    path: "/admin",
    name: "Admin",
    component: Admin,
    children: [],
  },
  {
    path: "/manager",
    name: "Manager",
    component: Manager,
    children: [],
  },
  {
    path: "/login",
    name: "LoginPage",
    component: LoginPage,
    children: [],
  },
  {
    path: "/profile",
    name: "UserProfile",
    component: UserProfile,
  },
  // {
  // path: "/about",
  // name: "About",
  // route level code-splitting
  // this generates a separate chunk (about.[hash].js) for this route
  // which is lazy-loaded when the route is visited.
  // component: () =>
  //   import(/* webpackChunkName: "about" */ "../views/About.vue"),
  // },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
