<template>
  <div>
    <b-container fluid v-if="publication">
      <b-row>
        <b-col md="6" sm="12">
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
            </b-col>
            <b-col>
              <p>Title: {{ publication.title }}</p>
              <p>Creator: {{ publication.creator }}</p>
              <p>Subject: {{ publication.subject.name }}</p>
              <p>Source: {{ publication.source }}</p>
              <p>Publisher: {{ publication.publisher }}</p>
              <p>Publication date: {{ publication.publication_date }}</p>
              <p>Contributor: {{ publication.contributor }}</p>
              <p>Rights: {{ publication.rights }}</p>
              <p>Format: {{ publication.format }}</p>
              <p>Language: {{ publication.language }}</p>
              <p>Type: {{ publication.type.type }}</p>
              <p>Edition: {{ publication.edition }}</p>
            </b-col>
          </b-row>
          <!-- <div class="publication-passport"></div> -->
        </b-col>
        <b-col md="6" sm="12">
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
            <b-form-file
              v-model="file_digital_version"
              :state="Boolean(file_digital_version)"
              placeholder="Choose a file or drop it here..."
              drop-placeholder="Drop file here..."
            ></b-form-file>
            <b-button @click="saveChanges" variant="primary" class="mt-3"
              >Зберегти</b-button
            >
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
          </b-button-group>
          <b-table
            :fields="catalog_entries.fields"
            :items="catalog_entries.catalog_entries_related"
          ></b-table>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import publicationMixin from "@/mixins/publication";
import catalogEntryMixin from "@/mixins/catalog_entry";

export default {
  mixins: [publicationMixin, catalogEntryMixin],
  props: ["publicationId"],
  data() {
    return {
      publication: null,

      file_digital_version: "",
      publication_types: [],
      publication_subjects: [],
      publ_type_new: null, //remove

      catalog_entries: {
        fields: [
          "id",
          "item_number",
          "copies",
          "available_from",
          "loan_days",
          "more",
          "delete",
        ],
        catalog_entries_related: [
          {
            id: 1,
            publication_id: 4,
            library_id: 12,
            item_number: 1212,
            status_id: 12,
            available_from: "",
            copies_number: "",
            copies_available: "",
            loan_days: "",
          },
        ],
      },
    };
  },
  created() {
    this.setUpPublication();
    this.setUpPublicationTypes();
    this.setUpRelatedCatalogEntries();
  },
  methods: {
    setUpRelatedCatalogEntries() {
      const self = this;
      this.get_ce_by_publication_id(this.publicationId)
        .then(function (response) {
          self.catalog_entries_related = response.data;
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    refreshCatalogEntries() {
      // todo
      console.log("refreshed");
    },
    setUpPublication() {
      const self = this;
      this.getPublicationById(this.publicationId)
        .then((response) => {
          self.publication = response.data;
        })
        .catch((error) => {
          console.error(error);
        });
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
      // todo
    },
    addCatalogEntry() {
      this.catalog_entries.catalog_entries_related.unshift({
        id: "new",
        publication_id: 4,
        library_id: 12,
        item_number: 1212,
        status_id: 12,
        available_from: "",
        copies_number: "",
        copies_available: "",
        loan_days: "",
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
</style>