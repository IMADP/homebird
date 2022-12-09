import { createContext, Dispatch, SetStateAction, useContext, useState } from "react";
import { User } from "./user-api";

/**
 * UserContextType
 * 
 */
export interface UserContextType {
  user: User | undefined;
  setUser: Dispatch<SetStateAction<User | undefined>>;
  token: string | undefined;
  setToken: Dispatch<SetStateAction<string | undefined>>;
}

const UserContext = createContext<UserContextType>(null!);
export const useUser = () => useContext(UserContext);

/**
 * UserContextProvider
 * 
 * @param children 
 * @returns UserContextProvider
 */
export const UserContextProvider = ({ children }: { children: React.ReactNode }) => {
  const [user, setUser] = useState<User>();
  const [token, setToken] = useState<string>();

  let value = {
    user,
    setUser,
    token,
    setToken
  };

  return <UserContext.Provider value={value}>{children}</UserContext.Provider>;
}
