import axios, { AxiosResponse } from "axios";
import { ValidationError } from "features/ui/validation/ValidationErrors";
import { UserContextType } from "features/user";
const apiClient = axios.create({
  baseURL: "http://localhost:8080/api",
  responseType: "json",
});

const { get, post, put, delete: del } = apiClient;

export { get, post, put, del };
export type { AxiosResponse };

/**
 * Interface defining a form request, which will handle validation errors and loading indicators.
 * 
 */
export interface FormRequest<Type> {
  userContext: UserContextType,
  toast: any
  setErrors: React.Dispatch<React.SetStateAction<ValidationError[]>>;
  setLoading: React.Dispatch<React.SetStateAction<boolean>>;
  onRequest: Promise<AxiosResponse<Type>>;
  onSuccess(request: Type): any;
}

/**
 * Executes a form request.
 * If successful, calls on the onSuccess callback.
 * 
 * @param request 
 */
export const formRequest = async <Type>(request: FormRequest<Type>) => {
  request.setLoading(true);
  
  await request.onRequest
  .then((response: any) => {
      request.setLoading(false);

      if(response.headers.authorization) {
        const token = response.headers.authorization;
        request.userContext.setToken(token);
      }

      request.onSuccess(response.data, );
    })
    .catch((error: { response: { data: any; }; }) => {
      request.setLoading(false);

      if (error.response) {
        request.setErrors(error.response.data);
        return;
      }

      request.toast({
        title: 'Communication Error',
        description: "Your request couldn't be completed, please try again later",
        status: 'error',
        duration: 10000,
        isClosable: true,
      })
    });
};


