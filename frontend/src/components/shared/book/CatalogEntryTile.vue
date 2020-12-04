<template>
  <div @click="$emit('click', $event)">
    <b-card>
      <b-container>
        <b-row>
          <b-col cols="12" md="3" style="background-color: white">
            <div class="book-tile-img text-center">
              <b-img
                fluid
                :src="
                  catalog_entry.publication.descripiton.photo.trim().length != 0
                    ? catalog_entry.publication.descripiton.photo
                    : defaults.img_path
                "
                class="rounded"
                alt=""
              ></b-img>
            </div>
          </b-col>
          <b-col cols="12" md="9" style="background-color: white">
            <h4>
              {{ catalog_entry.publication.title }}
            </h4>
            <h5>Автор: {{ catalog_entry.publication.creator }}</h5>
            <!-- download a book button todo-->
            <div v-if="user_role == 'reader'">
              <!-- can take a book! buttons-->
            </div>
            <b-link
              v-if="digital_version_link != null"
              @click="downloadDigitalVersion"
              >Завантажити електронну версію</b-link
            >
          </b-col>
        </b-row>
      </b-container>
      <template #footer>
        <b-container fluid>
          <b-row style="background-color: transparent">
            <b-col cols="4" md="8">
              <div>
                {{
                  catalog_entry.copies_available <= 0
                    ? "Недоступна"
                    : "У наявності"
                }}
              </div>
              <div v-if="$store.getters.userRole == 'reader'">
                <b-link
                  v-if="catalog_entry.copies_available <= 0"
                  @click="bookCatalogEntry"
                >
                  Забронювати
                </b-link>
                <b-link v-else @click="takeCatalogEntry"> Взяти книгу </b-link>
              </div>
            </b-col>
            <b-col cols="4" md="4" style="background-color: transparent">
              <b-button variant="primary" @click="openCatalogEntry" size="sm"
                >Дізнатися більше</b-button
              >
            </b-col>
          </b-row>
        </b-container>
      </template>
    </b-card>
  </div>
</template>

<script>
export default {
  props: ["catalogEntry", "userRole"],
  data() {
    return {
      catalog_entry: this.catalogEntry,
      user_role: this.userRole,
      defaults: {
        img_path: this.$globals.assets.avatars.no_photo,
      },
      digital_version_link: this.$globals.stub_ebook, //todo fetch from api
    };
  },
  methods: {
    bookCatalogEntry() {
      const self = this;
      this.$swal
        .fire({
          title:
            "Забронювти видання на " + self.catalog_entry.loan_days + " днів?",
          showDenyButton: true,
          confirmButtonText: `Так`,
          denyButtonText: `Відміна`,
        })
        .then((result) => {
          if (result.isConfirmed) {
            self.$swal.fire(
              "Заброньовано",
              "Заберіть замовлене видання протягом " +
                self.catalog_entry.loan_days +
                "днів",
              "success"
            );
          } else if (result.isDenied) {
            self.$swal.fire("Відмінено", "", "info");
          }
        });
    },
    takeCatalogEntry() {
      const self = this;
      this.$swal
        .fire({
          title: "Взяти видання на " + self.catalog_entry.loan_days + " днів?",
          showDenyButton: true,
          confirmButtonText: `Так`,
          denyButtonText: `Відміна`,
        })
        .then((result) => {
          if (result.isConfirmed) {
            // todo request
            self.$swal.fire("Отримайте видання", "", "success");
          } else if (result.isDenied) {
            self.$swal.fire("Відмінено", "", "info");
          }
        });
    },
    openCatalogEntry() {
      this.$router.push({
        name: "ReaderCatalogEntry",
        params: { id: this.catalog_entry.id },
      });
    },
    downloadDigitalVersion() {
      const url = this.digital_version_link;
      const method = "GET";

      this.$axios
        .request({
          url,
          method,
          responseType: "blob",
        })
        .then(function (response) {
          const downloadUrl = window.URL.createObjectURL(
            new Blob([response.data])
          );
          const link = document.createElement("a");
          link.href = downloadUrl;
          link.setAttribute("download", "book.pdf"); //any other extension
          document.body.appendChild(link);
          link.click();
          link.remove();
        })
        .catch(function (error) {
          console.log(error);
        });
    },
  },
  created() {
    console.log(this.catalog_entry);
  },
};
</script>

<style scoped>
* {
  word-wrap: anywhere;
}

.book-tile-img {
  max-width: 300px;
}
.book-tile-img:hover {
  cursor: pointer;
}
</style>
