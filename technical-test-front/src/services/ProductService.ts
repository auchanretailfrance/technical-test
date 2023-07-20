import axios from "axios";
import { type Product } from "@/models/Product";

const BASE_URL = 'http://localhost:8080/api';

export const getProducts = async () => {
    try {
        const response = await axios.get(`${BASE_URL}/products`);
        return response.data;
    } catch (error) {
        throw new Error('Error while fetching the list of products.')
    }
}

export const getProduct = async (productId: String) => {
    try {
        const response = await axios.get(`${BASE_URL}/products/${productId}`);
        return response.data;
    } catch (error) {
        throw new Error('Error while fetching the requested product.')
    }
}

export const addProduct = async (productData: Product) => {
    try {
        const response = await axios.post(`${BASE_URL}/products`, productData);
        return response.data;
    } catch (error) {
        throw new Error('Error while adding new product.');
    }
}

export const updateProduct = async (productId: String, productData: Product) => {
    const method = await isPartialUpdate(productId, productData) ? 'patch' : 'put';
    try {
        const response = await axios[method](`${BASE_URL}/products/${productId}`, productData);
        return response.data;
    } catch (error) {
        throw new Error('Error while updating product.');
    }
}

export const deleteProduct = async (productId: String) => {
    try {
        const response = await axios.delete(`${BASE_URL}/products/${productId}`);
        return response.data;
    } catch (error) {
        throw new Error('Error while deleting product.');
    }
}

async function isPartialUpdate(productId: String, productData: Product) {
    try {
        const existingProduct = await getProduct(productId);

        const keys = Object.keys(productData);
        for (const key of keys) {
            if (productData[key] !== existingProduct[key]) {
                // Une différence a été trouvée, il s'agit donc d'une mise à jour partielle
                return true;
            }
        } 
        return false;
    } catch (error) {
        // Gérer les erreurs éventuelles ici
        console.error('Error while checking partial update.', error);
        throw new Error('Error while checking partial update.');
    }

}