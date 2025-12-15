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
const selectedRole = ref('USER')
const isHuman = ref(false)

const submitForm = async () => {
  if (!isHuman.value) {
    alert('Please confirm you are human.')
    return
  }

  if (!fullName.value || !email.value || !phone.value || !password.value || !confirmPassword.value) {
    alert('Please fill in all fields.')
    return
  }

  if (password.value !== confirmPassword.value) {
    alert('Passwords do not match.')
    return
  }

  try {
    await axios.post('http://localhost:2222/auth/register', {
      name: fullName.value,
      email: email.value,
      phone: phone.value,
      password: password.value,
      role: selectedRole.value
    })
    alert('Registered successfully!')
    router.push('/login')
  } catch (error) {
    console.error(error)
    alert('Registration failed.')
  }
}
</script>

<template>
  <div id="app">
    <main class="content">
      <div class="left-column">
        <img :src="avatarImage" alt="User Avatar" class="dog-img" />
        <div class="contact-section">
          <h4>Already have an account?</h4>
          <p><router-link to="/login">Log In</router-link></p>
        </div>
      </div>

      <div class="right-column">
        <h2>Create Account</h2>
        <form @submit.prevent="submitForm">
          <label>Full Name:</label>
          <input type="text" v-model="fullName" placeholder="ex. Popa Ioana" />

          <label>Email Address:</label>
          <input type="email" v-model="email" placeholder="example@email.com" />

          <label>Phone Number:</label>
          <input type="text" v-model="phone" placeholder="07xxxxxxxx" />

          <label>Password:</label>
          <input type="password" v-model="password" placeholder="password" />

          <label>Confirm Password:</label>
          <input type="password" v-model="confirmPassword" placeholder="password" />

          <label>Register as:</label>
          <div class="role-select">
            <button type="button" :class="{ active: selectedRole === 'USER' }" @click="selectedRole = 'USER'">✓ User</button>
            <button type="button" :class="{ active: selectedRole === 'ADMIN' }" @click="selectedRole = 'ADMIN'">Admin</button>
          </div>

          <div class="captcha-container">
            <input type="checkbox" id="human" v-model="isHuman" />
            <label for="human">I am human</label>
          </div>

          <button type="submit" class="btn-contact">Create Account</button>
        </form>
      </div>
    </main>
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

.content {
  display: flex;
  padding: 2rem;
  gap: 2rem;
}

.left-column {
  flex: 1;
  text-align: center;
}

.right-column {
  flex: 2;
}

.right-column h2 {
  margin-bottom: 1.5rem;
}

.right-column form {
  display: flex;
  flex-direction: column;
}

.right-column label {
  margin-top: 1rem;
  font-weight: 500;
}

.right-column input[type="text"],
.right-column input[type="email"],
.right-column input[type="password"] {
  padding: 0.5rem 1rem;
  border-radius: 8px;
  border: 1px solid #ccc;
  margin-top: 0.3rem;
  font-size: 0.95rem;
  background-color: #f9f9f9;
}

.role-select {
  display: flex;
  gap: 1rem;
  margin-top: 0.5rem;
}

.role-select button {
  flex: 1;
  padding: 0.4rem;
  border: 1px solid #ccc;
  background-color: #f2f2f2;
  border-radius: 8px;
  cursor: pointer;
  font-weight: 500;
}

.role-select .active {
  background-color: #d7c4f2;
  border-color: #a77dfa;
}

.captcha-container {
  display: flex;
  align-items: center;
  gap: 1rem;
  margin-top: 1rem;
  flex-wrap: wrap;
}

.btn-contact {
  background-color: #4c6ef5;
  color: white;
  padding: 0.6rem 1.2rem;
  border: none;
  border-radius: 20px;
  margin-top: 1.5rem;
  font-weight: 500;
  cursor: pointer;
  width: 100%;
  transition: background-color 0.2s;
}

.btn-contact:hover {
  background-color: #3b5bdb;
}

.dog-img {
  width: 100%;
  max-width: 300px;
  border-radius: 50%;
}

.contact-section {
  margin-top: 2rem;
  text-align: center;
}
</style>
