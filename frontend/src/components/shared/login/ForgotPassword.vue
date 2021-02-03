<template>
  <div>
    <b-form inline>
      <b-input-group class="mb-2 mr-sm-2 mb-sm-0">
        <b-form-input
          id="inline-form-input-username"
          placeholder="Введіть пошту"
          v-model="email"
        ></b-form-input>
      </b-input-group>
      <b-button variant="primary" @click="resetPaasword"
        >Відновити пароль</b-button
      >
    </b-form>
    <b-toast id="error-toast-3" variant="danger" v-on:hide="error = ''">
      <div>{{ error }}</div>
    </b-toast>
  </div>
</template>

<script>
export default {
  data() {
    return {
      email: "",
      error: "",
    };
  },
  methods: {
    formIsValid() {
      let isValid = true;
      if (!/^[\w_\d]+@knu\.ua$/.test(this.email)) {
        this.error = "Введіть коректно пошту з домену knu.ua";
        isValid = false;
      }
      return isValid;
    },
    resetPaasword() {
      if (!this.formIsValid()) {
        this.$bvToast.show("error-toast-3");
        return;
      }

      // axios request
      this.$bvToast.hide("error-toast-3");
      this.$emit("fp-hide");
      this.$swal.fire(
        "Запит на відновлення надіслано!",
        "Очікуйте лист на вказану пошту!",
        "success"
      );
    },
  },
};
</script>

<style>
</style>