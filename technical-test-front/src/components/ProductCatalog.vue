<script setup lang="ts">
import { onMounted, ref } from "vue";
import { type Product } from "../models/Product";
import {
    getProducts,
    getProduct,
    addProduct,
    updateProduct,
    deleteProduct,
} from "../services/ProductService";

const loading = ref(false);
const products = ref<Product[]>([]);
const showAddForm = ref(false); // State for showing/hiding the adding form
const showEditForm = ref(false); // State for showing/hiding the editing form

// Define the newProduct variable
const newProduct = ref<Product>({
    sku: "",
    name: "",
    description: "",
    price: 0,
    brand: "",
    category: "",
});

// Define the editedProduct variable
let editedProduct = ref<Product>({
    sku: "",
    name: "",
    description: "",
    price: 0,
    brand: "",
    category: "",
});

async function fetchProducts() {
    try {
        const productList = await getProducts();
        products.value = productList;
    } catch (error) {
        console.error("An error occurred while fetching products.");
    }
}

// Add the product
async function createProduct(evt: Event | SubmitEvent) {
    // Prevent the default form submission behavior
    evt.preventDefault();

    try {
        addProduct(newProduct.value);

        // Reset the newProduct after adding
        newProduct.value = {
            sku: "",
            name: "",
            description: "",
            price: 0,
            brand: "",
            category: "",
        };

        // Refetch the products list to update the view
        await fetchProducts();
    } catch (error) {
        console.error("An error occurred while adding the product.");
    }
}

async function onDeleteProduct(sku: String) {
    try {
        deleteProduct(sku);
        // Refetch the products list to update the view
        await fetchProducts();
    } catch (error) {
        console.error("An error occurred while attempting to delete the product.");
    }
}

async function onEditProduct(selectedProduct: Product) {
    try {
        updateProduct(selectedProduct.sku, selectedProduct);
        // Reset editedProduct
        editedProduct.value = {
            sku: "",
            name: "",
            description: "",
            price: 0,
            brand: "",
            category: "",
        };
        // Refetch the products list to update the view
        await fetchProducts();
    } catch (error) {
        console.error("An error occurred while attempting to update the product.");
    }
}

function toggleAddForm() {
    showAddForm.value = !showAddForm.value;
}

function toggleEditForm(product: Product) {
    editedProduct.value = JSON.parse(JSON.stringify(product));
    showEditForm.value = !showEditForm.value;
}



onMounted(fetchProducts);
</script>

<template>
    <q-page>
        <div class="q-pa-md row items-start q-gutter-md">
            <div elevated class="q-pa-md" style="margin-top: 80px;">
                <!-- Display the form if showAddForm is true -->
                <q-btn @click="toggleAddForm" v-if="!showEditForm" :color="showAddForm ? 'secondary' : 'primary'">
                    <span v-if="showAddForm">Cancel creation</span>
                    <span v-else>Add a new product</span>
                </q-btn>
                <q-btn @click="toggleEditForm(editedProduct)" v-if="showEditForm"
                    :color="showEditForm ? 'secondary' : 'primary'">
                    <span v-if="showEditForm">Cancel edition</span>
                </q-btn>
                <!-- Display the form for adding a product if showAddForm is true -->
                <q-slide-transition v-if="showAddForm">
                    <q-form @submit="createProduct">
                        <q-input v-model="newProduct.sku" label="SKU" />
                        <q-input v-model="newProduct.name" label="Name" />
                        <q-input v-model="newProduct.description" label="Description" />
                        <q-input v-model="newProduct.price" label="Price" type="number" />
                        <q-input v-model="newProduct.brand" label="Brand" />
                        <q-input v-model="newProduct.category" label="Category" />

                        <q-btn type="submit" color="primary" label="Create a product" />
                    </q-form>
                </q-slide-transition>
                <!-- End of addForm section -->
                <!-- Display the form for updating a product if showEditForm is true -->
                <q-slide-transition v-if="showEditForm">
                    <q-form @submit="updateProduct(editedProduct.sku, editedProduct)">
                        <q-input v-model="editedProduct.sku" label="SKU" />
                        <q-input v-model="editedProduct.name" label="Name" />
                        <q-input v-model="editedProduct.description" label="Description" />
                        <q-input v-model="editedProduct.price" label="Price" type="number" step="any" />
                        <q-input v-model="editedProduct.brand" label="Brand" />
                        <q-input v-model="editedProduct.category" label="Category" />

                        <q-btn type="submit" color="primary" label="Update the product" />
                    </q-form>
                </q-slide-transition>
                <!-- End of editForm section -->
            </div>
            <q-card v-if="loading">
                <q-spinner-hourglass size="40px" color="primary" />
                <div class="q-mt-md">Loading products...</div>
            </q-card>

            <q-card v-else v-for="product in products" :key="product.sku">

                <q-item>
                    <q-item-section>
                        <q-item-label>
                            <div class="text-h6">{{ product.name }}</div>
                            <div class="text-subtitle1">{{ product.brand }}</div>
                            <div class="text-body1">{{ product.description }}</div>
                            <div class="text-h6">{{ product.price }}€</div>
                        </q-item-label>
                    </q-item-section>
                </q-item>

                <q-card-actions>
                    <q-btn flat color="negative" @click="onDeleteProduct(product.sku)">Delete</q-btn>
                    <q-btn flat color="primary" @click="toggleEditForm(product)">Edit</q-btn>
                </q-card-actions>
            </q-card>
        </div>
    </q-page>
</template>
