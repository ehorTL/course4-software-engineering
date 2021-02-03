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
      >
        <template #cell(catalog_entry.publication.title)="data">
          <router-link
            :to="{
              name: 'ReaderCatalogEntry',
              params: { id: loan_cards[data.index].catalog_entry.id },
            }"
            >{{ data.value }}</router-link
          >
        </template>
      </b-table>
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
    this.getReaderRelatedLoanCards();
  },
  methods: {
    getReaderRelatedLoanCards() {
      const self = this;
      const readerId = this.$store.getters.savedUserId;
      this.getReaderLoanCards({ email: readerId })
        .then((response) => {
          self.loan_cards = response.data;
          self.loan_cards = self.loan_cards.map((lc) =>
            self.loan_card_transform(lc)
          );
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};
</script>

<style>
.cell-1 {
  max-width: 20vw;
  word-wrap: anywhere;
}
</style>