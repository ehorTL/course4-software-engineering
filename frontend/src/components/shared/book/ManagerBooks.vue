<template>
  <b-container fluid>
    <b-row>
      <b-col cols="12" md="3" lg="2">
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
      <b-col cols="12" md="9" lg="10">
        <b-form>
          <b-form-row>
            <b-col md="10" class="mt-2">
              <b-form-input type="text"></b-form-input>
            </b-col>
            <b-col md="2" class="mt-1">
              <b-button size="sm" variant="primary">Пошук</b-button>
            </b-col>
          </b-form-row>
        </b-form>
        <div class="mt-5">
          <b-button-group size="sm" class="mt-3">
            <b-button @click="addPublication" variant="primary"
              >Додати публікацію</b-button
            >
            <b-button @click="refreshCatalogEntriesList"
              >Оновити список</b-button
            >
          </b-button-group>
          <b-table
            striped
            hover
            :items="publications"
            :fields="fields"
            responsive
          >
            <template #cell(show_details)="row">
              <b-button
                size="sm"
                @click="row.toggleDetails"
                class="mr-2"
                v-b-popover.hover.top="'Інформація про публікацію'"
                >Детальніше
              </b-button>
            </template>
            <template #cell(remove_publication)="row">
              <b-button
                size="sm"
                class="mr-2"
                variant="danger"
                @click="deletePublication(row.index)"
              >
                <b-icon icon="trash"></b-icon>
              </b-button>
            </template>
            <template #row-details="row">
              <b-container fluid>
                <b-row>
                  <b-col>
                    <div class="book-tile-img">
                      <b-img
                        fluid
                        :src="
                          row.item.descripiton.photo.trim().length != 0
                            ? row.item.descripiton.photo
                            : $globals.assets.avatars.no_photo
                        "
                        alt=""
                      />
                    </div>
                  </b-col>
                  <b-col
                    ><router-link
                      :to="{
                        name: 'ManagerBook',
                        params: {
                          publicationId: row.item.identifier,
                        },
                      }"
                      >Редагувати</router-link
                    ></b-col
                  >
                </b-row>
              </b-container>
            </template>
          </b-table>
        </div>
      </b-col>
    </b-row>
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
  </b-container>
</template>

<script>
import publicationMixin from "@/mixins/publication";

export default {
  mixins: [publicationMixin],
  data() {
    return {
      fields: [
        {
          key: "identifier",
          label: "ISBN",
        },
        {
          key: "title",
          label: "Назва книги",
        },
        {
          key: "creator",
          label: "Автор",
        },
        {
          key: "show_details",
          label: "Деталі",
        },
        {
          key: "remove_publication",
          label: "Видалити",
        },
      ],
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
      publications: [],
    };
  },
  methods: {
    refreshCatalogEntriesList() {
      this.getPublicationsAll();
    },
    deletePublication(publIndex) {
      console.info(publIndex);
      // axios request todo and then
      this.publications.splice(publIndex, 1);
    },
    getPublicationsAll() {
      const self = this;
      this.getPublications()
        .then((response) => {
          self.publications = response.data;
        })
        .catch((error) => {
          console.log(error);
        });
    },
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
      this.publications.unshift({
        identifier: "new",
        title: "Назва книги",
        creator: "Автор",
        descripiton: {
          photo: this.$globals.assets.avatars.no_photo,
        },
      });
    },
    openDetails() {},
  },
  created() {
    this.getPublicationsAll();
  },
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