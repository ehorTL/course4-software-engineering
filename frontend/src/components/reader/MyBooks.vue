<template>
  <div>
    <b-container fluid>
      <b-table
        small
        striped
        hover
        :fields="header_fields"
        :items="loan_cards"
        responsive=""
      ></b-table>
    </b-container>
  </div>
</template>

<script>
import loanCardMixin from "@/mixins/loan_card.js";
export default {
  mixins: [loanCardMixin],
  data() {
    return {
      header_fields: [
        {
          key: "catalog_entry.publication.title",
          label: "Назва книги",
          class: "cell-1",
        },
        { key: "loan_status.status", label: "Статус" },
      ],
      loan_cards: [],
    };
  },
  created() {
    this.loan_cards = [
      {
        id: "",
        // user
        catalog_entry: {
          id: 1,
          publication: {
            identifier: "isbn1231",
            title:
              "bookbookbookbookbookbookbookbookbookbookbookbookbookbook title longlong long longlong longmn longvery",
            subject: {
              id: 1,
              name: "Physics",
            },
            descripiton: {
              // todo description??
              text: "",
              photo: "",
            },
            creator: "",
            source: "",
            publisher: "",
            publication_date: "",
            contributor: "",
            rights: "",
            format: "",
            language: "",
            type: {
              id: 1,
              type: "книга",
            },
            edition: "видання",
          },
          library: {
            id: 1,
            name: "",
            address: "",
          },
          item_number: "шев123",
          status: {
            id: "",
            status: "",
          },
          available_from: "",
          copies_number: "",
          copies_available: "",
          loan_days: "",
        },
        loan_status: {
          id: 1,
          status: "requested",
        },
        //
        checked_in_date: "",
        loan_until: "",
        available_from: "",
        notes: "",
      },
    ];

    const self = this;
    this.loan_cards = this.loan_cards.map((lc) => self.loan_card_transform(lc));
  },
  methods: {},
};
</script>

<style>
.cell-1 {
  max-width: 20vw;
  word-wrap: anywhere;
}
</style>