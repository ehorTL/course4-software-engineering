<template>
  <div>
    <!-- todo insert edit and delete buttons -->
    <!-- todo add link to profile -->
    <b-table striped hover :items="users"></b-table>
  </div>
</template>

<script>
export default {
  props: ["users"],
};
</script>

<style></style>
