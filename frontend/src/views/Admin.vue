<template>
  <div>
    on admin page
    <users-table :users="managers"></users-table>
  </div>
</template>

<script>
import UsersTable from "@/components/user/UsersTable";
export default {
  components: {
    "users-table": UsersTable,
  },
  created() {
    // todo make request for manager list
    this.managers = [
      {
        id: 1,
        name: "Vasya",
        lastName: "Pupkin",
        role: "manager",
      },
      {
        id: 2,
        name: "Petya",
        lastName: "Petrov",
        role: "manager",
      },
      {
        id: 3,
        name: "Ivan",
        lastName: "Ivanov",
        role: "manager",
      },
    ];
  },
  data() {
    return {
      managers: [],
    };
  },
};
</script>

<style></style>template
