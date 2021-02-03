<template>
  <div>
    <b-container fluid>
      <b-row>
        <b-col cols="12" md="6" class="mt-4">
          <b-card v-if="reader">
            <div class="text-center user-card-header">Картка користувача</div>
            <div class="user-card-main-text">
              <div>
                {{ reader.name }}
                {{ reader.patronymic }}
                {{ reader.surname }}
              </div>
              <div>Дата народження: {{ reader.birthday }}</div>
              <div>Tel: {{ reader.phone }}</div>
              <div>Email: {{ reader.email }}</div>
              <div>Книжок на руках: {{ books_on_hands }}</div>
              <div>Борг: {{ reader.fee }}</div>
            </div>
          </b-card>
        </b-col>
        <b-col cols="12" md="6" class="mt-4 mb-4">
          <b-card v-if="reader">
            <div class="text-center user-card-header">Налаштування</div>
            <div class="user-card-main-text">
              <div>
                <b-form-checkbox
                  switch
                  inline
                  v-model="isActive"
                  @change="askForConfirmation"
                  >{{
                    isActive ? "Деактивувати запис" : "Активувати запис"
                  }}</b-form-checkbox
                >
              </div>
              <b-button
                size="sm"
                :disabled="!isInDebt"
                variant="primary"
                class="mt-2"
                >Нагадати читачу про заборгованість</b-button
              >
            </div>
          </b-card>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
export default {
  data() {
    return {
      reader: null,
      reader_id: this.$route.params.id,
      books_on_hands: 0,
      isActive: true,
      isInDebt: true, // logic to define if user in debt?
    };
  },
  created() {
    this.getReader(this.$route.params.id);
  },
  methods: {
    getReader(id) {
      const self = this;
      const url = this.$globals.remlib_api_host + this.$globals.get_reader + id;
      this.$axios
        .get(url)
        .then(function (response) {
          self.reader = response.data;
        })
        .catch(function (error) {
          console.log(error);
        });
      // fetch catalogue...
    },
    askForConfirmation() {
      const self = this;
      this.$swal
        .fire({
          //such a logic because of fireing change event after value is actually changed, not before
          title: self.isActive ? "Активувати читача" : "Деактивувати читача",
          showDenyButton: true,
          confirmButtonText: `Так`,
          denyButtonText: `Відміна`,
        })
        .then((result) => {
          if (result.isConfirmed) {
            // api call PUT password and THEN
            self.$swal.fire("Виконано", "", "success");
          } else if (result.isDenied) {
            self.isActive = !this.isActive;
            self.$swal.fire("Статус не змінено", "", "info");
          }
        });
    },
  },
};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Philosopher:wght@400;700&display=swap");
.user-card-header {
  font-family: "Philosopher", sans-serif;
  font-weight: 700;
  font-size: 1.4rem;
}
.user-card-main-text {
  font-family: "Philosopher", sans-serif;
  font-weight: 400;
  line-height: 1.8rem;
}
</style>
