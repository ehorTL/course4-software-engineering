<template>
  <div>
    <b-button variant="primary" @click="addManager">Додати менеджера</b-button>
    <b-button variant="primary" @click="getManagers">Оновити список</b-button>
    <b-table striped hover :items="managers" :fields="header_fields">
      <template #cell(show_details)="row">
        <b-button size="sm" @click="row.toggleDetails" class="mr-2">
          {{ row.detailsShowing ? "Сховати" : "Детальніше" }}
        </b-button>
      </template>
      <template #cell(actions)="row">
        <b-button
          size="sm"
          @click="deleteManager(row.index, row.item.id)"
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
        <div>
          <input type="text" v-model="row.item.name" />
          <input type="text" v-model="row.item.patronymic" />
          <input type="text" v-model="row.item.surname" />
          <date-picker
            :confirm="true"
            confirm-text="Ок"
            v-model="row.item.birthday"
            valueType="format"
          ></date-picker>
          <b-form-input
            type="tel"
            v-model="row.item.phone"
            placeholder="телефон"
          ></b-form-input>
          <input type="text" v-model="row.item.email" />
          <b-form-input
            v-if="row.item.password != undefined"
            type="password"
            v-model="row.item.password"
          ></b-form-input>
          <b-form-input
            v-if="row.item.password_confirmation != undefined"
            type="password"
            v-model="row.item.phone.password_confirmation"
          ></b-form-input>
          <b-button variant="danger">{{
            row.item.newly_created ? "Створити" : "Зберегти"
          }}</b-button>
        </div>
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
    this.managers = [
      {
        id: 1,
        name: "Vasya",
        patronymic: "Vasilievich",
        surname: "Pupkin",
        email: "vasya@test.test",
        role: "manager",
        detailsShowing: false,
        phone: "",
      },
      {
        id: 2,
        name: "Petya",
        patronymic: "Vasilievich",
        surname: "Petrov",
        email: "petya@test.test",
        role: "manager",
        detailsShowing: false,
        phone: "",
      },
    ];
  },
  data() {
    return {
      header_fields: [
        "id",
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
    deleteManager(index, id) {
      // if (axios.....) deleted then remove from the list or fetch all again
      this.managers.splice(index, 1);
      alert("deleted, ID: " + id);
    },
    addManager() {
      this.managers.unshift({
        id: "?",
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
    saveManager() {
      // todo axios...
      // update id
      // ask for confirmation
    },
    getManagers() {
      // todo axios....
    },
  },
};
</script>

<style></style>
