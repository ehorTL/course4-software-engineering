<template>
  <div>
    <header-main class="header-style"></header-main>
    <router-view class="center-style" />
    <footer-main class="footer-style"></footer-main>
  </div>
</template>

<script>
import HeaderMain from "@/components/shared/page/Header";
import FooterMain from "@/components/shared/page/Footer";

export default {
  name: "Main",
  components: {
    "header-main": HeaderMain,
    "footer-main": FooterMain,
  },
  methods: {},
  created() {},
};
</script>

<style>
@import url("https://fonts.googleapis.com/css2?family=Comfortaa&display=swap");
* {
  background-color: #f0f2f5;
}
.footer-style {
  min-height: 10vh;
  background-color: #f0f2f5;
}
.header-style {
  min-height: 5vh;
  background-color: #f0f2f5;
}
.center-style {
  min-height: 85vh;
  background-color: #f0f2f5;
}
</style>
