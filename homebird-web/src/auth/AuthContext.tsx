import { useContext, createContext, useState } from "react";
import { fakeAuthProvider } from "./authProvider";

/**
 * AuthContextType
 * 
 */
interface AuthContextType {
  user: any;
  signin: (user: string, callback: VoidFunction) => void;
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
  let [user, setUser] = useState<any>(null);

  let signin = (newUser: string, callback: VoidFunction) => {
    return fakeAuthProvider.signin(() => {
      setUser(newUser);
      callback();
    });
  };

  let signout = (callback: VoidFunction) => {
    return fakeAuthProvider.signout(() => {
      setUser(null);
      callback();
    });
  };

  let value = { user, signin, signout };
  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

/**
 * Returns the current auth context.
 * 
 * @returns AuthContextType
 */
export const useAuthContext = () => useContext(AuthContext);