<template>
  <div>
    <b-container fluid>
      <b-row>
        <b-col>
          {{ catalog_entry.publication.title }}
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
export default {
  data() {
    return {
      catalog_entry: null,
    };
  },
  methods: {
    getCatalogEntry(id) {
      const url =
        this.$globals.remlib_api_host + this.$globals.get_ce_by_id + id;
      return this.$axios.get(url);
    },
  },
  created() {
    const self = this;
    this.getCatalogEntry(this.$route.params.id)
      .then(function (response) {
        self.catalog_entry = response.data;
      })
      .catch(function (error) {
        console.log(error);
      });
  },
};
</script>

<style>
</style>