<template>
  <div>
    <b-container>
      <b-row>
        <b-col cols="12" md="3">
          <!-- book photo or stub like no photo here -->
          photo here
        </b-col>
        <b-col cols="12" md="9">
          <h4>
            {{ catalog_entry.publication.title }}
          </h4>
          <h5>
            {{ catalog_entry.publication.creator }}
          </h5>
          <!-- download a book button todo-->
          <div v-if="user_role == 'reader'">
            <!-- can take a book! buttons-->
          </div>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
export default {
  props: ["catalogEntry", "userRole"],
  data() {
    return {
      catalog_entry: this.catalogEntry,
      user_role: this.userRole,
    };
  },
  methods: {},
  created() {},
};
</script>

<style scoped>
* {
  word-wrap: anywhere;
}
</style>
