<template>
  <div class="app-container">
    <div class="login-container">
      <div class="login-card">
        <h2 class="login-title">Login</h2>

        <div v-if="error" class="error-message">
          {{ error }}
        </div>

        <div class="form-group">
          <label for="email">Email or phone number:</label>
          <input
              type="text"
              id="email"
              v-model="credentials.username"
              class="form-input"
              placeholder="example@email.com"
          >
        </div>

        <div class="form-group">
          <label for="password">Password:</label>
          <input
              type="password"
              id="password"
              v-model="credentials.password"
              class="form-input"
              placeholder="password"
          >
        </div>

        <div class="forgot-password">
          <router-link to="/forgot-password" class="forgot-link">Forgot your password?</router-link>
        </div>

        <div class="login-actions">
          <button class="login-button" @click="login">Log In</button>
          <router-link to="/register" class="create-account">Create an account</router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'LoginPage',
  data() {
    return {
      credentials: {
        username: '',
        password: ''
      },
      error: null
    }
  },
  methods: {
    login() {
      console.log("Login button clicked...");

      if (!this.credentials.username || !this.credentials.password) {
        this.error = 'Please enter both username and password';
        return;
      }

      axios.post('http://localhost:2222/auth/login', {
        email: this.credentials.username,
        password: this.credentials.password
      })
      .then(response => {
        console.log("Login success:", response.data);

        const user = response.data;

        localStorage.setItem('userData', JSON.stringify(user));

        localStorage.setItem('userToken', 'session-active');

        window.dispatchEvent(new Event('auth-change'));

        if (user.role === 'ADMIN') {
            this.$router.push('/dashboard');
        } else {
            this.$router.push('/');
        }
      })
      .catch(error => {
        console.error("Login Error:", error);
        if (error.response) {
          if (error.response.status === 401) {
            this.error = 'Invalid email or password.';
          } else if (error.response.status === 404) {
            this.error = 'User not found.';
          } else {
            this.error = 'Login failed. Server responded with status ' + error.response.status;
          }
        } else {
          this.error = 'Network error or server is offline.';
        }
      });
    }
  }
}
</script>

<style scoped>
.app-container {
  font-family: 'Arial', sans-serif;
  min-height: 100vh;
  background-color: #f9f2f2;
  display: flex;
  flex-direction: column;
}

.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  flex: 1;
  padding: 40px 20px;
}

.login-card {
  background-color: white;
  border-radius: 10px;
  padding: 30px;
  width: 100%;
  max-width: 400px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05);
}

.login-title {
  font-size: 24px;
  margin-bottom: 25px;
  text-align: left;
}

.form-group {
  margin-bottom: 20px;
}

.form-group label {
  display: block;
  margin-bottom: 8px;
  font-size: 14px;
  color: #333;
}

.form-input {
  width: 100%;
  padding: 12px 15px;
  border-radius: 8px;
  border: none;
  background-color: #f0f0f0;
  font-size: 14px;
}

.forgot-password {
  text-align: left;
  margin-bottom: 20px;
}

.forgot-link {
  color: #4361ee;
  text-decoration: none;
  font-size: 14px;
}

.login-actions {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.login-button {
  background-color: #4361ee;
  color: white;
  border: none;
  border-radius: 20px;
  padding: 10px 30px;
  cursor: pointer;
  font-weight: 500;
}

.create-account {
  color: #4361ee;
  text-decoration: none;
  font-size: 14px;
}

.error-message {
  color: red;
  margin-bottom: 15px;
  font-size: 14px;
}

@media (max-width: 768px) {
  .login-actions {
    flex-direction: column;
    gap: 15px;
  }

  .login-button {
    width: 100%;
  }
}
</style>