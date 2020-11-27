import axios from "axios";
import { const_globals as globals } from "@/helpers/globals";

export default {
  data() {
    return {};
  },
  methods: {
    getSubjects() {
      const url = globals.remlib_api_host + globals.publication_subjects;
      return axios.get(url);
    },
  },
};
