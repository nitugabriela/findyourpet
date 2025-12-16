<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'
import avatarImage from '../assets/avatar.png'

const router = useRouter()

const fullName = ref('')
const email = ref('')
const phone = ref('')
const password = ref('')
const confirmPassword = ref('')
const adminKey = ref('')
const isAdminMode = ref(false)

const toggleAdminMode = () => {
  isAdminMode.value = !isAdminMode.value;
  if(isAdminMode.value) {
    alert("Admin Mode Activated: Please enter the Secret Key.");
  }
};

const submitForm = async () => {
  if (!fullName.value || !email.value || !password.value || !confirmPassword.value) {
    alert('Please fill in all required fields.')
    return
  }

  if (password.value !== confirmPassword.value) {
    alert('Passwords do not match.')
    return
  }

  let finalRole = 'USER';
  if (isAdminMode.value && adminKey.value === 'secret123') {
    finalRole = 'ADMIN';
  } else if (isAdminMode.value && adminKey.value !== 'secret123') {
    alert("Invalid Admin Key! Registering as standard User.");
    return;
  }

  try {
    await axios.post('http://localhost:2222/auth/register', {
      name: fullName.value,
      email: email.value,
      phone: phone.value,
      password: password.value,
      role: finalRole
    })

    const msg = finalRole === 'ADMIN' ? 'Admin account created!' : 'Registration successful!';
    alert(msg);
    router.push('/login');

  } catch (error) {
    console.error(error)
    alert('Registration failed. Please try again.')
  }
}
</script>

<template>
  <div class="register-container">
    <div class="register-card">

      <div class="card-visual">
        <img :src="avatarImage" alt="Welcome" class="hero-image" />
        <div class="overlay-text">
          <h3>Join the Community</h3>
          <p>Help us reunite pets with their owners.</p>
        </div>
      </div>

      <div class="card-form">
        <div class="header">
          <h2 @dblclick="toggleAdminMode" title="Double click for Staff options">Create Account</h2>
          <p class="subtitle">Enter your details below</p>
        </div>

        <form @submit.prevent="submitForm">
          <div class="input-group">
            <label>Full Name</label>
            <input type="text" v-model="fullName" placeholder="e.g. John Doe" required />
          </div>

          <div class="input-group">
            <label>Email Address</label>
            <input type="email" v-model="email" placeholder="john@example.com" required />
          </div>

          <div class="input-group">
            <label>Phone Number</label>
            <input type="tel" v-model="phone" placeholder="0712 345 678" />
          </div>

          <div class="row">
            <div class="input-group">
              <label>Password</label>
              <input type="password" v-model="password" placeholder="••••••" required />
            </div>
            <div class="input-group">
              <label>Confirm</label>
              <input type="password" v-model="confirmPassword" placeholder="••••••" required />
            </div>
          </div>

          <div v-if="isAdminMode" class="input-group admin-field">
            <label>Admin Secret Key</label>
            <input type="password" v-model="adminKey" placeholder="Enter staff key..." />
          </div>

          <button type="submit" class="submit-btn">
            {{ isAdminMode ? 'Register as Staff' : 'Sign Up' }}
          </button>
        </form>

        <div class="footer-links">
          <p>Already have an account? <router-link to="/login">Log in</router-link></p>
        </div>
      </div>

    </div>
  </div>
</template>
<style scoped>
.register-container {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #f3f4f6;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  padding: 20px;
}

.register-card {
  display: flex;
  width: 850px;
  max-width: 100%;
  background: white;
  border-radius: 20px;
  box-shadow: 0 20px 40px rgba(0,0,0,0.1);
  overflow: hidden;
  min-height: 500px;
}

.card-visual {
  flex: 0.8;
  background-color: #4f46e5;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  color: white;
  padding: 30px;
  text-align: center;
}

.hero-image {
  width: 140px;
  margin-bottom: 15px;
  border-radius: 50%;
  border: 4px solid rgba(255,255,255,0.2);
}

.overlay-text h3 {
  font-size: 1.5rem;
  margin-bottom: 8px;
}

.overlay-text p {
  opacity: 0.9;
  font-size: 0.9rem;
}

.card-form {
  flex: 1.2;
  padding: 30px 40px;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.header {
  margin-bottom: 20px;
  text-align: left;
}

.header h2 {
  font-size: 1.6rem;
  color: #1f2937;
  margin: 0;
  cursor: help;
}

.subtitle {
  color: #6b7280;
  font-size: 0.85rem;
  margin-top: 4px;
}

.input-group {
  margin-bottom: 12px;
  display: flex;
  flex-direction: column;
}

.row {
  display: flex;
  gap: 12px;
}

.row .input-group {
  flex: 1;
}

label {
  font-size: 0.8rem;
  font-weight: 600;
  color: #374151;
  margin-bottom: 4px;
}

input {
  padding: 10px;
  border: 1px solid #d1d5db;
  border-radius: 6px;
  font-size: 0.9rem;
  transition: border-color 0.2s;
  outline: none;
  background-color: #f9fafb;
}

input:focus {
  border-color: #4f46e5;
  background-color: #fff;
  box-shadow: 0 0 0 3px rgba(79, 70, 229, 0.1);
}

.admin-field input {
  border-color: #f59e0b;
  background-color: #fffbeb;
}

.admin-field label {
  color: #d97706;
}

.submit-btn {
  width: 100%;
  padding: 12px;
  background-color: #4f46e5;
  color: white;
  border: none;
  border-radius: 8px;
  font-size: 0.95rem;
  font-weight: 600;
  cursor: pointer;
  margin-top: 8px;
  transition: background-color 0.2s;
}

.submit-btn:hover {
  background-color: #4338ca;
}

.footer-links {
  margin-top: 15px;
  text-align: center;
  font-size: 0.85rem;
}

.footer-links a {
  color: #4f46e5;
  text-decoration: none;
  font-weight: 600;
}

@media (max-width: 768px) {
  .register-card {
    flex-direction: column;
    width: 100%;
    margin: 10px;
  }
  .card-visual {
    padding: 20px;
    flex: none;
  }
  .card-form {
    padding: 20px;
  }
}
</style>