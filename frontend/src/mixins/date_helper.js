export default {
  data() {
    return {};
  },
  methods: {
    addDaysToDay(from, daysNumber) {
      //   from = "2012:06:20";
      return this.$moment(from, "YYYYMMDD")
        .add(daysNumber, "days")
        .calendar();
    },
    dateToFormat(date) {
      return this.$moment(date, "YYYYMMDD").calendar();
    },
  },
};
