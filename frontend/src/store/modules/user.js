import axios from "axios";
import { const_globals as globals } from "@/helpers/globals";

const user = {
  state: {
    firstName: "",
    secondName: "",
    lastName: "",
    email: "",
    authorized: true,
    role: "manager", // reader, manager, admin
    auth_token: "werftghyjkl0oiu6456tyui",
    birthday: "",
    phone: "",
    fee: 0,
    role_id: "",

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

      axios
        .post(url, payload)
        .then(function(response) {
          context.commit("saveToken", response.data.token);
          return context.dispatch("getUser", {
            email: payload.email,
            token: response.data.token,
          });
        })
        .catch(function(error) {
          console.log(error.response);
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
    },
    saveToken(state, payload) {
      state.auth_token = payload.token;
    },
    setManagers(state, payload) {
      state.managers = payload.managers;
    },
    setReaders(state, payload) {
      state.readers = payload.readers;
    },
  },
  getters: {},
};

export default user;
