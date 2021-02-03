<template>
  <div>
    <h3 class="mt-3 text-center">Звіти</h3>
    <b-container>
      <b-card class="mt-2 shadow-sm">
        <p class="text-center"><b>Боржники</b></p>
        <b-form>
          <b-form-group label="Тип звіту">
            <b-form-radio v-model="readers_in_debt.all" name="n1" :value="true"
              >Усі</b-form-radio
            >
            <b-form-radio v-model="readers_in_debt.all" name="n1" :value="false"
              >Уточнити параметри</b-form-radio
            >
          </b-form-group>
          <div v-if="!readers_in_debt.all">
            <b-form-group label="Розмір боргу">
              <p>
                від<input
                  type="text"
                  class="custom-input text-center"
                  size="5"
                  v-model="readers_in_debt.fee.from"
                />
                до
                <input
                  type="text"
                  class="custom-input text-center"
                  size="5"
                  v-model="readers_in_debt.fee.to"
                />
              </p>
            </b-form-group>
          </div>
          <div class="d-flex justify-content-between mt-2">
            <b-button @click="generateReport(0)" variant="success" size="sm"
              >Згенерувати звіт</b-button
            >
            <b-button
              @click="downloadReport(0)"
              size="sm"
              variant="info"
              :disabled="!report_generated[0]"
            >
              Завантажити
            </b-button>
          </div>
        </b-form>
      </b-card>
      <b-card class="mt-2 shadow-sm">
        <p class="text-center"><b> Публікації</b></p>
        <p class="text-muted" style="font-size: 0.7rem">
          Розділ знаходиться в розробці
        </p>
      </b-card>
      <b-card class="mt-2 shadow-sm">
        <p class="text-center">
          <b>Хто користувався <u>виданням (читачі)</u></b>
        </p>
        <div>
          <b-form-group label="Шукати за">
            <b-form-radio v-model="who_used.by_id" name="n2" :value="true"
              >ID публікації</b-form-radio
            >
            <b-form-radio v-model="who_used.by_id" name="n2" :value="false"
              >Бібліотечний номер</b-form-radio
            >
            <b-form-input
              class="mt-1"
              v-if="who_used.by_id"
              type="text"
              v-model="who_used.id"
              placeholder="Введіть ID"
            ></b-form-input>
            <b-form-input
              class="mt-1"
              v-else
              type="text"
              v-model="who_used.item_number"
              placeholder="Введіть бібліотечний номер"
            ></b-form-input>
          </b-form-group>
          <div class="d-flex justify-content-between mt-2">
            <b-button @click="generateReport(2)" variant="success" size="sm"
              >Згенерувати звіт</b-button
            >
            <b-button
              @click="downloadReport(2)"
              size="sm"
              variant="info"
              :disabled="!report_generated[2]"
            >
              Завантажити
            </b-button>
          </div>
        </div>
      </b-card>
      <b-card class="mt-2 shadow-sm">
        <p class="text-center"><b>Рейтинг публікацій</b></p>
        <div role="group">
          <b-form-group label="Популярність публікацій">
            <b-form-radio
              v-model="publication_rate.descending"
              name="some-radios"
              :value="true"
              >Найменш популярні</b-form-radio
            >
            <b-form-radio
              v-model="publication_rate.descending"
              name="some-radios"
              :value="false"
              >Найбільш популярні</b-form-radio
            >
          </b-form-group>
          <label for="how-many-publ">Скільки публікацій?</label>
          <b-form-input
            id="how-many-publ"
            type="number"
            v-model="publication_rate.num"
            min="1"
          ></b-form-input>
          <div class="d-flex justify-content-between mt-2">
            <b-button @click="generateReport(3)" variant="success" size="sm"
              >Згенерувати звіт</b-button
            >
            <b-button
              @click="downloadReport(3)"
              size="sm"
              variant="info"
              :disabled="!report_generated[3]"
            >
              Завантажити
            </b-button>
          </div>
        </div>
      </b-card>
    </b-container>
  </div>
</template>

<script>
export default {
  data() {
    return {
      readers_in_debt: {
        all: true,
        fee: {
          from: "",
          to: "",
        },
      },
      who_used: {
        by_pulbication: true,
        by_id: true,
        id: "",
        item_number: "",
      },
      publication_rate: {
        descending: true,
        num: 10,
      },
      report_generated: [false, false, false, false],
    };
  },
  methods: {
    downloadReport(reportTypeNumber) {
      // todo, now this is a stub
      let id = 1;
      const url = this.$globals.remlib_api_host + this.$globals.reports + id;
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
          link.setAttribute("download", "report.pdf"); //any other extension
          document.body.appendChild(link);
          link.click();
          link.remove();
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    generateReport(reportTypeNumber) {
      const url = this.$globals.remlib_api_host + this.$globals.reports;
      const self = this;
      let params = {};

      this.$axios
        .post(url, params)
        .then((response) => {
          self.$swal.fire("Очікуйте", "Звіт генерується", "success");
          //todo, now stubbed
          setTimeout(() => {
            self.report_generated[reportTypeNumber] = true;
          }, 1000);
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};
</script>

<style scoped>
.custom-input {
  outline: none !important;
  border: none;
  background-color: transparent;
  border-bottom: 0.5px solid grey;
}
</style>