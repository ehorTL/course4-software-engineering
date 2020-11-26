<template>
  <div>
    <b-form inline>
      <b-input-group class="mb-2 mr-sm-2 mb-sm-0">
        <b-form-input
          id="inline-form-input-username"
          placeholder="Введіть пошту"
        ></b-form-input>
      </b-input-group>
      <b-button variant="primary" @click="resetPaasword"
        >Відновити пароль</b-button
      >
    </b-form>
    <b-toast id="error-toast" variant="danger" v-on:hide="error = ''">
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
        this.$bvToast.show("error-toast");
        return;
      }

      this.$bvToast.hide("error-toast");
      // axios request
    },
  },
};
</script>

<style>
</style>