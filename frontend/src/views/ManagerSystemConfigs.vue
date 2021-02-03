<template>
  <div>
    <h5 class="text-center mt-3 main-font">Системні налаштування</h5>
    <b-container fluid class="mt-3">
      <b-row>
        <b-col cols="12">
          <div>
            <b-row>
              <b-col> Сповіщення читачів про заборгованість </b-col>
              <b-col>
                <b-form-checkbox switch v-model="isActive">{{
                  isActive ? "Вимкнути" : "Увімкнути"
                }}</b-form-checkbox></b-col
              >
              <b-col>
                <b-form-select
                  v-model="quantity_selected"
                  :options="quantities_available"
                  value-field="id"
                  text-field="name"
                  placeholder="Виберість частоту"
                  :disabled="!isActive"
                ></b-form-select>
              </b-col>
              <b-col>
                <b-button
                  variant="primary"
                  size="sm"
                  pill
                  @click="saveNotificationsChanges"
                  >Підтвердити зміни</b-button
                >
              </b-col>
            </b-row>
          </div>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
export default {
  data() {
    return {
      quantities_available: [],
      isActive: null,
      quantity_selected: null,
    };
  },
  created() {
    // todo axios request
    this.quantities_available = [
      {
        id: 1,
        name: "Раз на тиждень",
      },
      {
        id: 2,
        name: "Раз на 2 тижні",
      },
      {
        id: 3,
        name: "Раз на місяць",
      },
    ];
    this.isActive = true;
  },
  methods: {
    changeSystemConfigs() {},
    saveNotificationsChanges() {
      //todo request
    },
  },
};
</script>

<style scoped>
* {
  background-color: #f0f2f5;
}
.main-font {
  font-family: "Comfortaa", cursive;
  font-size: 16px !important;
}
</style>
