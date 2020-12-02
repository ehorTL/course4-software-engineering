export default {
  data() {
    return {};
  },
  methods: {
    getPublicationById(id) {
      const url =
        this.$globals.remlib_api_host +
        this.$globals.get_publication_by_id +
        id;
      return this.$axios.get(url);
    },
    getPublications() {
      const url =
        this.$globals.remlib_api_host + this.$globals.get_publications;
      return this.$axios.get(url);
    },
    getPublicationTypes() {
      const url =
        this.$globals.remlib_api_host + this.$globals.get_publication_types;
      return this.$axios.get(url);
    },
  },
};
