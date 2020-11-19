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
    </b-form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      reader: {
        name: "",
        patronymic: "",
        surname: "",
        email: "",
        phone: "",
        password: {
          pass: "",
          pass_confirm: "",
        },
      },
    };
  },
  methods: {
    registerAsReader() {},
  },
};
</script>

<style>
</style>