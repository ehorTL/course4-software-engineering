export default {
  data() {
    return {};
  },
  methods: {
    get_statuses() {
      const url = this.$globals.remlib_api_host + this.$globals.get_ce_statuses;
      return this.$axios.get(url);
    },
  },
};
