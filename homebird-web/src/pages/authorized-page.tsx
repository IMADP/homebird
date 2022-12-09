import { useUser } from 'features/user';
import {
  Navigate, useLocation
} from "react-router-dom";

/**
 * AuthorizedPage
 * 
 * @param children 
 * @returns children
 */
export function AuthorizedPage({ children }: { children: JSX.Element }) {
  const userContext = useUser();
  const location = useLocation();

  if (!userContext.token) {
    return <Navigate to="/login" state={{ from: location }} replace />;
  }

  return children;
}