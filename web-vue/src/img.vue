<template>
    <v-flex id="imgs" xs12 offset-xs-4>

          <v-pagination :length="1000" v-model="page" v-on:input="changePage"></v-pagination>
        
    </v-flex>
</template>
<script>
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
  }
}

</script>
