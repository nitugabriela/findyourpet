<template>
  <div class="app-container">
    <div class="login-container">
      <div class="login-card">
        <h2 class="login-title">New Password</h2>

        <div v-if="successMessage" class="success-message">
          {{ successMessage }}
          <br><br>
          <router-link to="/login">Go to Login</router-link>
        </div>

        <div v-if="error" class="error-message">
          {{ error }}
        </div>

        <form @submit.prevent="handleReset" v-if="!successMessage">
          <div class="form-group">
            <label>Enter new password:</label>
            <input
                type="password"
                v-model="password"
                class="form-input"
                placeholder="New password"
                required
                minlength="6"
            />
          </div>

          <div class="login-actions">
            <button type="submit" class="login-button">Change Password</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRoute } from 'vue-router';
import axios from 'axios';

const route = useRoute();
const password = ref('');
const error = ref(null);
const successMessage = ref(null);

const handleReset = async () => {
  const token = route.params.token;

  try {
    await axios.post('http://localhost:2222/auth/reset-password', {
      token: token,
      password: password.value
    });

    successMessage.value = "Password successfully changed!";
  } catch (err) {
    console.error(err);
    error.value = "Invalid or expired token. Please try again.";
  }
};
</script>

<style scoped>

.app-container { font-family: 'Arial', sans-serif; min-height: 100vh; background-color: #f9f2f2; display: flex; flex-direction: column; }
.login-container { display: flex; justify-content: center; align-items: center; flex: 1; padding: 40px 20px; }
.login-card { background-color: white; border-radius: 10px; padding: 30px; width: 100%; max-width: 400px; box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05); }
.login-title { font-size: 24px; margin-bottom: 25px; text-align: left; }
.form-group { margin-bottom: 20px; }
.form-input { width: 100%; padding: 12px 15px; border-radius: 8px; border: none; background-color: #f0f0f0; font-size: 14px; }
.login-button { background-color: #4361ee; color: white; border: none; border-radius: 20px; padding: 10px 30px; cursor: pointer; font-weight: 500; width: 100%; }
.success-message { background-color: #d1fae5; color: #065f46; padding: 1rem; border-radius: 8px; text-align: center; }
.error-message { color: red; margin-bottom: 15px; }
</style>