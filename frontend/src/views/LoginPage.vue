<template>
  <div class="containter-fluid">
    <div class="row">
      <div
        class="col-md-6 my-col d-flex align-items-center justify-content-center"
      >
        <div class="login-logo-main">
          <b-img fluid src="/img/logo_main.png" class="rounded" alt=""></b-img>
        </div>
      </div>
      <div
        class="col-md-6 my-col d-flex align-items-center justify-content-center"
      >
        <div class="login-block shadow p-3 mb-5 bg-white rounded">
          <div>
            <b-form>
              <b-form-input
                v-model="email"
                type="email"
                required
                placeholder="Enter email"
              ></b-form-input>
              <b-form-input
                class="mt-2"
                v-model="password"
                type="password"
                required
                placeholder="Enter password"
              ></b-form-input>
              <div class="mt-2">
                <b-button style="width: 100%" variant="primary" @click="login"
                  >Вхід</b-button
                >
              </div>
              <b-form-group class="text-center mt-2 mb-4">
                <a href="javascript:void(0)">Забули пароль?</a>
              </b-form-group>
              <hr />
              <b-form-group class="text-center mt-0">
                <a href="javascript:void(0)" @click="onCreateAccountClicked"
                  >Створити обліковий запис</a
                >
              </b-form-group>
            </b-form>
          </div>
          <div v-show="state.creating_account">
            <b-modal id="modal-1" title="BootstrapVue">
              <signup-form></signup-form>
            </b-modal>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import SignupForm from "@/components/shared/login/SignupForm";

export default {
  name: "LoginPage",
  components: {
    "signup-form": SignupForm,
  },
  data() {
    return {
      state: {
        creating_account: false,
      },
      email: "",
      password: "",
    };
  },
  methods: {
    onCreateAccountClicked() {
      // this.state.creating_account = true;
      this.$bvModal.show("modal-1");
    },
    login() {
      // todo
    },
  },
};
</script>

<style scoped>
.login-block {
  min-width: 200px;
  margin: 20px;
  padding: 20px;
  border-radius: 20px;
  background-color: black;
}
</style>

<style>
html,
body,
.row {
  background-color: #f0f2f5;
}
.my-col {
  height: 100vh;
}
.login-logo-main {
  max-width: 300px;
}
</style>
