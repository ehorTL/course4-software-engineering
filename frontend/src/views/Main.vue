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
  created() {
    console.log("hello");
  },
};
</script>

<style>
.footer-style {
  min-height: 10vh;
}
.header-style {
  min-height: 20vh;
}
.center-style {
  min-height: 70vh;
}
</style>
