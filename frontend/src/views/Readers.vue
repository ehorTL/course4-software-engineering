<template>
  <div>
    <b-container class="mt-3 mb-4">
      <b-row>
        <b-col>
          <b-form inline>
            <b-input-group>
              <b-form-input placeholder="Прізвище"></b-form-input>
              <b-form-input placeholder="Ім'я"></b-form-input>
              <b-form-input placeholder="По-батькові"></b-form-input>
              <b-button variant="primary">Пошук</b-button>
            </b-input-group>
          </b-form>
          <b-form class="mt-2">
            <b-form-group label="Фільтрувати за боргом">
              <b-form-radio
                v-model="filters.fee.has_fee"
                name="hasFee"
                :value="true"
                >Має борг</b-form-radio
              >
              <b-form-radio
                v-model="filters.fee.has_fee"
                name="hasFee"
                :value="false"
                >Не має боргу</b-form-radio
              >
              <b-form-radio
                v-model="filters.fee.has_fee"
                name="hasFee"
                :value="null"
                >Не важливо</b-form-radio
              >
            </b-form-group>
            <div v-if="filters.fee.has_fee === true">
              <p>
                від<input
                  type="text"
                  class="custom-input text-center"
                  size="5"
                  v-model="filters.fee.from"
                />
                до
                <input
                  type="text"
                  class="custom-input text-center"
                  size="5"
                  v-model="filters.fee.to"
                />
              </p>
            </div>
          </b-form>
          <b-form class="mt-2">
            <b-form-group label="Фільтрувати за отриманими книжками">
              <b-form-radio
                v-model="filters.books.has_books_checked_in"
                name="hasFee"
                :value="true"
                >Має видання</b-form-radio
              >
              <b-form-radio
                v-model="filters.books.has_books_checked_in"
                name="hasFee"
                :value="false"
                >Не має видань</b-form-radio
              >
              <b-form-radio
                v-model="filters.books.has_books_checked_in"
                name="hasFee"
                :value="null"
                >Не важливо</b-form-radio
              >
            </b-form-group>
            <div v-if="filters.books.has_books_checked_in === true">
              <p>
                від<input
                  type="text"
                  class="custom-input text-center"
                  size="5"
                  v-model="filters.books.from"
                />
                до
                <input
                  type="text"
                  class="custom-input text-center"
                  size="5"
                  v-model="filters.books.to"
                />
              </p>
            </div>
          </b-form>
        </b-col>
      </b-row>
    </b-container>
    <b-container fluid>
      <b-table striped hover :items="readers" :fields="header_fields">
        <template #cell(show_details)="row">
          <b-button
            size="sm"
            @click="readMore(row.item.email)"
            class="mr-2"
            v-b-popover.hover.top="
              'Натисніть щоб переглнути інформацію про читача'
            "
            >Детальніше
          </b-button>
        </template>
        <template #cell(actions)="row">
          <b-form-checkbox v-model="row.item.active" name="check-button" switch>
          </b-form-checkbox>
        </template>
      </b-table>
    </b-container>
    <!-- todo pagination https://bootstrap-vue.org/docs/components/pagination -->
  </div>
</template>

<script>
export default {
  data() {
    return {
      filters: {
        fee: {
          by: false,
          has_fee: null, //null if not mentioned
          from: 0,
          to: 9999,
        },
        books: {
          by: false,
          has_books_checked_in: null,
          from: 0,
          to: 20,
        },
      },
      header_fields: [
        {
          key: "name",
          label: "Ім'я",
        },
        {
          key: "patronymic",
          label: "По-батькові",
        },
        {
          key: "surname",
          label: "Прізвище",
        },
        {
          key: "fee",
          label: "Борг",
        },
        {
          key: "show_details",
          label: "Деталі",
        },
        {
          key: "actions",
          label: "Активний",
        },
      ],
      readers: [],
    };
  },
  methods: {
    getReaders(page, quanityPerPage) {
      console.log(page, quanityPerPage);
    },
    getReadersAll() {
      const self = this;
      const url = this.$globals.remlib_api_host + this.$globals.get_readers;
      this.$axios
        .get(url)
        .then((response) => {
          self.readers = self.mapReaders(response.data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    deactivateReader(index, id) {
      console.log(index, id);
      this.readers[index].active = !this.readers[index].active;
    },
    readMore(readerId) {
      console.log(readerId);

      this.$router.push({ name: "ReaderInfo", params: { id: readerId } });
    },
    mapReaders(readersResponse) {
      return readersResponse.map((reader) => {
        return {
          name: reader.name,
          patronymic: reader.patronymic,
          surname: reader.surname,
          email: reader.email,
          role: reader.role.role,
          phone: reader.phone,
          detailsShowing: false,
          fee: 0,
          active: true,
        };
      });
    },
  },
  created() {
    this.getReadersAll();
    // this.getReaders(1, this.$store.state.global_configs.readers_per_page);
  },
};
</script>

<style scoped>
.btn-on {
  background-color: #00c851;
}
.btn-off {
  background-color: #ff4444;
}
.custom-input {
  outline: none !important;
  border: none;
  background-color: transparent;
  border-bottom: 0.5px solid grey;
}
</style>
