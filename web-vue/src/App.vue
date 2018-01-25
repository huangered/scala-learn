<template>
  <v-app id="app">
    <v-tabs fixed centered>
      <v-toolbar color='cyan' dark>
        <v-toolbar-title>Title</v-toolbar-title>
        <v-spacer></v-spacer>
        <v-toolbar-side-icon class="hidden-md-and-up"></v-toolbar-side-icon>
      </v-toolbar>
      <v-tabs-bar class="cyan" dark>
        <v-tabs-slider class="yellow"></v-tabs-slider>
        <v-tabs-item v-for="i in items" :key="i.name" :href="'#tab-' + i.name">
          <router-link :to="i.url">
            <p>{{ i.name }}</p>
          </router-link>
        </v-tabs-item>
      </v-tabs-bar>
    </v-tabs>
    <v-content>
      <v-container fluid fill-height>
        <v-layout justify-center align-center>
          <h1>Hello world</h1>
          <router-view></router-view>
        </v-layout>
      </v-container>
    </v-content>
    <v-footer color="indigo" app>
      <span class="white--text">&copy; 2017</span>
    </v-footer>
  </v-app>
</template>
<script>
export default {
  data: () => ({
    items: [{ name: '美女图片', url: '/imgs' }, { name: '搞笑小段子', url: '/jokes' }]
  })
}

</script>
