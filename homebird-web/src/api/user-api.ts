import { AxiosResponse, post } from "api/api-client";

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
  createUser: (request: UserRequest): Promise<AxiosResponse<User>> =>
    post<User>("/user", request),
};
