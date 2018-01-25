<template>
  <div id="joke">
    <img src="./assets/logo.png">
    <h1>{{ msg }}</h1>
    <h2>Joke</h2>
    
  </div>
</template>

<script>
export default {
  name: 'joke',
  data () {
    return {
      msg: 'Welcome to Your Vue.js App'
    }
  }
}
</script>
