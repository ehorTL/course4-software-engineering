<template>
  <div>
    <b-form>
      <b-form-input
        class="mt-2"
        v-model="reader.name"
        type="text"
        required
        placeholder="Введіть ім'я"
      ></b-form-input>
      <b-form-input
        class="mt-2"
        v-model="reader.patronymic"
        type="text"
        required
        placeholder="Введіть по-батькові"
      ></b-form-input>
      <b-form-input
        class="mt-2"
        v-model="reader.surname"
        type="text"
        required
        placeholder="Введіть прізвище"
      ></b-form-input>
      <b-form-input
        class="mt-2"
        v-model="reader.email"
        type="email"
        required
        placeholder="Введіть email"
      ></b-form-input>
      <b-form-input
        class="mt-2"
        v-model="reader.phone"
        type="email"
        required
        placeholder="Введіть телефон"
      ></b-form-input>
      <b-form-input
        class="mt-2"
        v-model="reader.password.pass"
        type="password"
        required
        placeholder="Введіть пароль"
      ></b-form-input>
      <b-form-input
        class="mt-2"
        v-model="reader.password.pass_confirm"
        type="password"
        required
        placeholder="Повторіть пароль"
      ></b-form-input>
      <div class="mt-2">
        <b-button
          style="width: 100%"
          variant="primary"
          @click="registerAsReader"
          >Реєстрація</b-button
        >
      </div>
      <b-toast variant="danger" id="errors-toast" v-on:hide="errors = []">
        <div v-for="(e, index) in errors" :key="index">{{ e }}</div>
      </b-toast>
    </b-form>
  </div>
</template>

<script>
import { const_globals as globals } from "@/helpers/globals";

export default {
  data() {
    return {
      reader: {
        name: "",
        patronymic: "",
        surname: "",
        email: "",
        phone: "",
        birthday: "",
        password: {
          pass: "",
          pass_confirm: "",
        },
      },
      errors: [],
    };
  },
  methods: {
    registerAsReader() {
      // send data to server

      if (!this.formIsValid()) {
        this.showValidationErrors();
        return;
      }

      const self = this;
      let url = globals.remlib_api_host + globals.register_reader;
      this.$axios
        .post(url, {
          name: self.reader.name,
          patronymic: self.reader.patronymic,
          surname: self.reader.surname,
          email: self.reader.email,
          phone: self.reader.phone,
          password: self.reader.pass,
          birthday: self.reader.birthday,
        })
        .then(function (response) {
          console.log(response.data);
          self.$emit("reader-registered");
        })
        .catch(function (error) {
          console.log(error);
          self.$emit("error-in-registration");
        });
    },
    formIsValid() {
      let isValid = true;
      if (
        this.reader.password.pass != this.reader.password.pass_confirm ||
        this.reader.password.pass === undefined ||
        this.reader.password.pass === null ||
        this.reader.password.pass == ""
      ) {
        this.errors.push("Паролі не збігаються або не заповнені.");
        console.log(this.errors);
        isValid = false;
      }
      if (
        this.reader.name.trim() == "" ||
        this.reader.patronymic.trim() == "" ||
        this.reader.surname.trim == ""
      ) {
        this.errors.push("Заповніть ПІБ");
        isValid = false;
      }
      if (this.reader.phone.trim() == "") {
        this.errors.push("Введіть номер мобільного");
        isValid = false;
      }
      if (this.reader.email.trim() == "") {
        this.errors.push("Введіть адресу пошти");
        isValid = false;
      }

      return isValid;
    },
    showValidationErrors() {
      this.$bvToast.show("errors-toast");

      // this.errors = [];
    },
  },
};
</script>

<style>
</style>