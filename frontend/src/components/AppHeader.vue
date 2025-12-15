<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();
const searchInput = ref('');

function goToBrowse() {
  if (searchInput.value.trim()) {
    router.push({ path: '/browse', query: { q: searchInput.value } });
  } else {
    router.push('/browse');
  }
}
</script>


<template>
  <header class="topbar">
    <div class="left-section">
      <div class="logo-container">
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="paw-icon">
          <path d="M9.196 6.704c.265 1.308-.97 2.982-2.753 3.734-1.783.752-3.443.302-3.708-1.006-.265-1.308.97-2.982 2.753-3.734 1.783-.752 3.443-.302 3.708 1.006zm2.757-2.095c.265 1.308-.97 2.982-2.753 3.734-1.783.752-3.443.302-3.708-1.006-.265-1.308.97-2.982 2.753-3.734 1.783-.752 3.443-.302 3.708 1.006zm3.212 7.564c-1.783.752-3.443.302-3.708-1.006-.265-1.308.97-2.982 2.753-3.734 1.783-.752 3.443-.302 3.708 1.006.265 1.308-.97 2.982-2.753 3.734zm4.497-2.582c-1.783.752-3.443.302-3.708-1.006-.265-1.308.97-2.982 2.753-3.734 1.783-.752 3.443-.302 3.708 1.006.265 1.308-.97 2.982-2.753 3.734zm.326 6.949c-.674 1.157-2.714 1.157-4.549 0-1.835-1.157-2.775-3.033-2.101-4.19.674-1.157 2.714-1.157 4.549 0 1.835 1.157 2.775 3.033 2.101 4.19zm-7.678 4.615c-.674 1.157-2.714 1.157-4.549 0-1.835-1.157-2.775-3.033-2.101-4.19.674-1.157 2.714-1.157 4.549 0 1.835 1.157 2.775 3.033 2.101 4.19z" />
        </svg>
        <span class="logo-text">FindYourPet</span>
      </div>
      <router-link to="/" class="nav-link">Home Page</router-link>
    </div>

    <div class="search-container">
      <input type="text" placeholder="Address, ad number, breed, etc." class="search-input" @keyup.enter="goToBrowse"/>
    </div>

    <div class="right-section">
      <router-link to="/post" class="btn-post">Post Announcement</router-link>
      <router-link to="/login" class="login-link">Log in</router-link>
    </div>
  </header>
</template>

<style scoped>
.topbar {
  background-color: #ffffff;
  display: flex;
  align-items: center;
  padding: 0.8rem 2rem;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05);
}

.left-section {
  display: flex;
  align-items: center;
  min-width: 220px;
}

.logo-container {
  display: flex;
  align-items: center;
  margin-right: 1.5rem;
}

.paw-icon {
  width: 24px;
  height: 24px;
  color: #4361ee;
}

.logo-text {
  font-weight: bold;
  font-size: 16px;
  margin-left: 8px;
}

.nav-link {
  text-decoration: none;
  color: #333;
  font-size: 14px;
}

.search-container {
  flex: 1;
  margin: 0 1.5rem;
}

.search-input {
  width: 100%;
  padding: 0.6rem 1rem;
  border-radius: 20px;
  border: none;
  background-color: #f5f5f5;
  font-size: 14px;
}

.right-section {
  display: flex;
  align-items: center;
  gap: 1.5rem;
  min-width: 220px;
  justify-content: flex-end;
}

.btn-post {
  background-color: #4361ee;
  color: white;
  padding: 0.6rem 1.2rem;
  border: none;
  border-radius: 20px;
  font-weight: 500;
  text-decoration: none;
  font-size: 14px;
}

.login-link {
  text-decoration: none;
  color: #333;
  font-size: 14px;
}

@media (max-width: 768px) {
  .topbar {
    flex-direction: column;
    padding: 0.8rem 1rem;
  }

  .left-section {
    width: 100%;
    justify-content: space-between;
    margin-bottom: 0.8rem;
  }

  .search-container {
    width: 100%;
    margin: 0.5rem 0;
  }

  .right-section {
    width: 100%;
    justify-content: space-between;
    margin-top: 0.8rem;
  }
}
</style>