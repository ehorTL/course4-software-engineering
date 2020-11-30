<template>
  <div>
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

    <!-- todo pagination https://bootstrap-vue.org/docs/components/pagination -->
  </div>
</template>

<script>
export default {
  data() {
    return {
      header_fields: [
        "name",
        "patronymic",
        "surname",
        "fee",
        "show_details",
        "actions",
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
</style>
