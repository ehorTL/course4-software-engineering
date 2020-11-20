<template>
  <div class="container-fluid">
    <div class="row">
      <div class="col-2">
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
      </div>
      <div class="col-10">
        <b-form>
          <b-form-row>
            <b-col md="10">
              <b-form-input type="text"></b-form-input>
            </b-col>
            <b-col md="2">
              <b-button>Пошук</b-button>
            </b-col>
          </b-form-row>
        </b-form>
        <div class="mt-5">
          <b-button @click="addPublication" variant="primary"
            >Додати публікацію</b-button
          >
          <b-table striped hover :items="books" :fields="fields" responsive>
            <template #cell(show_details)="row">
              <b-button
                size="sm"
                @click="row.toggleDetails"
                class="mr-2"
                v-b-popover.hover.top="
                  'Натисніть щоб переглнути інформацію про публікацію'
                "
                >Детальніше
              </b-button>
            </template>
            <template #row-details="row">
              <b-container fluid>
                <b-row>
                  <b-col>
                    <div class="book-tile-img">
                      <b-img
                        fluid
                        src="https://storage.fabulae.ru/images/authors/10538/foto_96852.jpg"
                        alt=""
                      />
                    </div>
                  </b-col>
                  <b-col
                    ><router-link :to="{ name: 'ManagerBook' }"
                      >Редагувати</router-link
                    ></b-col
                  >
                </b-row>
              </b-container>
            </template>
          </b-table>
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col">
        <b-pagination
          v-model="state.current_page"
          pills
          :total-rows="state.pages"
          :per-page="state.per_page"
          limit="8"
          align="center"
        ></b-pagination>
        <div class="d-flex justify-content-center">
          <div style="width: 10vw" class="d-inline-block">
            <b-form-input
              type="number"
              min="1"
              :max="state.pages / state.per_page"
              v-model="state.go_to_page"
            ></b-form-input>
          </div>
          <b-button variant="primary" class="d-inline-block" @click="goToPage"
            >Go</b-button
          >
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      fields: ["id", "title", "author", "show_details"],
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
      books: [
        {
          id: 12,
          title: "Invisible Man",
        },
      ],
    };
  },
  methods: {
    getBooks() {},
    filterBooks() {},
    goToPage() {
      this.goToPageNumber(this.state.go_to_page);
    },
    goToPageNumber(pageNumber) {
      console.log(pageNumber);
    },
    clearFilters() {
      this.state.filters.by.name = false;
      this.state.filters.by.author = false;
      this.state.filters.by.isbn = false;
      this.state.filters.by.library_book_number = false;
    },
    addPublication() {
      this.books.unshift({
        id: "new",
        title: "Назва книги",
        author: "Автор",
        // todo
      });
    },
    openDetails() {},
  },
  created() {},
};
</script>

<style scoped>
.clear-btn {
  font-size: 12px;
  color: red;
  text-decoration: underline;
}
.clear-btn:hover {
  cursor: pointer;
}
.book-tile-img {
  max-width: 150px;
}
</style>