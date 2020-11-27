export default {
  data() {
    return {
      dict: {
        available: "Доступна",
        "on hold": "ШО", //todo
        "non-circulating": "Не видається",
      },
    };
  },
  methods: {
    ce_status_en2ua(status) {
      if (this.dict[status.status.toLowerCase()] !== undefined) {
        return {
          id: status.id,
          status: this.dict[status.status.toLowerCase()],
        };
      } else return status;
    },
    ce_translate_statuses(statuses) {
      const self = this;
      return statuses.map((s) => self.ce_status_en2ua(s));
    },
    ce_translate_status(catalogEntry) {
      catalogEntry.status = this.ce_status_en2ua(catalogEntry);
    },
    ce_transform_response(response) {
      return response;
    },
  },
};