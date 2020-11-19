<template>
  <div>
    <div class="container-fluid">
      <div class="row">
        <div class="col">push some info here</div>
      </div>
      <div class="row">
        <div class="col-2 left-col">
          <span>Шукати за параметрами</span>
          <b-button squared variant="outline-danger" @click="clearFilters"
            >Очистити фільтри</b-button
          >
          <div>
            <b-form-checkbox
              id="checkbox-1"
              v-model="search_options.by.title"
              name="checkbox-1"
            >
              назва
            </b-form-checkbox>
            <div>
              <!-- some extra info -->
            </div>
          </div>
          <div>
            <b-form-checkbox
              id="checkbox-2"
              v-model="search_options.by.isbn"
              name="checkbox-1"
            >
              ISBN
            </b-form-checkbox>
            <div>
              <!-- some extra info -->
            </div>
          </div>
          <div>
            <b-form-checkbox
              id="checkbox-3"
              v-model="search_options.by.author"
              name="checkbox-1"
            >
              автор
            </b-form-checkbox>
            <div>
              <!-- some extra info -->
            </div>
          </div>
        </div>
        <div class="col-8 text-center center-col">
          <div>
            <b-form inline>
              <b-form-input
                id="inline-form-input-name"
                class="mb-2 mr-sm-2 mb-sm-0"
                placeholder="Введіть назву видання..."
              ></b-form-input>
              <b-button variant="primary">Шукати</b-button>
            </b-form>
          </div>
          <div v-show="search_options.by.author === true">
            <b-form inline>
              <b-form-input
                id="inline-form-input-name"
                class="mb-2 mr-sm-2 mb-sm-0"
                placeholder="Введіть автора"
              ></b-form-input>
            </b-form>
          </div>
          <div v-show="search_options.by.isbn === true">
            <b-form inline>
              <b-form-input
                id="inline-form-input-name"
                class="mb-2 mr-sm-2 mb-sm-0"
                placeholder="Введіть ISBN видання"
              ></b-form-input>
            </b-form>
          </div>
        </div>
        <div class="col-2 right-col">
          three cards of top-3 books or ads
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      search_options: {
        // boolean
        by: {
          title: "",
          isbn: "",
          i_dont_now: "",
          author: "",
        },
      },
    };
  },
  methods: {
    clearFilters() {
      this.search_options.by.title = false;
      this.search_options.by.isbn = false;
      this.search_options.by.author = false;
      this.search_options.by.i_dont_now = true;
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
