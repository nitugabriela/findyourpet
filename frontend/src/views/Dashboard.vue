<template>
  <div class="admin-dashboard">
    <main class="main-content">
      <div class="admin-profile">
        <div class="admin-avatar">
          <img src="https://randomuser.me/api/portraits/men/32.jpg" alt="Admin Avatar" />
        </div>
        <h1 class="admin-title">Admin Dashboard</h1>
      </div>

      <section class="new-posts-section">
        <h2 class="section-title">New Posts</h2>

        <div class="posts-list">
          <div v-for="post in pendingPosts" :key="post.id" class="post-card">
            <div class="post-image">
              <img :src="post.imageUrl" :alt="post.description || 'Pet image'" />
            </div>

            <div class="post-content">
              <p class="post-description">
                {{ post.description || 'No description available.' }}
              </p>

              <div><strong>Pet type:</strong> {{ post.petType }}</div>
              <div><strong>Type:</strong> {{ post.type }}</div>

              <div v-if="post.features" class="post-features">
                <strong>Features:</strong> {{ post.features }}
              </div>

              <div v-if="post.location" class="post-location">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="location-icon">
                  <path d="M21 10c0 7-9 13-9 13s-9-6-9-13a9 9 0 0 1 18 0z" />
                  <circle cx="12" cy="10" r="3" />
                </svg>
                <span>{{ post.location }}</span>
              </div>

              <div v-if="post.date" class="post-date">
                <strong>Date:</strong> {{ new Date(post.date).toLocaleString() }}
              </div>

              <div v-if="post.name"><strong>Contact name:</strong> {{ post.name }}</div>
              <div v-if="post.phone"><strong>Phone:</strong> {{ post.phone }}</div>
              <div v-if="post.email"><strong>Email:</strong> {{ post.email }}</div>
            </div>

            <div class="post-actions">
              <button
                  class="approve-button"
                  @click="() => handlePost(post.id, 'approve')"
                  :disabled="post.processing"
              >
                <span v-if="!post.processing">Approve</span>
                <span v-else>Processing...</span>
              </button>
              <button
                  class="reject-button"
                  @click="() => handlePost(post.id, 'reject')"
                  :disabled="post.processing"
              >
                Reject
              </button>
            </div>
          </div>
        </div>
      </section>
    </main>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const pendingPosts = ref([])

const fetchPendingPosts = async () => {
  try {
    const response = await axios.get('http://localhost:2222/api/posts/pending')
    console.log('Fetched posts:', response.data)
    pendingPosts.value = response.data.map(p => ({ ...p, processing: false }))
  } catch (error) {
    console.error('Failed to fetch posts:', error)
  }
}

const handlePost = async (postId, action) => {
  const post = pendingPosts.value.find(p => p.id === postId)
  if (!post) return
  post.processing = true

  try {
    await axios.put(`http://localhost:2222/api/posts/${action}/${postId}`)
    pendingPosts.value = pendingPosts.value.filter(p => p.id !== postId)
  } catch (error) {
    console.error(`Failed to ${action} post:`, error)
    post.processing = false
  }
}

onMounted(() => {
  fetchPendingPosts()
})
</script>

<style scoped>
.admin-dashboard {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  font-family: sans-serif;
  background-color: #f5f5f5;
}

.main-content {
  flex: 1;
  padding: 1.5rem;
  max-width: 1200px;
  margin: 0 auto;
  width: 100%;
}

.admin-profile {
  display: flex;
  align-items: center;
  margin-bottom: 2rem;
}

.admin-avatar {
  width: 48px;
  height: 48px;
  border-radius: 50%;
  overflow: hidden;
  margin-right: 1rem;
}

.admin-avatar img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.admin-title {
  font-size: 1.5rem;
  font-weight: 600;
  color: #111827;
}

.new-posts-section {
  background-color: #f5f5f5;
  border-radius: 0.5rem;
}

.section-title {
  font-size: 1.25rem;
  font-weight: 600;
  color: #111827;
  margin-bottom: 1rem;
}

.posts-list {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.post-card {
  display: flex;
  background-color: white;
  border-radius: 0.5rem;
  overflow: hidden;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  padding: 1rem;
}

.post-image {
  width: 120px;
  height: 120px;
  border-radius: 0.375rem;
  overflow: hidden;
  margin-right: 1rem;
  flex-shrink: 0;
}

.post-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.post-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  font-size: 0.875rem;
}

.post-description {
  margin-bottom: 0.5rem;
}

.post-features,
.post-location,
.post-date {
  font-size: 0.75rem;
  margin-bottom: 0.25rem;
}

.location-icon {
  width: 14px;
  height: 14px;
  margin-right: 0.25rem;
}

.post-actions {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  margin-left: 1rem;
  justify-content: center;
}

.approve-button,
.reject-button {
  padding: 0.375rem 0.75rem;
  border-radius: 0.375rem;
  font-size: 0.75rem;
  font-weight: 500;
  cursor: pointer;
  transition: background-color 0.2s;
  min-width: 100px;
}

.approve-button {
  background-color: #e0e7ff;
  color: #4f46e5;
  border: 1px solid #e0e7ff;
}

.reject-button {
  background-color: white;
  color: #4b5563;
  border: 1px solid #e5e7eb;
}
</style>
