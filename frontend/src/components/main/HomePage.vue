<template>
  <div>
    <b-container fluid>
      <b-row>
        <b-col cols="12" md="3">
          <b-card class="shadow-sm" style="width: unset !important">
            <div>Фільтри</div>
            <b-form-checkbox v-model="state.filters.by.name"
              >Назва</b-form-checkbox
            >
            <b-form-checkbox v-model="state.filters.by.author"
              >Автор</b-form-checkbox
            >
            <b-form-checkbox v-model="state.filters.by.isbn"
              >ISBN</b-form-checkbox
            >
            <b-form-checkbox v-model="state.filters.by.library_book_number"
              >Бібліотечний номер</b-form-checkbox
            >
            <div class="clear-btn text-center" @click="clearFilters">
              Очистити фільтри <b-icon icon="x-square"></b-icon>
            </div>
          </b-card>
        </b-col>
        <b-col cols="12" md="7">
          <b-form>
          <b-form-row>
            <b-col md="10">
              <b-form-input type="text"></b-form-input>
            </b-col>
            <b-col md="2">
              <b-button variant="primary">Пошук</b-button>
            </b-col>
          </b-form-row>
        </b-form>
        </b-col>
        <b-col cols="12" md="3">fd</b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
export default {
  data() {
    return {
      state: {
        pages: 100,
        current_page: 1,
        per_page: 3,
        go_to_page: 1,
        filters: {
          by: {
            name: true,
            author: false,
            isbn: false,
            library_book_number: false,
          },
        },
      },
    };
  },
  methods: {
    clearFilters() {
      this.state.filters.by.name = true;
      this.state.filters.by.author = false;
      this.state.filters.by.isbn = false;
      this.state.filters.by.library_book_number = false;
    },
  },
};
</script>

<style>
.left-col {
  background-color: bisque;
}
.center-col {
  background-color: darkgray;
}
.right-col {
  background-color: burlywood;
}
</style>

<style scoped>
.clear-btn {
  font-size: 12px;
  color: red;
  text-decoration: underline;
}
.clear-btn:hover {
  cursor: pointer;
}
</style>
