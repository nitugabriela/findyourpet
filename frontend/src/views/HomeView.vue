<template>
  <div class="home-container">
    <div class="hero">
      <div class="overlay">
        <div class="hero-text">
          <p>
            <strong>FindYourPet</strong> is a community-powered platform designed to help reunite lost pets with their owners and support animals in need of new homes. Whether you've found a wandering pet or are searching for your own, FindYourPet makes it easy to post and browse announcements in your area.<br /><br />
            Thank you for being part of this mission—your compassion brings pets home.
          </p>
        </div>
      </div>
    </div>

    <div class="main-content">
      <div class="cta-section">
        <h2><b>What happened?</b></h2>
        <div class="buttons">
          <!-- ✅ This one is updated -->
          <button class="lost-btn" @click="goToPostPage">Post a Lost</button>
          <!-- ❌ This one stays the same -->
          <button class="found-btn" @click="goToLostPage">Found Pet</button>
        </div>
      </div>
    </div>

    <footer class="footer">
      <div class="footer-content">
        <div>
          <strong>Find your pet</strong>
          <ul>
            <li>Place an add</li>
            <li>Pets Map</li>
            <li>Contacts</li>
          </ul>
        </div>
        <div>
          <strong>Help</strong>
          <ul>
            <li>Support the project</li>
          </ul>
        </div>
      </div>
    </footer>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'

const router = useRouter()

// ✅ Updated to point to /post
function goToPostPage() {
  router.push('/post')
}

// ❌ This remains unchanged
function goToLostPage() {
  router.push('/browse')
}
</script>

<style scoped>
/* Your original styles — unchanged */
.home-container {
  font-family: Arial, sans-serif;
  background-color: #f7f7f7;
  color: #000;
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}
.hero {
  background-image: url('/cat.jpg');
  background-size: cover;
  background-position: center;
  padding: 60px 20px;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 0;
  width: 100%;
  position: relative;
}
.overlay {
  background-color: rgba(0, 0, 0, 0.5);
  padding: 30px;
  border-radius: 15px;
  width: 100%;
  max-width: 800px;
}
.hero-text {
  color: #fff;
  line-height: 1.6;
}
.cta-section {
  text-align: center;
  margin: 20px 0 40px;
}
.cta-section h2 {
  margin-bottom: 20px;
  font-size: 24px;
}
.buttons {
  display: flex;
  justify-content: center;
  gap: 20px;
}
.lost-btn,
.found-btn {
  padding: 12px 30px;
  border-radius: 10px;
  border: 2px solid #0044ff;
  background-color: #0044ff;
  color: white;
  font-weight: bold;
  font-size: 16px;
  cursor: pointer;
  transition: all 0.3s ease;
}
.found-btn {
  background-color: white;
  color: #0044ff;
}
.lost-btn:hover,
.found-btn:hover {
  background-color: #0030cc;
  color: white;
}
.footer {
  background-color: #1a1a1a;
  color: white;
  padding: 30px 20px;
  margin-top: auto;
}
.footer-content {
  display: flex;
  justify-content: space-around;
  flex-wrap: wrap;
}
.footer-content ul {
  list-style: none;
  padding: 0;
  margin: 10px 0 0;
}
.footer-content li {
  margin-bottom: 8px;
}
.main-content {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 20px;
}
</style>
