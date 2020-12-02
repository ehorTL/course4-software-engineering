import Vue from "vue";
import VueRouter from "vue-router";
import Main from "../views/Main";
import LoginPage from "../views/LoginPage";
import UserProfile from "../views/user/UserProfile.vue";
import Admin from "../views/Admin";
import Error from "../views/Error";
import Readers from "../views/Readers";
import ReaderInfo from "@/components/reader/ReaderInfo";
import HomePageComponent from "@/components/main/HomePage";
import SystemConfigs from "../views/SystemConfigs";
import ManagerSystemConfigs from "../views/ManagerSystemConfigs";
import ManagerBooks from "@/components/shared/book/ManagerBooks";
import Reports from "@/components/manager/Reports";
import AboutLibrary from "@/components/shared/section/AboutLibrary";
import Book from "@/components/shared/book/Book";
import ReaderBook from "@/components/shared/book/ReaderBook";
import MyBooks from "@/components/reader/MyBooks.vue";
import ReaderCatalogEntry from "@/components/shared/book/ReaderCatalogEntry";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Main",
    component: Main,
    children: [
      {
        path: "/error",
        name: "Error",
        component: Error,
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
        path: "/manager/system-configs",
        name: "ManagerSystemConfigs",
        component: ManagerSystemConfigs,
        children: [],
      },
      {
        path: "/catalog-entry/:id",
        name: "ReaderCatalogEntry",
        component: ReaderCatalogEntry,
        props: true,
      },
      {
        path: "/reader/my-books",
        name: "MyBooks",
        component: MyBooks,
      },
      {
        path: "/about",
        name: "AboutLibrary",
        component: AboutLibrary,
      },
      {
        path: "/reader/book/:id",
        name: "ReaderBook",
        component: ReaderBook,
        props: true,
      },
      {
        path: "/manager/book/:bookId",
        name: "ManagerBook",
        component: Book,
        props: true,
      },
      {
        path: "/manager/reports",
        name: "Reports",
        component: Reports,
      },
      {
        path: "/admin/system-configs",
        name: "system-configs",
        component: SystemConfigs,
      },
      {
        path: "/admin/managers",
        name: "Admin",
        component: Admin,
        children: [],
      },
      {
        path: "/manager/readers",
        name: "Readers",
        component: Readers,
        children: [],
      },
      {
        path: "/manager/books",
        name: "ManagerBooks",
        component: ManagerBooks,
        children: [],
      },
      {
        path: "/profile",
        name: "UserProfile",
        component: UserProfile,
        meta: {
          requiresAuth: true,
        },
        beforeEnter: (to, from, next) => {
          if (localStorage.getItem("user-token")) {
            next();
          } else {
            next({ name: "LoginPage" });
          }
        },
        children: [],
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
    path: "/login",
    name: "LoginPage",
    component: LoginPage,
    children: [],
  },
  {
    path: "*",
    name: "Error",
    component: Error,
    redirect: "/error",
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

// router.beforeEach((to, from, next) => {
//   if (to.path.startsWith("/manager")) {
//     if (localStorage.getItem("user-role") == "manager") {
//       next();
//     } else next({ name: "LoginPage" });
//   } else if (to.path.startsWith("/admin")) {
//     if (localStorage.getItem("user-role") == "admin") {
//       next();
//     } else next({ name: "LoginPage" });
//   } else next();
// });

export default router;
