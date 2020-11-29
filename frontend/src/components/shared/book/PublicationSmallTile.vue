<template>
  <div @click="$emit('click', $event)">
    <div class="book-tile-img">
      <b-img fluid :src="defaults.no_photo" class="rounded" alt=""></b-img>
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
import { const_globals as globals } from "@/helpers/globals";

export default {
  props: ["publicationProperty"],
  data() {
    return {
      defaults: {
        no_photo: globals.assets.avatars.no_photo,
      },
      publication: this.publicationProperty,
    };
  },
};
</script>

<style>
</style>