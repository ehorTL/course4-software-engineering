<template>
  <div class="mt-2">
    <b-button-group size="sm" class="">
      <b-button variant="primary" @click="addManager"
        >Додати менеджера</b-button
      >
      <b-button variant="info" @click="getManagers">Оновити список</b-button>
    </b-button-group>
    <b-table striped hover :items="managers" :fields="header_fields">
      <template #cell(show_details)="row">
        <b-button size="sm" @click="row.toggleDetails" class="mr-2">
          {{ row.detailsShowing ? "Сховати" : "Детальніше" }}
        </b-button>
      </template>
      <template #cell(actions)="row">
        <b-button
          size="sm"
          @click="deleteManager(row.index, row.item.email)"
          class="mr-2"
          v-b-popover.hover.top="'Натисніть щоб видалити менеджера'"
        >
          <svg
            width="1em"
            height="1em"
            viewBox="0 0 16 16"
            class="bi bi-trash-fill"
            fill="currentColor"
            xmlns="http://www.w3.org/2000/svg"
          >
            <path
              fill-rule="evenodd"
              d="M2.5 1a1 1 0 0 0-1 1v1a1 1 0 0 0 1 1H3v9a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V4h.5a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H10a1 1 0 0 0-1-1H7a1 1 0 0 0-1 1H2.5zm3 4a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 .5-.5zM8 5a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7A.5.5 0 0 1 8 5zm3 .5a.5.5 0 0 0-1 0v7a.5.5 0 0 0 1 0v-7z"
            />
          </svg>
        </b-button>
      </template>

      <template #row-details="row">
        <b-container fluid>
          <b-row>
            <b-col cols="12" md="6">
              <b-form-input type="text" v-model="row.item.name"> </b-form-input>
              <b-form-input type="text" v-model="row.item.patronymic" />
              <b-form-input type="text" v-model="row.item.surname" />
            </b-col>
            <b-col cols="12" md="6">
              <date-picker
                :confirm="true"
                confirm-text="Ок"
                v-model="row.item.birthday"
                valueType="format"
              ></date-picker>
              <b-form-input
                type="tel"
                v-model="row.item.phone"
                placeholder="Телефон"
              ></b-form-input>
              <b-form-input type="text" v-model="row.item.email" />
            </b-col>
          </b-row>
          <b-row>
            <b-col>
              <b-form-input
                v-if="row.item.password != undefined"
                type="password"
                v-model="row.item.password"
                placeholder="Введіть пароль"
              ></b-form-input>
              <b-form-input
                v-if="row.item.password_confirmation != undefined"
                type="password"
                v-model="row.item.phone.password_confirmation"
                placeholder="Повторіть пароль"
              ></b-form-input>
            </b-col>
          </b-row>
          <b-row>
            <b-col cols="4">
              <b-button
                variant="danger"
                class="mt-2"
                @click="saveManager(row.item)"
                >{{
                  row.item.newly_created ? "Створити" : "Зберегти"
                }}</b-button
              >
            </b-col>
          </b-row>
        </b-container>
      </template>
    </b-table>
  </div>
</template>

<script>
import DatePicker from "vue2-datepicker";
import "vue2-datepicker/index.css";
import "vue2-datepicker/locale/uk";

export default {
  components: { "date-picker": DatePicker },
  created() {
    this.getManagers();
  },
  data() {
    return {
      header_fields: [
        "name",
        "patronymic",
        "surname",
        "show_details",
        "actions",
      ],
      managers: [],
    };
  },
  methods: {
    deleteManager(index, email) {
      // if (axios.....) deleted then remove from the list or fetch all again
      this.managers.splice(index, 1);
      console.log(email + " " + "deleted");
    },
    addManager() {
      this.managers.unshift({
        name: "Імя",
        patronymic: "По-батькові",
        surname: "Прізвище",
        email: "poshta@knu.ua",
        role: "manager",
        phone: "",
        detailsShowing: false,
        newly_created: true,
        password: "",
        password_confirmation: "",
      });
    },
    createManager(manager) {
      const url = this.$globals.remlib_api_host + this.$globals.add_manager;
      return this.$axios.post(url, manager);
    },
    updateManager(manager) {
      const url =
        this.$globals.remlib_api_host +
        this.$globals.add_manager +
        "/" +
        manager.email;
      return this.$axios.put(url, manager);
    },
    saveManager(manager) {
      // ask for confirmation
      let mgr = {
        email: manager.email,
        name: manager.name,
        surname: manager.surname,
        patronymic: manager.patronymic,
        birthday: manager.birthday,
        phone: manager.phone,
        role: {
          id: "2",
          name: "manager",
        },
        fee: 0,
        password: manager.password,
        password_confirmation: manager.password_confirmation,
      };

      // wrap in promise, handle response todo
      if (manager.newly_created) {
        this.createManager(mgr);
      } else {
        this.updateManager(mgr);
      }
    },
    getManagers() {
      const url = this.$globals.remlib_api_host + this.$globals.managers;
      const self = this;
      this.$axios
        .get(url)
        .then(function (response) {
          self.managers = response.data.map((mgr) => {
            return {
              ...mgr,
              detailsShowing: false,
            };
          });
        })
        .catch(function (error) {
          console.log(error);
        });
    },
  },
};
</script>

<style></style>
