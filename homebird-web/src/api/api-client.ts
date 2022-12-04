import axios, { AxiosResponse } from "axios";

const apiClient = axios.create({
  baseURL: "http://localhost:8080/api",
  responseType: "json",
});

const { get, post, put, delete: del } = apiClient;

export { get, post, put, del };
export type { AxiosResponse as ApiResponse };
