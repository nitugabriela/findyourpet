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
          <button class="action-btn" @click="goToPostPage">
            Report a Lost or Found Pet
          </button>
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

function goToPostPage() {
  router.push('/post')
}
</script>

<style scoped>
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
}

.action-btn {
  padding: 15px 40px;
  border-radius: 30px;
  border: none;
  background-color: #0044ff;
  color: white;
  font-weight: bold;
  font-size: 18px;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 4px 15px rgba(0, 68, 255, 0.3);
}

.action-btn:hover {
  background-color: #0030cc;
  transform: translateY(-2px); /* Slight lift effect */
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