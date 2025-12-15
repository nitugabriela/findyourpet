<script setup>
import dogImage from '../assets/dog.png'
import mapImage from '../assets/map.png'
</script>

<template>
  <div id="app">

    <main class="content">
      <div class="left-column">
        <img :src="dogImage" alt="Golden Retriever" class="dog-img" />
        <div class="contact-section">
          <h4>Contact</h4>
          <p>alinapopescu@example.com</p>
          <button class="btn-contact">Contact</button>
        </div>
      </div>

      <div class="right-column">
        <h2>Lost a Pet - Dog Golden Retriever</h2>
        <p class="posted-by"><a href="#">Posted by Diana Popescu - Today</a></p>

        <h4>Description</h4>
        <p>
          Friendly, 7-year-old golden retriever, with a light golden coat.<br />
          Last seen wearing a red collar.
        </p>

        <h4>Location</h4>
        <p>Last seen in San Francisco, CA</p>
        <img :src="mapImage" alt="Map" class="map-img" />
      </div>
    </main>
  </div>
</template>