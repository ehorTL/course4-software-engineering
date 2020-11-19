<template>
  <div>
    <header-main></header-main>
    <router-view />
    <footer-main></footer-main>
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

<style></style>
