<template>
  <b-container fluid>
    <b-row>
      <b-col md="6" sm="12">
        <b-row>
          <b-col>
            <div class="publication-img">
              <b-img
                fluid
                v-if="publication.descr_photo"
                :src="publication.descr_photo"
              ></b-img>
              <b-img
                v-else
                :src="$store.state.publication.default_image"
                fluid
              ></b-img>
            </div>
          </b-col>
          <b-col>
            <p>Title: {{ publication.title }}</p>
            <p>Subject: {{ publication.subject_id }}</p>
            <p>Creator: {{ publication.creator }}</p>
            <p>Source: {{ publication.source }}</p>
            <p>Publisher: {{ publication.publisher }}</p>
            <p>Publication date: {{ publication.publication_date }}</p>
            <p>Contributor: {{ publication.contributor }}</p>
            <p>Rights: {{ publication.rights }}</p>
            <p>Format: {{ publication.format }}</p>
            <p>Language: {{ publication.language }}</p>
            <p>Type: {{ publication.type_id }}</p>
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
              v-model="publication.subject_id"
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
              v-model="publication.publ_type"
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
            <b-form-input
              class="mt-2"
              type="text"
              v-model="publication.descr_text"
            ></b-form-input>
          </b-form-group>
          <b-button @click="saveChanges" variant="primary" class="mt-3"
            >Зберегти</b-button
          >
        </b-form>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
export default {
  props: ["bookId"],
  data() {
    return {
      publication: {
        id: "",
        title: "",
        subject_id: "",
        descr_text: "",
        descr_photo: null,
        creator: "",
        source: "",
        publisher: "",
        publication_date: "",
        contributor: "",
        rights: "",
        format: "",
        language: "",
        type_id: 1, // not used
        publ_type: null,
        edition: "",
      },
      publication_types: [],
      publication_subjects: [],
    };
  },
  created() {
    this.publication_types = this.$store.state.publication.types.map((t) => {
      return {
        value: t.id,
        text: t.name,
      };
    });
    // for (let t of this.publication_types) {
    //   if (t.value == this.publication.type_id) {
    //     this.publication.publ_type = t;
    //   }
    // }
  },
  methods: {
    saveChanges() {
      // todo
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