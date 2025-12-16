<template>
  <div class="app-container">
    <div class="login-container">
      <div class="login-card">
        <h2 class="login-title">Reset Password</h2>

        <p class="instruction-text">
          Enter your email address and we'll send you a link to reset your password.
        </p>

        <div v-if="successMessage" class="success-message">
          {{ successMessage }}
        </div>

        <div v-if="error" class="error-message">
          {{ error }}
        </div>

        <form @submit.prevent="handleReset" v-if="!successMessage">
          <div class="form-group">
            <label for="email">Email address:</label>
            <input
                type="email"
                id="email"
                v-model="email"
                class="form-input"
                placeholder="example@email.com"
                required
            >
          </div>

          <div class="login-actions">
            <button type="submit" class="login-button" :disabled="isLoading">
              {{ isLoading ? 'Sending...' : 'Send Reset Link' }}
            </button>
          </div>
        </form>

        <div class="back-link-container">
          <router-link to="/login" class="back-link">← Back to Login</router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import axios from 'axios';

const email = ref('');
const isLoading = ref(false);
const error = ref(null);
const successMessage = ref(null);

const handleReset = async () => {
  error.value = null;
  isLoading.value = true;

  try {
    await axios.post('http://localhost:2222/auth/forgot-password', { email: email.value });

    successMessage.value = "If an account exists with this email, you will receive a reset link shortly.";
  } catch (err) {
    console.error(err);
    error.value = "Unable to connect to the server. Please try again later.";
  } finally {
    isLoading.value = false;
  }
};
</script>

<style scoped>
.app-container { font-family: 'Arial', sans-serif; min-height: 100vh; background-color: #f9f2f2; display: flex; flex-direction: column; }
.login-container { display: flex; justify-content: center; align-items: center; flex: 1; padding: 40px 20px; }
.login-card { background-color: white; border-radius: 10px; padding: 30px; width: 100%; max-width: 400px; box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05); }
.login-title { font-size: 24px; margin-bottom: 15px; text-align: left; }
.instruction-text { color: #666; font-size: 14px; margin-bottom: 25px; line-height: 1.4; }
.form-group { margin-bottom: 20px; }
.form-group label { display: block; margin-bottom: 8px; font-size: 14px; color: #333; }
.form-input { width: 100%; padding: 12px 15px; border-radius: 8px; border: none; background-color: #f0f0f0; font-size: 14px; }
.login-button { background-color: #4361ee; color: white; border: none; border-radius: 20px; padding: 10px 30px; cursor: pointer; font-weight: 500; width: 100%; margin-top: 10px; }
.login-button:disabled { background-color: #a0b1f5; cursor: not-allowed; }
.back-link-container { margin-top: 20px; text-align: center; }
.back-link { color: #666; text-decoration: none; font-size: 14px; }
.back-link:hover { color: #4361ee; }
.success-message { background-color: #d1fae5; color: #065f46; padding: 1rem; border-radius: 8px; font-size: 14px; line-height: 1.4; margin-bottom: 20px; }
.error-message { color: red; margin-bottom: 15px; font-size: 14px; }
</style>