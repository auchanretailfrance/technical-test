import axios from "axios";
import { type Product } from "@/models/Product";

const BASE_URL = 'http://localhost:8080/api';

export const getProducts = async() => {
    try {
        const response = await axios.get(`${BASE_URL}/products`);
        return response.data;
    } catch (error) {
        throw new Error('Error while fetching the list of products.')
    }
}

export const getProduct = async(productId: String) => {
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

export const updateProduct = async (productId: String, productData: Product, isPartialUpdate: boolean) => {
    const method = isPartialUpdate ? 'patch': 'put';
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