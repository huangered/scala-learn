<template>
  <v-flex id="imgs" xs12 offset-xs-4>
    <v-pagination :length="num" v-model="page" v-on:input="changePage" :total-visible="15"></v-pagination>
  </v-flex>
</template>
<script>
var axios = require('axios')
export default {
  name: 'imgs',
  data() {
    return {
      msg: 'Welcome to Your Vue.js App',
      page: 1
    }
  },
  methods: {
    changePage: function(event) {
      // `this` inside methods points to the Vue instance
      console.log(event)
    }
  },
  beforeMount: function() {
    this.num = 3000
  },
  mounted: function() {
    axios.get('http://localhost:8091/rest/api/v1/imgs')
      .then(function(response) {
        console.log(response);
      })
      .catch(function(error) {
        console.log(error);
      });

  }
}

</script>
