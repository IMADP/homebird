import {
  Navigate, useLocation
} from "react-router-dom";
import { useAuthContext } from "./AuthContext";

/**
 * AuthPage
 * 
 * @param children 
 * @returns children
 */
export default function AuthPage({ children }: { children: JSX.Element }) {
  const authContext = useAuthContext();
  const location = useLocation();

  if (!authContext.email) {
    return <Navigate to="/login" state={{ from: location }} replace />;
  }

  return children;
}