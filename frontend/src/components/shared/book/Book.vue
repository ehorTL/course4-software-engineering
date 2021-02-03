<template>
  <div>
    <b-container fluid v-if="publication" class="mt-3">
      <b-row>
        <b-col md="6" cols="12">
          <b-row>
            <b-col>
              <div class="publication-img">
                <b-img
                  v-if="publication.descripiton"
                  :src="
                    publication.descripiton.photo.trim().length != 0
                      ? publication.descripiton.photo
                      : $globals.assets.avatars.no_photo
                  "
                  fluid
                ></b-img>
              </div>
              <div class="text-center">
                <div>
                  <b-link @click="deletePhoto">Видалити фото</b-link>
                </div>
                <div>
                  <b-link @click="setPhoto">Завантажити фото</b-link>
                </div>
              </div>
            </b-col>
            <b-col>
              <p><strong>Title:</strong> {{ publication.title }}</p>
              <p><strong>Creator:</strong> {{ publication.creator }}</p>
              <p><strong>Subject:</strong> {{ publication.subject.name }}</p>
              <p><strong>Source:</strong> {{ publication.source }}</p>
              <p><strong>Publisher:</strong> {{ publication.publisher }}</p>
              <p>
                <strong>Publication date:</strong>
                {{ publication.publication_date }}
              </p>
              <p><strong>Contributor:</strong> {{ publication.contributor }}</p>
              <p><strong>Rights:</strong> {{ publication.rights }}</p>
              <p><strong>Format:</strong> {{ publication.format }}</p>
              <p><strong>Language:</strong> {{ publication.language }}</p>
              <p><strong>Type:</strong> {{ publication.type.type }}</p>
              <p><strong>Edition:</strong> {{ publication.edition }}</p>
            </b-col>
          </b-row>

          <!-- <div class="publication-passport"></div> -->
        </b-col>
        <b-col md="6" cols="12">
          <b-form>
            <b-form-group description="Title">
              <b-form-input
                type="text"
                v-model="publication.title"
              ></b-form-input>
            </b-form-group>
            <b-form-group description="Subject">
              <b-form-input
                class="mt-2"
                type="text"
                v-model="publication.subject.name"
              ></b-form-input>
            </b-form-group>
            <b-form-group description="Creator">
              <b-form-input
                class="mt-2"
                type="text"
                v-model="publication.creator"
              ></b-form-input>
            </b-form-group>
            <b-form-group description="Source">
              <b-form-input
                class="mt-2"
                type="text"
                v-model="publication.source"
              ></b-form-input>
            </b-form-group>
            <b-form-group description="Publisher">
              <b-form-input
                class="mt-2"
                type="text"
                v-model="publication.publisher"
              ></b-form-input>
            </b-form-group>
            <b-form-group description="Publication date">
              <b-form-input
                class="mt-2"
                type="text"
                v-model="publication.publication_date"
              ></b-form-input>
            </b-form-group>
            <b-form-group description="Contributor"
              ><b-form-input
                class="mt-2"
                type="text"
                v-model="publication.contributor"
              ></b-form-input
            ></b-form-group>
            <b-form-group description="Rights"
              ><b-form-input
                class="mt-2"
                type="text"
                v-model="publication.rights"
              ></b-form-input
            ></b-form-group>
            <b-form-group description="Format">
              <b-form-input
                class="mt-2"
                type="text"
                v-model="publication.format"
              ></b-form-input>
            </b-form-group>
            <b-form-group description="Language"
              ><b-form-input
                class="mt-2"
                type="text"
                v-model="publication.language"
              ></b-form-input
            ></b-form-group>
            <b-form-group description="Publication type">
              <b-form-select
                class="mt-2"
                v-model="publ_type_new"
                :options="publication_types"
              >
                <template #first>
                  <b-form-select-option :value="null" disabled
                    >Виберіть тип публікації</b-form-select-option
                  >
                </template>
              </b-form-select>
            </b-form-group>
            <b-form-group description="Edition"
              ><b-form-input
                class="mt-2"
                type="text"
                v-model="publication.edition"
              ></b-form-input
            ></b-form-group>
            <b-form-group description="Description text">
              <b-form-textarea
                v-model="publication.descripiton.text"
                placeholder="Enter description"
                rows="3"
                max-rows="6"
                class="mt-2"
              ></b-form-textarea>
            </b-form-group>
            <input
              type="file"
              style="display: none"
              ref="publicationPhotoFile"
              accept="image/jpeg,image/png,image/gif"
              @change="loadBase64Photo"
            />
            <b-form-group description="Електронна версія">
              <b-form-file
                id="file-ebook"
                size="sm"
                placeholder="Не вибрано жодного файлу"
              ></b-form-file>
            </b-form-group>

            <b-button-group size="sm" class="mt-2">
              <b-button @click="saveChanges" variant="primary"
                >Зберегти</b-button
              >
              <b-button variant="danger" @click="refreshPublication"
                >Скинути зміни</b-button
              >
            </b-button-group>
          </b-form>
        </b-col>
      </b-row>
    </b-container>
    <b-container fluid class="mt-5">
      <b-row>
        <b-col md="12">
          <b-button-group size="sm">
            <b-button variant="primary" @click="addCatalogEntry"
              >Додати видання</b-button
            >
            <b-button @click="refreshCatalogEntries">Оновити список</b-button>
            <b-button @click="saveCatalogEntriesList" variant="success"
              >Зберегти зміни</b-button
            >
          </b-button-group>
          <b-table
            :fields="catalog_entries.fields"
            :items="catalog_entries.catalog_entries_related"
          >
            <template #cell(status)="data"> {{ data.value.status }} </template>
            <template #cell(more)="row">
              <b-link @click="row.toggleDetails"
                ><b-icon icon="arrow-down-square"></b-icon
              ></b-link>
            </template>
            <template #cell(delete_ce)="row">
              <b-link @click="deleteCatalogEntry(row.index)">
                <b-icon icon="trash"></b-icon>
              </b-link>
            </template>
            <template #row-details="row">
              <b-form>
                <b-form-group description="Бібліотечний номер">
                  <b-form-input
                    type="text"
                    placeholder="Бібліотечний номер"
                    class="mt-1"
                    v-model="row.item.item_number"
                  ></b-form-input>
                </b-form-group>
                <b-form-group description="Копій">
                  <b-form-input
                    type="number"
                    placeholder="Копій"
                    class="mt-1"
                    v-model="row.item.copies_number"
                  ></b-form-input>
                </b-form-group>
                <b-form-group description="Статус">
                  <b-form-select
                    v-model="row.item.status"
                    :options="ce_statuses"
                    class="mb-3"
                  ></b-form-select>
                </b-form-group>
                <b-form-group description="Максимум днів">
                  <b-form-input
                    type="number"
                    placeholder="Максимум днів"
                    class="mt-1"
                    v-model="row.item.loan_days"
                    min="0"
                  ></b-form-input>
                </b-form-group>
              </b-form>
            </template>
          </b-table>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import publicationMixin from "@/mixins/publication";
import catalogEntryMixin from "@/mixins/catalog_entry";
import libraryMixin from "@/mixins/library";
import ceStatusesMixin from "@/mixins/ce_statuses";

export default {
  mixins: [publicationMixin, catalogEntryMixin, libraryMixin, ceStatusesMixin],
  props: ["publicationId"],
  data() {
    return {
      publication: null,
      ce_statuses: [],

      file_digital_version: null,
      publication_types: [],
      publication_subjects: [],
      publ_type_new: null, //remove

      libraries: [],
      catalog_entries: {
        fields: [
          {
            key: "id",
            label: "ID",
          },
          {
            key: "item_number",
            label: "Номер",
          },
          {
            key: "copies_number",
            label: "Копій",
          },
          {
            key: "loan_days",
            label: "Макс. днів",
          },
          {
            key: "status",
            label: "Статус",
          },
          {
            key: "more",
            label: "Більше",
          },
          {
            key: "delete_ce",
            label: "Видалити",
          },
        ],
        catalog_entries_related: [],
      },
    };
  },
  created() {
    this.setUpPublication();
    this.setUpPublicationTypes();
    this.setUpRelatedCatalogEntries();
    this.setAllStatuses();
    // this.getLibrariesAll();
  },
  methods: {
    setAllStatuses() {
      const self = this;
      this.get_statuses()
        .then((response) => {
          self.ce_statuses = response.data.map((status) => {
            return {
              text: status.status,
              value: status,
            };
          });
        })
        .catch((error) => {
          console.log(error);
        });
    },
    loadBase64Photo() {
      const self = this;
      let file = this.$refs.publicationPhotoFile.files[0];
      self.getBase64(file).then((data) => {
        self.publication.descripiton.photo = data;
      });
    },
    setPhoto() {
      this.$refs.publicationPhotoFile.click();
    },
    deletePhoto() {
      this.publication.descripiton.photo = "";
    },
    getLibrariesAll() {
      const self = this;
      this.get_libraries()
        .then((response) => {
          self.libraries = response.data;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    saveCatalogEntriesList() {
      //todo save all entries
      const self = this;
      this.$swal
        .fire({
          title: "Зберегти зміни?",
          showDenyButton: true,
          confirmButtonText: `Так`,
          denyButtonText: `Ні`,
        })
        .then((result) => {
          if (result.isConfirmed) {
            self.$swal.fire("Зміни збережено", "", "success");
            // after request promise was resolves
            self.refreshCatalogEntries();
          } else if (result.isDenied) {
            self.$swal.fire("Відмінено", "", "info");
          }
        });
    },
    refreshPublication() {
      const self = this;
      this.$swal
        .fire({
          title: "Скинути зміни?",
          text: "Усі не збережені зміни будуть втрачені",
          showDenyButton: true,
          confirmButtonText: `Так`,
          denyButtonText: `Відміна`,
        })
        .then((result) => {
          if (result.isConfirmed) {
            self.setUpPublication();
          }
        });
    },
    deleteCatalogEntry(catalogEntryIndex) {
      // id must be replaced with more representative ID
      this.catalog_entries.catalog_entries_related.splice(catalogEntryIndex, 1);
      console.log("deleted");
    },
    setUpRelatedCatalogEntries() {
      const self = this;
      this.get_ce_by_publication_id(this.publicationId)
        .then(function (response) {
          self.catalog_entries.catalog_entries_related = response.data;
        })
        .catch(function (error) {
          self.catalog_entries.catalog_entries = [];
        });
    },
    refreshCatalogEntries() {
      const self = this;
      this.$swal
        .fire({
          title: "Оновити список?",
          text: "Не збережені зміни будуть втрачені",
          showDenyButton: true,
          confirmButtonText: `Так`,
          denyButtonText: `Відміна`,
        })
        .then((result) => {
          if (result.isConfirmed) {
            self.setUpRelatedCatalogEntries();
            self.$swal.fire("Оновлено", "", "success");
          }
        });
    },
    setUpPublication() {
      const self = this;
      this.getPublicationById(this.publicationId)
        .then((response) => {
          self.publication = response.data;
        })
        .catch((error) => {
          self.setUpPublicationDefault();
        });
    },
    setUpPublicationDefault() {
      this.publication = this.publication_default;
    },
    setUpPublicationTypes() {
      const self = this;
      this.getPublicationTypes()
        .then((response) => {
          self.publication_types = response.data.map((t) => {
            return {
              value: t.id,
              text: t.name,
            };
          });
        })
        .catch((e) => {
          console.error(e);
        });
    },
    saveChanges() {
      //todo request
      this.$swal
        .fire({
          title: "Зберегти зміни?",
          showDenyButton: true,
          confirmButtonText: `Так`,
          denyButtonText: `Відміна`,
        })
        .then((result) => {
          if (result.isConfirmed) {
            // send update request
          }
        });
    },
    addCatalogEntry() {
      this.catalog_entries.catalog_entries_related.unshift({
        id: "new",
        publication: this.publication,
        library: {
          //todo fetch libraries, and set any from the list
          id: 1,
          name: "",
          address: "",
        },
        item_number: "lib_num",
        status: {
          //todo fix status
          id: 1,
          status: "status",
        },
        available_from: "",
        copies_number: 0,
        copies_available: 0,
        loan_days: 0,
      });
    },
  },
};
</script>
<style scoped>
.publication-passport {
  border-radius: 2px;
  margin: 15px;
  padding: 10px;
  background-color: #fff;
}
.publication-img {
  max-width: 200px;
  padding-left: 15px;
}
/* .remove-photo-button {
  position: absolute;
  top: 0px;
  right: 0px;
  z-index: 100;
} */
</style>