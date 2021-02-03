export default {
  data() {
    return {};
  },
  methods: {
    get_libraries() {
      const url = this.$globals.remlib_api_host + this.$globals.get_libraries;
      return this.$axios.get(url);
    },
  },
};
