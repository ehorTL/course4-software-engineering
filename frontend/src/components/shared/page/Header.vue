<template>
  <div>
    <b-navbar toggleable="lg" type="light" variant="light">
      <!-- <b-navbar-brand href="#">NavBar</b-navbar-brand> -->
      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
      <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav>
          <b-nav-item :to="{ name: 'HomePageComponent' }">Головна</b-nav-item>
          <b-nav-item :to="{ name: 'AboutLibrary' }">Про бібліотеку</b-nav-item>
          <b-nav-item
            v-if="$store.state.user.role === 'reader'"
            exact-active-class="active"
            :to="{ name: 'MyBooks' }"
            >Мій формуляр</b-nav-item
          >
          <b-nav-item
            v-if="$store.state.user.role === 'manager'"
            exact-active-class="active"
            :to="{ name: 'Readers' }"
            >Читачі</b-nav-item
          >
          <b-nav-item
            v-if="$store.state.user.role === 'manager'"
            exact-active-class="active"
            :to="{ name: 'ManagerBooks' }"
            >Видання</b-nav-item
          >
          <b-nav-item
            v-if="$store.state.user.role === 'manager'"
            exact-active-class="active"
            :to="{ name: 'Reports' }"
            >Звіти</b-nav-item
          >
          <b-nav-item
            v-if="$store.state.user.role === 'manager'"
            exact-active-class="active"
            :to="{ name: 'ManagerSystemConfigs' }"
            >Системні налаштування</b-nav-item
          >
          <b-nav-item
            v-if="$store.state.user.role === 'admin'"
            exact-active-class="active"
            :to="{ name: 'Admin' }"
            >Менеджери</b-nav-item
          >
          <b-nav-item
            v-if="$store.state.user.role === 'admin'"
            exact-active-class="active"
            :to="{ name: 'system-configs' }"
            >Системні налаштування</b-nav-item
          >
        </b-navbar-nav>

        <!-- Right aligned nav items -->
        <b-navbar-nav class="ml-auto">
          <b-nav-item-dropdown text="Профіль" right>
            <b-dropdown-item href="#" v-if="!$store.state.user.authorized">
              <router-link :to="{ name: 'LoginPage' }"
                >Вхід / Реєстрація</router-link
              ></b-dropdown-item
            >
            <div v-if="$store.state.user.authorized">
              <b-dropdown-item href="#">
                <router-link :to="{ name: 'UserProfile' }">Профіль</router-link>
              </b-dropdown-item>
              <b-dropdown-item href="#" @click="logout">
                <a href="javascript:void(0)">Вийти</a></b-dropdown-item
              >
            </div>
          </b-nav-item-dropdown>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
  </div>
</template>

<script>
export default {
  methods: {
    logout() {
      this.$store.dispatch("logout");
      this.goToLoginPage();
    },
    goToLoginPage() {
      this.$router.push({ name: "LoginPage" });
    },
  },
  created() {
    const self = this;
    // todo fix user data client storage persistense in another way
    setInterval(function () {
      self.$store.dispatch("updateCreds");
    }, 5000);
  },
};
</script>

<style>
/* removing dotted outline around the menu button */
.navbar-toggler:focus,
.navbar-toggler:active,
.navbar-toggler-icon:focus {
  outline: none;
  box-shadow: none;
}
</style>
