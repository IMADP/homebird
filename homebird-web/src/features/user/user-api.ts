import { AxiosResponse, post } from "api/api-client";

export interface User {
  id: string;
  email: string;
  authority: string;
}

export interface UserRequest {
  email: string;
  password: string;
}

export interface TimeZone {
  id: string;
  name: string;
}

export const UserApi = {
  getToken: (request: UserRequest): Promise<AxiosResponse<User>> =>
    post<User>("/user/token", request),
  createUser: (request: UserRequest): Promise<AxiosResponse<User>> =>
    post<User>("/user", request),
};
