<template>
  <div>
    <users-table :users="readers"></users-table>
  </div>
</template>

<script>
import UsersTable from "@/components/user/UsersTable";

export default {
  components: {
    "users-table": UsersTable,
  },
  data() {
    return {
      readers: [],
    };
  },
};
</script>

<style></style>tmeplate
