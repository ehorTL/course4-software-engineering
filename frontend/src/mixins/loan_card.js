export default {
  data() {
    return {
      dict: {
        requested: "Не забрана",
        queue: "У черзі",
        "checked out": "Видана",
        "checked in": "Повернута",
      },
    };
  },
  methods: {
    loan_statuses_en2ua(statuses) {
      const self = this;
      return statuses.map((s) => {
        if (self.dict[s.status.toLowerCase()] !== undefined) {
          return {
            id: s.id,
            status: self.dict[s.status.toLowerCase()],
          };
        } else return s;
      });
    },
    loan_card_status_en2ua(status) {
      if (this.dict[status.status.toLowerCase()] !== undefined) {
        return {
          id: status.id,
          status: this.dict[status.status.toLowerCase()],
        };
      } else return status;
    },
    loan_card_transform(responseLoanCard) {
      responseLoanCard.loan_status = this.loan_card_status_en2ua(
        responseLoanCard.loan_status
      );

      return responseLoanCard;
    },
  },
};
