<script setup>
import { ref, computed, onMounted, watch } from 'vue';
import { useRoute } from 'vue-router';
import L from 'leaflet';
import 'leaflet/dist/leaflet.css';
import axios from 'axios';

import grayStripedCatImage from '../assets/gray-striped-cat.jpg';
import blackWhiteCatImage from '../assets/black-white-cat.jpg';
import orangeCatImage from '../assets/orange-cat.jpeg';
import puppyImage from '../assets/puppy.jpg';

import markerIcon2x from 'leaflet/dist/images/marker-icon-2x.png';
import markerIcon from 'leaflet/dist/images/marker-icon.png';
import markerShadow from 'leaflet/dist/images/marker-shadow.png';

const route = useRoute();
const mapContainer = ref(null);
const selectedPet = ref(null);
const searchQuery = ref(route.query.q || '');
const markers = [];

const approvedPosts = ref([]);
const hardcodedPets = ref([
  {
    id: 1,
    image: grayStripedCatImage,
    description: 'Our indoor cat slipped through a window...',
    features: 'gray, striped',
    location: 'Herăstrău Park, near Șoseaua Nordului',
    date: 'Mon, 29.04.2025',
    name: 'Andrei Popescu',
    phone: '0723 456 789',
    email: 'andrei@email.com',
    coordinates: { lat: 44.4647, lng: 26.0865 }
  },
  {
    id: 2,
    image: blackWhiteCatImage,
    description: 'Found a young white and black cat...',
    features: 'white, striped',
    location: 'Bulevardul Timișoara, Sector 6',
    date: 'Tue, 30.04.2025',
    name: 'Mihai Georgescu',
    phone: '0765 987 654',
    email: 'mihai@email.com',
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

watch(() => route.query.q, (newVal) => {
  searchQuery.value = newVal || '';
});

const filteredLocalPets = computed(() => {
  const query = searchQuery.value.trim().toLowerCase();
  if (!query) return hardcodedPets.value;

  return hardcodedPets.value.filter(pet =>
      pet.description.toLowerCase().includes(query) ||
      pet.features?.toLowerCase().includes(query) ||
      pet.location.toLowerCase().includes(query)
  );
});

const selectPet = (pet) => {
  selectedPet.value = pet;
  if (mapInstance && pet.coordinates) {
    mapInstance.flyTo([pet.coordinates.lat, pet.coordinates.lng], 15);
  }
};

const fetchApprovedPosts = async () => {
  try {
    const response = await axios.get('http://localhost:2222/api/posts/approved');
    approvedPosts.value = response.data;
  } catch (error) {
    console.error('Error fetching approved posts:', error);
  }
};

let mapInstance;

onMounted(() => {
  delete L.Icon.Default.prototype._getIconUrl;
  L.Icon.Default.mergeOptions({
    iconRetinaUrl: markerIcon2x,
    iconUrl: markerIcon,
    shadowUrl: markerShadow,
  });

  mapInstance = L.map(mapContainer.value).setView([44.4268, 26.1025], 12);

  L.tileLayer('https://{s}.basemaps.cartocdn.com/rastertiles/voyager/{z}/{x}/{y}{r}.png', {
    attribution: '&copy; OpenStreetMap contributors &copy; CARTO',
    subdomains: 'abcd',
    maxZoom: 20
  }).addTo(mapInstance);

  fetchApprovedPosts();

  hardcodedPets.value.forEach(pet => {
    if (pet.coordinates) {
      const popupContent = `
        <div style="font-family: sans-serif;">
          <img src="${pet.image}" style="width: 100%; height: 100px; object-fit: cover; border-radius: 4px; margin-bottom: 4px;" />
          <strong>${pet.features || 'Pet'}</strong><br>
          <span style="font-size: 12px; color: #555;">${pet.location}</span>
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
  <div class="browse-layout">

    <aside class="sidebar">

      <div class="sidebar-header">
        <h1>FindYourPet</h1>
        <div class="search-bar">
          <input
              v-model="searchQuery"
              type="text"
              placeholder="Search by breed, color, or location..."
          />
          <button class="search-btn">🔍</button>
        </div>
        <div class="results-count">
          {{ approvedPosts.length + filteredLocalPets.length }} pets found nearby
        </div>
      </div>

      <div class="pet-scroll-area">

        <div
            v-for="(post, index) in approvedPosts"
            :key="'api-'+index"
            class="pet-card"
            :class="{ 'active': selectedPet === post }"
            @click="selectPet(post)"
        >
          <div class="card-image">
            <img :src="post.imageUrl || '/placeholder-paw.png'" alt="Pet" />
          </div>
          <div class="card-content">
            <h3 class="pet-title">{{ post.description.substring(0, 40) }}...</h3>
            <p class="pet-location">{{ post.location }}</p>
            <p class="pet-meta" v-if="post.date">{{ new Date(post.date).toLocaleDateString() }}</p>
            <div class="pet-footer">
              <span class="contact-tag">{{ post.name }}</span>
            </div>
          </div>
        </div>

        <div
            v-for="(pet, index) in filteredLocalPets"
            :key="'local-'+index"
            class="pet-card"
            :class="{ 'active': selectedPet === pet }"
            @click="selectPet(pet)"
        >
          <div class="card-image">
            <img :src="pet.image" alt="Pet" />
          </div>
          <div class="card-content">
            <h3 class="pet-title">{{ pet.description.substring(0, 40) }}...</h3>
            <p class="pet-features" v-if="pet.features">{{ pet.features }}</p>
            <p class="pet-location">{{ pet.location }}</p>
            <div class="pet-footer">
              <span class="contact-tag">{{ pet.name }}</span>
              <span class="phone-tag">{{ pet.phone }}</span>
            </div>
          </div>
        </div>

      </div>
    </aside>

    <div class="map-wrapper">
      <div ref="mapContainer" class="leaflet-map"></div>
    </div>

  </div>
</template>

<style scoped>
.browse-layout {
  display: flex;
  height: 100vh;
  overflow: hidden;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  color: #333;
}

.sidebar {
  width: 400px;
  min-width: 400px;
  background-color: #f9fafb;
  border-right: 1px solid #e5e7eb;
  display: flex;
  flex-direction: column;
  z-index: 2;
  box-shadow: 2px 0 10px rgba(0,0,0,0.05);
}

.sidebar-header {
  padding: 1.5rem;
  background-color: white;
  border-bottom: 1px solid #e5e7eb;
  box-shadow: 0 2px 4px rgba(0,0,0,0.02);
}

.sidebar-header h1 {
  font-size: 1.5rem;
  color: #4f46e5;
  margin: 0 0 1rem 0;
}

.search-bar {
  display: flex;
  gap: 8px;
}

.search-bar input {
  flex: 1;
  padding: 10px 15px;
  border: 1px solid #d1d5db;
  border-radius: 8px;
  font-size: 0.95rem;
  outline: none;
  transition: border-color 0.2s;
}

.search-bar input:focus {
  border-color: #4f46e5;
}

.search-btn {
  background: #f3f4f6;
  border: 1px solid #d1d5db;
  border-radius: 8px;
  cursor: pointer;
  padding: 0 12px;
}

.results-count {
  margin-top: 10px;
  font-size: 0.85rem;
  color: #6b7280;
}

.pet-scroll-area {
  flex: 1;
  overflow-y: auto;
  padding: 1rem;
}

.pet-card {
  display: flex;
  background: white;
  border-radius: 12px;
  border: 1px solid #e5e7eb;
  margin-bottom: 1rem;
  overflow: hidden;
  cursor: pointer;
  transition: all 0.2s ease;
}

.pet-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0,0,0,0.08);
  border-color: #c7d2fe;
}

.pet-card.active {
  border-color: #4f46e5;
  background-color: #eef2ff;
}

.card-image {
  width: 120px;
  height: 120px;
  flex-shrink: 0;
}

.card-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.card-content {
  padding: 12px;
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.pet-title {
  font-size: 0.95rem;
  font-weight: 600;
  margin: 0 0 4px 0;
  line-height: 1.3;
}

.pet-location {
  font-size: 0.8rem;
  color: #6b7280;
  margin: 0;
}

.pet-features {
  font-size: 0.8rem;
  color: #4b5563;
  margin: 2px 0;
}

.pet-footer {
  margin-top: 8px;
  display: flex;
  gap: 8px;
  flex-wrap: wrap;
}

.contact-tag, .phone-tag {
  font-size: 0.75rem;
  background: #f3f4f6;
  padding: 2px 8px;
  border-radius: 4px;
  color: #374151;
}

.map-wrapper {
  flex: 1;
  position: relative;
}

.leaflet-map {
  width: 100%;
  height: 100%;
}
</style>s