<script setup>
import { ref, computed, onMounted, watch } from 'vue';
import { useRoute } from 'vue-router';
import L from 'leaflet';
import 'leaflet/dist/leaflet.css';
import axios from 'axios'

import grayStripedCatImage from '../assets/gray-striped-cat.jpg';
import grayWhiteCatImage from '../assets/gray-white-cat.jpeg';
import blackWhiteCatImage from '../assets/black-white-cat.jpg';
import orangeCatImage from '../assets/orange-cat.jpeg';
import puppyImage from '../assets/puppy.jpg';

const route = useRoute();
const mapContainer = ref(null);
const selectedPet = ref(null);
const searchQuery = ref(route.query.q || '');
const markers = [];

watch(
  () => route.query.q,
  (newVal) => {
    searchQuery.value = newVal || '';
  }
);

const pets = ref([
  {
    id: 1,
    image: grayStripedCatImage,
    description: 'Our indoor cat slipped through a window and hasn\'t returned...',
    features: 'gray, striped',
    location: 'Herăstrău Park, near Șoseaua Nordului',
    date: 'Mon, 29.04.2025',
    name: 'Andrei Popescu',
    phone: '0723 456 789',
    email: 'andrei.popescu@email.com',
    coordinates: { lat: 44.4647, lng: 26.0865 }
  },
  {
    id: 3,
    image: blackWhiteCatImage,
    description: 'Found a young white and black cat meowing outside...',
    features: 'white, striped',
    location: 'Bulevardul Timișoara, Sector 6',
    date: 'Tue, 30.04.2025',
    name: 'Mihai Georgescu',
    phone: '0765 987 654',
    email: 'mihai.georgescu@email.com',
    coordinates: { lat: 44.4278, lng: 26.0412 }
  },
  {
    id: 4,
    image: orangeCatImage,
    description: 'Leo disappeared during the night. He\'s curious and friendly...',
    features: 'tiny, orange',
    location: 'Intrarea Violoncelului, Sector 4',
    date: 'Wed, 31.04.2025',
    name: 'Ioana Marinescu',
    phone: '0734 222 333',
    email: 'ioana.marinescu@email.com',
    coordinates: { lat: 44.4139, lng: 26.1025 }
  },
  {
    id: 5,
    image: puppyImage,
    description: 'Tasha is missing. She slipped out during renovations...',
    features: 'gray, striped',
    location: 'Șoseaua Pantelimon, near Mega Mall',
    date: 'Wed, 31.04.2025',
    name: 'Vlad Dumitrescu',
    phone: '0788 444 555',
    email: 'vlad.dumitrescu@email.com',
    coordinates: { lat: 44.4417, lng: 26.1513 }
  }
]);

const filteredPets = computed(() => {
  const query = searchQuery.value.trim().toLowerCase();
  if (!query) return pets.value;

  return pets.value.filter(pet =>
    pet.description.toLowerCase().includes(query) ||
    pet.features.toLowerCase().includes(query) ||
    pet.location.toLowerCase().includes(query)
  );
});

const selectPet = (pet) => {
  selectedPet.value = pet;
  if (mapInstance && pet.coordinates) {
    mapInstance.setView([pet.coordinates.lat, pet.coordinates.lng], 15);
  }
};

const approvedPosts = ref([])

const fetchApprovedPosts = async () => {
  try {
    const response = await axios.get('http://localhost:2222/api/posts/approved')
    approvedPosts.value = response.data
  } catch (error) {
    console.error('Error fetching approved posts:', error)
  }
}


let mapInstance;

onMounted(() => {
  mapInstance = L.map(mapContainer.value).setView([44.4268, 26.1025], 12);
  fetchApprovedPosts()

  L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: '&copy; OpenStreetMap contributors'
  }).addTo(mapInstance);

  pets.value.forEach(pet => {
    if (pet.coordinates) {
      const popupContent = `
        <div style="width: 160px;">
          <img src="${pet.image}" alt="Pet image" style="width: 100%; border-radius: 8px; margin-bottom: 6px;" />
          <div style="font-size: 13px;">${pet.description.slice(0, 80)}...</div>
        </div>
      `;

      const marker = L.marker([pet.coordinates.lat, pet.coordinates.lng])
        .addTo(mapInstance)
        .bindPopup(popupContent);

      marker.on('click', () => selectPet(pet));
      markers.push(marker);
    }
  });
});
</script>

<template>
  <div class="pet-finder-app">
    <main class="main-content">
      <aside class="sidebar">
        <div class="sidebar-header">
          <span class="ads-count">{{ filteredPets.length }} ads found</span>
          <div class="sidebar-search">
            <input
              type="text"
              placeholder="Search here..."
              class="sidebar-search-input"
              v-model="searchQuery"
            />
          </div>
        </div>

        <div class="pet-listings">
          <div
              v-for="(post, index) in approvedPosts"
              :key="index"
              class="pet-card"
              @click="selectPet(post)"
          >
            <div class="pet-image">
              <img :src="post.imageUrl" :alt="post.description" />
            </div>
            <div class="pet-details">
              <p class="pet-description">{{ post.description }}</p>

              <div class="pet-location">{{ post.location }}</div>

              <div v-if="post.date" class="pet-date">
                {{ new Date(post.date).toLocaleString() }}
              </div>

              <div class="pet-contact">
                <div><strong>{{ post.name }}</strong></div>
                <div>{{ post.phone }}</div>
                <div>{{ post.email }}</div>
              </div>
            </div>
          </div>
        </div>
         <div class="pet-listings">
                  <div
                    v-for="(pet, index) in filteredPets"
                    :key="index"
                    class="pet-card"
                    @click="selectPet(pet)"
                  >
                    <div class="pet-image">
                      <img :src="pet.image" :alt="pet.description" />
                    </div>
                    <div class="pet-details">
                      <p class="pet-description">{{ pet.description }}</p>
                      <div class="pet-features"><strong>Features:</strong> {{ pet.features }}</div>
                      <div class="pet-location">{{ pet.location }}</div>
                      <div class="pet-date">{{ pet.date }}</div>




                      <div class="pet-contact">
                        <div><strong>{{ pet.name }}</strong></div>
                        <div>{{ pet.phone }}</div>
                        <div>{{ pet.email }}</div>
                      </div>
                    </div>
                  </div>
                  </div>
      </aside>
      <div class="map-container">
        <div ref="mapContainer" class="leaflet-map"></div>
      </div>
    </main>
  </div>
</template>

<style scoped>
.pet-finder-app {
  display: flex;
  flex-direction: column;
  height: 100vh;
  font-family: Arial, sans-serif;
}

.main-content {
  display: flex;
  flex: 1;
  height: 100%;
  overflow: hidden;
}

.sidebar {
  width: 500px;
  background-color: white;
  display: flex;
  flex-direction: column;
  border-right: 1px solid #e5e7eb;
}

.sidebar-header {
  padding: 1rem;
  border-bottom: 1px solid #e5e7eb;
}

.sidebar-search-input {
  width: 100%;
  padding: 0.5rem;
  border: 1px solid #ccc;
  border-radius: 20px;
  font-size: 0.875rem;
}

.pet-listings {
  overflow-y: auto;
  flex: 1;
}

.pet-card {
  display: flex;
  padding: 1rem;
  border-bottom: 1px solid #e5e7eb;
  cursor: pointer;
}

.pet-image {
  width: 80px;
  height: 80px;
  margin-right: 1rem;
  border-radius: 0.375rem;
  overflow: hidden;
}

.pet-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.pet-details {
  font-size: 0.75rem;
  display: flex;
  flex-direction: column;
}

.pet-contact {
  margin-top: 4px;
  font-size: 0.7rem;
  color: #444;
}
.map-container {
  flex: 1;
  position: relative;
}

.leaflet-map {
  width: 100%;
  height: 100%;
  min-height: 300px;
}
</style>
