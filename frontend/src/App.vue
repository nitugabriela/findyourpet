<script setup>
import AppHeader from './components/AppHeader.vue'
</script>

<template>
  <div class="app-container">
    <AppHeader />
    <router-view />
  </div>
</template>

<style>
html, body {
  margin: 0;
  padding: 0;
  height: 100%;
  background-color: #f7f7f8;
}

#app {
  max-width: none;
  margin: 0 auto;
  box-sizing: border-box;
}
</style>
