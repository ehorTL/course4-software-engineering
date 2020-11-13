const user = {
  state: {
    firstName: "",
    secondName: "",
    lastName: "",
    email: "",
    authorized: false,
  },
  actions: {},
  mutations: {
    setFirstName(state, name) {
      console.log("mutationa");
      state.firstName = name;
    },
    setSecondName(state, name) {
      state.secondName = name;
    },
    setLastName(state, name) {
      state.lastName = name;
    },
    setLastEmail(state, email) {
      state.email = email;
    },
  },
  getters: {},
};

export default user;
