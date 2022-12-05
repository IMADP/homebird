import { ApiResponse, post } from "api/api-client";

export interface AuthRequest {
  username: string;
  password: string;
  longExpire: boolean;
}

export const AuthApi = {
  getToken: (request: AuthRequest): Promise<ApiResponse<string>> =>
    post<string>("/auth/token", request),
};
