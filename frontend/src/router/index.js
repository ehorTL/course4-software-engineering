import Vue from "vue";
import VueRouter from "vue-router";
import Main from "../views/Main";
import LoginPage from "../views/LoginPage";
import UserProfile from "../views/user/UserProfile.vue";
import Admin from "../views/Admin";
import Manager from "../views/Manager";
import Error from "../views/Error";
import Readers from "../views/Readers";
import ReaderInfo from "@/components/reader/ReaderInfo";
import HomePageComponent from "@/components/main/HomePage";
import SystemConfigs from "../views/SystemConfigs";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Main",
    component: Main,
    children: [
      {
        path: "/profile",
        name: "UserProfile",
        component: UserProfile,
      },
      {
        path: "/",
        name: "HomePageComponent",
        component: HomePageComponent,
        children: [],
      },
    ],
  },
  {
    path: "/error",
    name: "Error",
    component: Error,
    children: [],
  },
  {
    path: "/admin/managers",
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
    path: "/manager/readers",
    name: "Readers",
    component: Readers,
    children: [],
  },
  {
    path: "/manager/readers/:id",
    name: "ReaderInfo",
    component: ReaderInfo,
    props: true,
    children: [],
  },
  {
    path: "/login",
    name: "LoginPage",
    component: LoginPage,
    children: [],
  },
  {
    path: "/manager/system-configs",
    name: "system-configs",
    component: SystemConfigs,
  },
  {
    path: "*",
    name: "Error",
    component: Error,
    redirect: "/error",
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