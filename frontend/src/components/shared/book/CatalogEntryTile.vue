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
