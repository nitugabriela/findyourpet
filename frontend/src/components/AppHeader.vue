<script setup>
import { ref, onMounted, onUnmounted } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();
const isLoggedIn = ref(false);
const userName = ref('');
const isAdmin = ref(false);

const checkLoginStatus = () => {
  const token = localStorage.getItem('userToken');
  const user = localStorage.getItem('userData');

  if (token && user) {
    isLoggedIn.value = true;
    try {
      const userData = JSON.parse(user);
      userName.value = userData.name;
      isAdmin.value = userData.role === 'ADMIN';
    } catch (e) {
      userName.value = 'User';
      isAdmin.value = false;
    }
  } else {
    isLoggedIn.value = false;
    userName.value = '';
    isAdmin.value = false;
  }
};

const handleLogout = () => {
  localStorage.removeItem('userToken');
  localStorage.removeItem('userData');
  isLoggedIn.value = false;
  isAdmin.value = false;
  router.push('/login');
};

function goToBrowse() {
  router.push('/browse');
}

onMounted(() => {
  checkLoginStatus();
  window.addEventListener('auth-change', checkLoginStatus);
});

onUnmounted(() => {
  window.removeEventListener('auth-change', checkLoginStatus);
});
</script>

<template>
  <header class="app-header">
    <div class="header-content">

      <div class="brand-section" @click="router.push('/')">
        <div class="logo-icon">
          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor">
            <path d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm0 18c-4.41 0-8-3.59-8-8s3.59-8 8-8 8 3.59 8 8-3.59 8-8 8zm-5.5-2.5l7.51-3.49L17.5 6.5 9.99 9.99 6.5 17.5zm5.5-6.6c.61 0 1.1.49 1.1 1.1s-.49 1.1-1.1 1.1-1.1-.49-1.1-1.1.49-1.1 1.1-1.1z"/>
          </svg>
        </div>
        <span class="brand-name">FindYourPet</span>
      </div>

      <div class="search-trigger-container">
        <button @click="goToBrowse" class="search-trigger-btn">
          <span class="search-icon">🔍</span>
          <span class="search-text">Find a pet nearby</span>
        </button>
      </div>

      <nav class="nav-section">
        <router-link to="/" class="nav-link hide-mobile">Home</router-link>

        <router-link v-if="isAdmin" to="/dashboard" class="nav-link" style="color: #ef4444;">Dashboard</router-link>

        <router-link to="/post" class="btn-primary">Post Announcement</router-link>

        <div class="divider"></div>

        <div v-if="isLoggedIn" class="user-menu">
          <div class="user-avatar">
            {{ userName.charAt(0).toUpperCase() }}
          </div>
          <div class="user-info">
            <span class="user-name">{{ userName }}</span>
            <button @click="handleLogout" class="logout-text">Log out</button>
          </div>
        </div>

        <div v-else class="auth-buttons">
          <router-link to="/login" class="nav-link">Log in</router-link>
          <router-link to="/register" class="btn-secondary">Register</router-link>
        </div>
      </nav>

    </div>
  </header>
</template>

<style scoped>
.app-header {
  background-color: #ffffff;
  border-bottom: 1px solid #f0f0f0;
  position: sticky;
  top: 0;
  z-index: 100;
  box-shadow: 0 4px 20px rgba(0,0,0,0.03);
}

.header-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 24px;
  height: 72px;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.brand-section {
  display: flex;
  align-items: center;
  gap: 10px;
  cursor: pointer;
  transition: opacity 0.2s;
}

.brand-section:hover {
  opacity: 0.8;
}

.logo-icon svg {
  width: 32px;
  height: 32px;
  color: #4f46e5;
}

.brand-name {
  font-size: 1.25rem;
  font-weight: 800;
  color: #1f2937;
  letter-spacing: -0.5px;
  font-family: 'Segoe UI', sans-serif;
}
.search-trigger-container {
  flex: 1;
  display: flex;
  justify-content: center;
  padding: 0 20px;
}

.search-trigger-btn {
  background-color: white;
  border: 2px solid #4f46e5;
  color: #4f46e5;
  padding: 10px 28px;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
  cursor: pointer;
  transition: all 0.2s ease;
  font-weight: 700;
  font-size: 0.95rem;
  box-shadow: 0 2px 8px rgba(79, 70, 229, 0.15);
}

.search-trigger-btn:hover {
  background-color: #4f46e5;
  color: white;
  box-shadow: 0 4px 12px rgba(79, 70, 229, 0.3);
  transform: translateY(-1px);
}

.search-icon {
  font-size: 1.1rem;
}

.nav-section {
  display: flex;
  align-items: center;
  gap: 16px;
}

.nav-link {
  text-decoration: none;
  color: #4b5563;
  font-weight: 600;
  font-size: 0.95rem;
  transition: color 0.2s;
}

.nav-link:hover {
  color: #4f46e5;
}

.btn-primary {
  background-color: #4f46e5;
  color: white;
  padding: 8px 18px;
  border-radius: 8px;
  text-decoration: none;
  font-weight: 600;
  font-size: 0.9rem;
  transition: background-color 0.2s;
}

.btn-primary:hover {
  background-color: #4338ca;
}

.btn-secondary {
  color: #4f46e5;
  background-color: #eef2ff;
  padding: 8px 18px;
  border-radius: 8px;
  text-decoration: none;
  font-weight: 600;
  font-size: 0.9rem;
  transition: background-color 0.2s;
}

.btn-secondary:hover {
  background-color: #e0e7ff;
}

.divider {
  width: 1px;
  height: 24px;
  background-color: #e5e7eb;
  margin: 0 4px;
}

/* --- User Menu --- */
.user-menu {
  display: flex;
  align-items: center;
  gap: 10px;
}

.user-avatar {
  width: 36px;
  height: 36px;
  background: linear-gradient(135deg, #4f46e5, #818cf8);
  color: white;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: bold;
  font-size: 1rem;
}

.user-info {
  display: flex;
  flex-direction: column;
  line-height: 1.2;
}

.user-name {
  font-size: 0.9rem;
  font-weight: 700;
  color: #374151;
}

.logout-text {
  font-size: 0.75rem;
  color: #ef4444;
  background: none;
  border: none;
  padding: 0;
  cursor: pointer;
  text-align: left;
}

.logout-text:hover {
  text-decoration: underline;
}

.auth-buttons {
  display: flex;
  gap: 12px;
  align-items: center;
}

@media (max-width: 768px) {
  .header-content {
    padding: 0 16px;
  }

  .brand-name {
    display: none;
  }

  .hide-mobile {
    display: none;
  }

  .search-trigger-container {
    padding: 0 10px;
  }

  .search-text {
    display: none;
  }

  .search-trigger-btn {
    width: auto;
    padding: 10px;
    border-radius: 50%;
  }
}
</style>