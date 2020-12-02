<template>
  <div>
    <under-header></under-header>
    <b-container fluid>
      <b-row>
        <b-col cols="12" md="3">
          <b-card
            class="shadow-sm"
            style="width: unset !important; word-wrap: anywhere"
          >
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
            <!-- todo set make font size smaller! -->
            <div class="mt-3">
              <multiselect
                style="font-size=12px !important;"
                v-model="query_params.subjects_selected"
                :options="subjects"
                :multiple="true"
                :clear-on-select="false"
                :preserve-search="true"
                placeholder="Виберіть предмети"
                label="name"
                track-by="name"
                :max="3"
                :max-height="150"
              >
              </multiselect>
            </div>

            <div class="clear-btn text-center mt-4" @click="clearFilters">
              Скинути фільтри <b-icon icon="x-square"></b-icon>
            </div>
          </b-card>
        </b-col>
        <b-col cols="12" md="7">
          <b-form>
            <b-form-row>
              <b-col cols="12" class="mt-1">
                <b-form-input
                  v-if="state.filters.by.name"
                  type="text"
                  v-model="query_params.book_name"
                  placeholder="Назва видання"
                ></b-form-input>
              </b-col>
              <b-col cols="12" v-if="state.filters.by.author" class="mt-1">
                <b-form-input
                  placeholder="Автор"
                  type="text"
                  v-model="query_params.book_author"
                ></b-form-input>
              </b-col>
              <b-col cols="12" v-if="state.filters.by.isbn" class="mt-1">
                <b-form-input
                  placeholder="ISBN"
                  type="text"
                  v-model="query_params.book_isbn"
                ></b-form-input>
              </b-col>
              <b-col
                cols="12"
                v-if="state.filters.by.library_book_number"
                class="mt-1"
              >
                <b-form-input
                  placeholder="Бібліотечний номер"
                  type="text"
                  v-model="query_params.library_book_number"
                ></b-form-input>
              </b-col>
              <b-col md="12">
                <div class="text-center mt-1 mb-4">
                  <b-button
                    variant="primary"
                    @click="search"
                    style="width: 200px"
                    >Пошук</b-button
                  >
                </div>
              </b-col>
            </b-form-row>
          </b-form>
          <b-row>
            <b-col cols="12">
              <catalog-entry-tile
                class="mt-2"
                v-for="(ce, index) in catalog_entries"
                :key="index"
                :catalog-entry="ce"
                :user-role="$store.state.user.role"
              ></catalog-entry-tile>
            </b-col>
          </b-row>
        </b-col>
        <b-col cols="12" md="2">
          <publ-tile
            class="mt-3 publ-tile"
            v-for="(p, index) in popular_publications"
            :key="index"
            :publication-property="p"
            @click="searchCatalogEntriesByPublication(p)"
          ></publ-tile>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import UnderHeader from "@/components/shared/page/UnderHeader";
import subjectMixin from "@/mixins/subject";
import Multiselect from "vue-multiselect";
import CatalogEntryTile from "@/components/shared/book/CatalogEntryTile";
import PublicationSmallTile from "@/components/shared/book/PublicationSmallTile";

export default {
  components: {
    "under-header": UnderHeader,
    Multiselect,
    "catalog-entry-tile": CatalogEntryTile,
    "publ-tile": PublicationSmallTile,
  },
  mixins: [subjectMixin],
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
      query_params: {
        book_name: "",
        book_author: "",
        book_isbn: "",
        library_book_number: "",
        subjects_selected: [],
      },
      subjects: [],
      catalog_entries: [],
      popular_publications: [], // can be just random publications
    };
  },
  methods: {
    clearFilters() {
      this.state.filters.by.name = true;
      this.state.filters.by.author = false;
      this.state.filters.by.isbn = false;
      this.state.filters.by.library_book_number = false;
      this.query_params.subjects_selected = [];
    },
    search() {
      // todo axios request
    },
    fillPopularBooksSidebar() {
      // right sidebar
      const url =
        this.$globals.remlib_api_host + this.$globals.popular_publications;
      const self = this;
      this.$axios
        .get(url)
        .then(function (response) {
          // todo map??
          self.popular_publications = response.data.map((p) => {
            return p;
          });
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    setInitBooks() {
      const url =
        this.$globals.remlib_api_host + this.$globals.catalog_entries_init;
      const self = this;

      this.$axios
        .get(url)
        .then(function (response) {
          self.catalog_entries = response.data.map((ce) => {
            return ce;
          });
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    searchCatalogEntriesByPublication(publication) {
      const url =
        this.$globals.remlib_api_host +
        this.$globals.get_ce_by_pid +
        publication.identifier;

      const self = this;
      this.$axios
        .get(url)
        .then(function (response) {
          self.catalog_entries = response.data;
        })
        .catch(function (error) {
          console.log(error);
        });
    },
  },
  created() {
    const self = this;
    this.getSubjects().then(function (response) {
      self.subjects = response.data;
    });

    this.setInitBooks();
    this.fillPopularBooksSidebar();
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
.publ-tile:hover {
  cursor: pointer;
}
</style>
