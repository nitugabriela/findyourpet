<template>
  <div class="page-wrapper">
    <main class="form-container">
      <div class="card-box">
        <h2 class="card-title">
          What happened?
          <span class="emoji">🐾</span>
        </h2>
        <div class="button-row">
          <button
            class="btn-select"
            :class="{ active: selectedType === 'lost' }"
            @click="selectedType = 'lost'"
          >
            Lost a pet
          </button>
          <button
            class="btn-select"
            :class="{ active: selectedType === 'found' }"
            @click="selectedType = 'found'"
          >
            Found a pet
          </button>
        </div>
      </div>

      <div class="card-box">
        <form @submit.prevent="submitForm">
          <label>Pet type</label>
          <input type="text" placeholder="dog, cat, etc." v-model="petType" required />

          <label>Description</label>
          <textarea placeholder="Describe your pet and how it was lost" v-model="petDescription" rows="5" required></textarea>

          <label>Location</label>
          <input type="text" placeholder="City or area" v-model="location" required />

          <label>Image URL</label>
          <input type="url" placeholder="https://example.com/image.jpg" v-model="imageUrl" />

          <h3 class="section-title">Your contact information</h3>

          <label>Your name</label>
          <input type="text" placeholder="Name" v-model="name" required />

          <label>Your phone</label>
          <input type="tel" placeholder="Phone" v-model="phone" required />

          <label>Email</label>
          <input type="email" placeholder="you@example.com" v-model="email" required />

          <div class="footer-row">
            <label class="privacy-check">
              <input type="checkbox" v-model="agreeToPrivacy" />
              I agree with the privacy policy
            </label>
            <button type="submit" class="btn-submit" :disabled="!agreeToPrivacy">
              Submit Announcement
            </button>
          </div>
        </form>
      </div>
    </main>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'

const selectedType = ref('lost')
const petType = ref('')
const imageUrl = ref('')
const petDescription = ref('')
const location = ref('')
const name = ref('')
const phone = ref('')
const email = ref('')
const agreeToPrivacy = ref(false)

const handleImage = (e) => {
  const file = e.target.files[0]
  if (file) {
    console.log('Selected image:', file.name)
    // You can use FormData to handle the image upload too
  }
}

const submitForm = async () => {
  if (!agreeToPrivacy.value) {
    alert('Please agree to the privacy policy.')
    return
  }

  const data = {
    type: selectedType.value,
    petType: petType.value,
    description: petDescription.value,
    location: location.value,
    name: name.value,
    phone: phone.value,
    email: email.value,
    imageUrl: imageUrl.value,
    status: 'pending'
  }

  try {
    await axios.post('http://localhost:2222/api/posts', data)
    alert('Announcement submitted!')
  } catch (error) {
    console.error('Error submitting form:', error)
    alert('Failed to submit post.')
  }
}
</script>


<style scoped>
.page-wrapper {
  background-color: #f7f7f8;
  padding: 2rem;
}

.form-container {
  max-width: 600px;
  margin: 0 auto;
}

.card-box {
  background-color: #fff;
  padding: 2rem;
  border-radius: 1rem;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.05);
  margin-bottom: 2rem;
}

.card-title {
  text-align: center;
  font-size: 1.5rem;
  font-weight: 600;
  margin-bottom: 1.5rem;
}

.emoji {
  margin-left: 0.5rem;
}

.button-row {
  display: flex;
  gap: 1rem;
}

.btn-select {
  flex: 1;
  background: #fff;
  color: #333;
  padding: 0.75rem;
  border: 1px solid #ccc;
  border-radius: 8px;
  cursor: pointer;
  font-weight: 500;
  transition: all 0.2s ease;
}

.btn-select.active {
  background-color: #4c6ef5;
  color: #fff;
  border-color: #4c6ef5;
}

label {
  display: block;
  margin-top: 1rem;
  font-weight: 500;
  color: #333;
}

input,
textarea {
  width: 100%;
  margin-top: 0.25rem;
  padding: 0.75rem;
  border: 1px solid #ddd;
  border-radius: 8px;
  background: #f0f0f0;
}

.section-title {
  text-align: center;
  font-weight: 600;
  margin: 2rem 0 1rem;
}

.footer-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 2rem;
}

.privacy-check {
  font-size: 0.95rem;
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.btn-submit {
  background-color: #4c6ef5;
  color: white;
  border: none;
  padding: 0.6rem 1.5rem;
  border-radius: 8px;
  font-weight: 500;
  cursor: pointer;
}

.btn-submit:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}
</style>
