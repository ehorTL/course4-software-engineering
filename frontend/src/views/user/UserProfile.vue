<template>
  <div>
    <div class="container-fluid">
      <div class="row">
        <div class="col-6">
          <b-card>
            <div class="text-center">Картка користувача</div>
            <div>
              {{ $store.state.user.firstName + " " + $store.state.user.secondName + " " + $store.state.user.lastName }}
            </div>
            <div>
              {{ $store.state.user.birthday }}
            </div>
            <div>Tel: {{ $store.state.user.phone }}</div>
            <div>Email: {{ $store.state.user.email }}</div>
            <div>Книжок на руках: {{ reader.books_on_hands }}</div>
            <div class="text-center">
              <a
                href="javascript:void(0)"
                @click="state.edit_opened = !state.edit_opened"
                >Редагувати
                <svg
                  width="1em"
                  height="1em"
                  viewBox="0 0 16 16"
                  class="bi bi-pen"
                  fill="currentColor"
                  xmlns="http://www.w3.org/2000/svg"
                >
                  <path
                    fill-rule="evenodd"
                    d="M13.498.795l.149-.149a1.207 1.207 0 1 1 1.707 1.708l-.149.148a1.5 1.5 0 0 1-.059 2.059L4.854 14.854a.5.5 0 0 1-.233.131l-4 1a.5.5 0 0 1-.606-.606l1-4a.5.5 0 0 1 .131-.232l9.642-9.642a.5.5 0 0 0-.642.056L6.854 4.854a.5.5 0 1 1-.708-.708L9.44.854A1.5 1.5 0 0 1 11.5.796a1.5 1.5 0 0 1 1.998-.001zm-.644.766a.5.5 0 0 0-.707 0L1.95 11.756l-.764 3.057 3.057-.764L14.44 3.854a.5.5 0 0 0 0-.708l-1.585-1.585z"
                  /></svg
              ></a>
            </div>
          </b-card>
        </div>
        <div class="col-6">
          <b-card>
            <div>Нагадування</div>
            <div>У вас жодних нагадувань</div>
          </b-card>
          <b-card v-if="state.edit_opened" header-tag="featured">
            <div>
              <b-form-input v-model="reader.name" type="text"></b-form-input>
              <b-form-input
                class="mt-2"
                v-model="reader.patronymic"
                type="text"
              ></b-form-input>
              <b-form-input
                class="mt-2"
                v-model="reader.surname"
                type="text"
              ></b-form-input>
              <b-form-input
                class="mt-2"
                v-model="reader.phone"
                type="text"
              ></b-form-input>
              <b-form-input
                class="mt-2"
                v-model="reader.email"
                type="text"
              ></b-form-input>
              <b-button class="mt-3" variant="primary" @click="saveChanges"
                >Зберегти</b-button
              >
            </div>
            <hr />
            <div class="text-center mt-3">
              <a
                href="javascript:void(0)"
                @click="state.change_pass_opened = !state.change_pass_opened"
                >Змінити пароль
                <svg
                  width="1em"
                  height="1em"
                  viewBox="0 0 16 16"
                  class="bi bi-key"
                  fill="currentColor"
                  xmlns="http://www.w3.org/2000/svg"
                >
                  <path
                    fill-rule="evenodd"
                    d="M0 8a4 4 0 0 1 7.465-2H14a.5.5 0 0 1 .354.146l1.5 1.5a.5.5 0 0 1 0 .708l-1.5 1.5a.5.5 0 0 1-.708 0L13 9.207l-.646.647a.5.5 0 0 1-.708 0L11 9.207l-.646.647a.5.5 0 0 1-.708 0L9 9.207l-.646.647A.5.5 0 0 1 8 10h-.535A4 4 0 0 1 0 8zm4-3a3 3 0 1 0 2.712 4.285A.5.5 0 0 1 7.163 9h.63l.853-.854a.5.5 0 0 1 .708 0l.646.647.646-.647a.5.5 0 0 1 .708 0l.646.647.646-.647a.5.5 0 0 1 .708 0l.646.647.793-.793-1-1h-6.63a.5.5 0 0 1-.451-.285A3 3 0 0 0 4 5z"
                  />
                  <path d="M4 8a1 1 0 1 1-2 0 1 1 0 0 1 2 0z" /></svg
              ></a>
              <div v-if="state.change_pass_opened">
                <b-form-input
                  v-model="reader.new_password.pass"
                  type="password"
                  class="mb-2 mt-2"
                  placeholder="Введіть новий пароль"
                ></b-form-input>
                <b-form-input
                  v-model="reader.new_password.conf_pass"
                  type="password"
                  placeholder="Повторіть пароль"
                ></b-form-input>
                <b-button class="mt-2" variant="danger" @click="changePassword"
                  >Змінити</b-button
                >
              </div>
            </div>

            <template #header>
              <div class="text-right">
                <svg
                  @click="state.edit_opened = false"
                  width="1em"
                  height="1em"
                  viewBox="0 0 16 16"
                  class="bi bi-x-square"
                  fill="red"
                  xmlns="http://www.w3.org/2000/svg"
                >
                  <path
                    fill-rule="evenodd"
                    d="M14 1H2a1 1 0 0 0-1 1v12a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1zM2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2z"
                  />
                  <path
                    fill-rule="evenodd"
                    d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708z"
                  />
                </svg>
              </div>
            </template>
          </b-card>
        </div>
      </div>
    </div>
    <b-container fluid v-if="$store.state.user.role == 'reader'">
      <b-row>
        <b-col>
          <my-books></my-books>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import MyBooks from "@/components/reader/MyBooks.vue";

export default {
  components: {
    "my-books": MyBooks,
  },
  data() {
    return {
      reader: {
        name: "man",
        patronymic: "manovich",
        surname: "mann",
        birthday: "12.03.2001",
        phone: "+380987654321",
        email: "email@email.email",
        books_on_hands: 3,
        new_password: {
          pass: "",
          conf_pass: "",
        },
      },
      state: {
        edit_opened: false,
        change_pass_opened: false,
      },
    };
  },
  beforeCreate() {
  },
  created() {
    console.log(this.$store.state.user);
    // :firstName="this.$store.state.user.firstName"
    //   :partonymic="this.$store.state.user.secondName"
    //   :surname="this.$store.state.user.lastName"
  },
  methods: {
    getReader(readerId) {
      console.log(readerId);
    },
    saveChanges() {},
    changePassword() {},
  },
};
</script>
