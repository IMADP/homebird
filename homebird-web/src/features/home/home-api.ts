import { AxiosResponse, get, post } from "api/api-client";

export interface Home {
  id: string;
  name: string;
}

export interface HomeRequest {
  userId: string;
  name: string;
}

export const HomeApi = {
  getHomes: (token: string): Promise<AxiosResponse<Home>> => get<Home>("/home", {
    headers:{
      authorization: `Bearer ${token}`
    }
  }),
  createHome: (request: HomeRequest): Promise<AxiosResponse<Home>> => post<Home>("/home", request),
};
