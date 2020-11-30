<template>
  <div>
    reports here
    <div @click="downloadReport">DOWNLOAD</div>
  </div>
</template>

<script>
export default {
  data() {
    return {};
  },
  methods: {
    downloadReport() {
      console.log("downloading...");
      const url = this.$globals.remlib_api_host + "reports/1";
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
  },
};
</script>

<style>
</style>