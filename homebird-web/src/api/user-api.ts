import { ApiResponse, post } from "api/api-client";

export interface User {
  id: string;
  email: string;
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
  createUser: (userRequest: UserRequest): Promise<ApiResponse<User>> =>
    post<User>("/user", userRequest),
};
