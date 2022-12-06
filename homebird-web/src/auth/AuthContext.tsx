import { useContext, createContext, useState } from "react";
import { fakeAuthProvider } from "./authProvider";

/**
 * AuthContextType
 * 
 */
interface AuthContextType {
  email: string;
  token: string;
  signin: (email: string, password: string, callback: VoidFunction) => void;
  signout: (callback: VoidFunction) => void;
}

const AuthContext = createContext<AuthContextType>(null!);

/**
 * AuthContextProvider
 * 
 * @param children 
 * @returns AuthContextProvider
 */
export const AuthContextProvider = ({ children }: { children: React.ReactNode }) => {
  let [email, setEmail] = useState<any>(null);
  let [token, setToken] = useState<any>(null);

  let signin = (email: string, token: string, callback: VoidFunction) => {
    return fakeAuthProvider.signin(() => {
      setEmail(email);
      setToken(token);
      callback();
    });
  };

  let signout = (callback: VoidFunction) => {
    return fakeAuthProvider.signout(() => {
      setEmail(null);
      setToken(null);
      callback();
    });
  };

  let value = { email, token, signin, signout };
  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

/**
 * Returns the current auth context.
 * 
 * @returns AuthContextType
 */
export const useAuthContext = () => useContext(AuthContext);