<template>
  <div @click="$emit('click', $event)">
    <div class="book-tile-img">
      <b-img
        fluid
        :src="
          publication.descripiton.photo.trim().length != 0
            ? publication.descripiton.photo
            : defaults.no_photo
        "
        class="rounded"
        alt=""
      ></b-img>
    </div>
    <div>
      {{ publicationProperty.title }}
    </div>
    <div>
      {{ publicationProperty.creator }}
    </div>
  </div>
</template>

<script>
export default {
  props: ["publicationProperty"],
  data() {
    return {
      defaults: {
        no_photo: this.$globals.assets.avatars.no_photo,
      },
      publication: this.publicationProperty,
    };
  },
};
</script>

<style>
</style>