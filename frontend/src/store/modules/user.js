import axios from "axios";
import { const_globals as globals } from "@/helpers/globals";

const user = {
  state: {
    firstName: "",
    secondName: "",
    lastName: "",
    email: "",
    authorized: false,
    role: "", // reader, manager, admin
    auth_token: "",
    birthday: "",
    phone: "",
    fee: 0,
    role_id: "",
    user_notification_messages: [],

    reders: [],
    managers: [],
    reader_signup_data: {
      name: "",
      patronymic: "",
      surname: "",
      email: "",
      phone: "",
      password: "",
      birthday: "",
    },
  },
  actions: {
    login(context, payload) {
      let url = globals.remlib_api_host + globals.auth;
      console.log(payload);

      return axios
        .post(url, payload)
        .then(function(response) {
          context.commit("saveToken", response.data);
          return context.dispatch("getUser", {
            email: payload.email,
            token: response.data.token,
          });
        })
        .then(function(response) {
          context.commit("login", response.data);
        });
    },
    logout({ commit }) {
      commit("logout");
      // query to auth api
    },
    getUser(context, payload) {
      let url = globals.remlib_api_host + globals.user_by_id + payload.email;
      return axios.get(url, payload);
    },
  },
  mutations: {
    setFirstName(state, name) {
      state.firstName = name;
    },
    setSecondName(state, name) {
      state.secondName = name;
    },
    setLastName(state, name) {
      state.lastName = name;
    },
    setEmail(state, email) {
      state.email = email;
    },
    logout(state) {
      state.auth_token = null;
      state.role = null;
      state.authorized = false;
      state.firstName = null;
      state.secondName = null;
      state.lastName = null;

      localStorage.removeItem("user-id");
      localStorage.removeItem("user-token");
      localStorage.removeItem("user-role");
    },
    login(state, payload) {
      state.authorized = true;
      state.role = payload.role.role;
      state.firstName = payload.name;
      state.secondName = payload.patronymic;
      state.lastName = payload.surname;
      state.birthday = payload.birthday;
      state.phone = payload.phone;
      state.fee = payload.fee;
      state.role_id = payload.role.id;
      state.email = payload.email;

      localStorage.setItem("user-id", payload.email);
      localStorage.setItem("user-role", payload.role.role);
    },
    saveToken(state, payload) {
      state.auth_token = payload.token;
      localStorage.setItem("user-token", payload.token);
    },
    setManagers(state, payload) {
      state.managers = payload.managers;
    },
    setReaders(state, payload) {
      state.readers = payload.readers;
    },
  },
  getters: {
    savedToken: (state) => {
      if (
        state.auth_token !== undefined &&
        state.auth_token != null &&
        state.auth_token != ""
      ) {
        return state.auth_token;
      } else if (localStorage.getItem("user-token") !== undefined) {
        console.log("here");
        console.log(localStorage.getItem("user-token"));
        return localStorage.getItem("user-token");
      }

      return "";
    },
    savedUserId: (state) => {
      if (state.email != "") {
        return state.email;
      } else if (localStorage.getItem("user-id") !== undefined) {
        return localStorage.getItem("user-id");
      }

      return "";
    },
  },
};

export default user;
