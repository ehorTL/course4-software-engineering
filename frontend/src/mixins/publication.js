export default {
  data() {
    return {
      publication_default: {
        identifier: "new",
        title: "Назва публікації",
        subject: {
          id: -1,
          name: "Предмет",
        },
        descripiton: {
          text: "Опис",
          photo: "",
        },
        creator: "Автор",
        source: "source",
        publisher: "publisher",
        publication_date: "date past",
        contributor: "conttibutor",
        rights: "rights",
        format: "Формат публікації",
        language: "ua",
        type: {
          id: -1,
          type: "Тип",
        },
        edition: "edition",
      },
    };
  },
  methods: {
    getPublicationById(id) {
      const url =
        this.$globals.remlib_api_host +
        this.$globals.get_publication_by_id +
        id;
      return this.$axios.get(url);
    },
    getPublications() {
      const url =
        this.$globals.remlib_api_host + this.$globals.get_publications;
      return this.$axios.get(url);
    },
    getPublicationTypes() {
      const url =
        this.$globals.remlib_api_host + this.$globals.get_publication_types;
      return this.$axios.get(url);
    },
    getBase64(file) {
      return new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = () => resolve(reader.result);
        reader.onerror = (error) => reject(error);
      });
    },
  },
};
