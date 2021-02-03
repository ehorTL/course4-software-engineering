<template>
  <b-container fluid>
    <b-row class="my-col">
      <div class="col-md-6 d-flex align-items-center justify-content-center">
        <div class="login-logo-main" @click="goToMain">
          <b-img fluid src="/img/logo_main.png" class="rounded" alt=""></b-img>
        </div>
      </div>
      <div class="col-md-6 d-flex align-items-center justify-content-center">
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
                <a href="javascript:void(0)" @click="forgotPassword"
                  >Забули пароль?</a
                >
              </b-form-group>
              <hr />
              <b-form-group class="text-center mt-0">
                <b-button variant="primary" @click="onCreateAccountClicked">
                  Створити обліковий запис</b-button
                >
              </b-form-group>
            </b-form>
          </div>
          <div>
            <b-modal id="modal-registering" title="Реєстрація">
              <signup-form
                v-on:reader-registered="closeRegistrationSuccess"
                v-on:error-in-registration="closeRegistrationFail"
              ></signup-form>
              <template #modal-footer class="justify-content-start">
                <b-button @click="closeRegistration">Cancel</b-button>
              </template>
            </b-modal>
          </div>
          <div>
            <b-modal
              id="modal-forgot-pass"
              title="Забули пароль"
              ok-only
              ok-title="Відміна"
              ok-variant="secondary"
              button-size="sm"
            >
              <forgot-password @fp-hide="forgotPasswordClose"></forgot-password>
            </b-modal>
          </div>
        </div>
      </div>
    </b-row>
    <b-toast
      variant="danger"
      v-on:hide="state.errors = []"
      id="errors-toast"
      title="Сповіщення"
    >
      <div v-for="(e, index) in state.errors" :key="index">{{ e }}</div>
    </b-toast>
  </b-container>
</template>

<script>
import SignupForm from "@/components/shared/login/SignupForm";
import ForgotPassword from "@/components/shared/login/ForgotPassword";

export default {
  name: "LoginPage",
  components: {
    "signup-form": SignupForm,
    "forgot-password": ForgotPassword,
  },
  data() {
    return {
      state: {
        errors: [],
        creating_account: false,
      },
      email: "",
      password: "",
    };
  },
  methods: {
    goToMain() {
      this.$router.push({ name: "HomePageComponent" });
    },
    onCreateAccountClicked() {
      // this.state.creating_account = true;
      this.$bvModal.show("modal-registering");
    },
    login() {
      if (!this.formIsValid()) {
        this.showErrors();
        return;
      }

      const self = this;
      this.$store
        .dispatch("login", {
          email: this.email,
          password: this.password,
        })
        .then(function () {
          self.goToMain();
        })
        .catch(() => {
          // console.log("Error in readers data fetching");
          self.$swal.fire(
            "Вибачте",
            "Виникла проблема з підключенням",
            "error"
          );
        });
    },
    formIsValid() {
      let isValid = true;
      if (this.email.trim() == "" || this.password == "") {
        this.state.errors.push("Введіть логін та пароль");
        isValid = false;
      } else {
        if (this.password.length < 8) {
          this.state.errors.push("Надто короткий пароль. Спробуйте ще раз.");
          isValid = false;
        }
        if (!/^[\w_\d]+@knu\.ua$/.test(this.email)) {
          this.state.errors.push("Введіть коректно пошту з домену knu.ua");
          isValid = false;
        }
      }

      return isValid;
    },
    showErrors() {
      this.$bvToast.show("errors-toast");
    },
    forgotPassword() {
      this.$bvModal.show("modal-forgot-pass");
    },
    forgotPasswordClose() {
      this.$bvModal.hide("modal-forgot-pass");
    },
    closeRegistration() {
      this.$bvModal.hide("modal-registering");
    },
    closeRegistrationSuccess() {
      this.closeRegistration();
      this.$swal.fire(
        "Успішно зареєстровано!",
        "Очікуйте лист з підтвердженням реєстрації на вказану пошту!",
        "success"
      );
    },
    closeRegistrationFail() {
      this.closeRegistration();
    },
  },
  created() {
    console.log(this.state.errors);
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
.login-logo-main:hover {
  cursor: pointer;
}
</style>
