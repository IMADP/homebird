import { theme } from '@chakra-ui/pro-theme';
import { ChakraProvider } from '@chakra-ui/react';
import { UserContextProvider } from 'features/user';
import { AuthorizedPage, DashboardPage, LoginPage, RegisterPage } from "pages";
import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter, Route, Routes } from "react-router-dom";
import './index.css';

const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);

root.render(
  <React.StrictMode>
    <ChakraProvider theme={theme}>
      <UserContextProvider>
        <BrowserRouter>
          <Routes>
            <Route>
              <Route path="/login" element={
                <LoginPage />
              } />
              <Route path="/register" element={
                <RegisterPage />
              } />
              <Route path="/" element={
                <AuthorizedPage>
                  <DashboardPage />
                </AuthorizedPage>
              } />
            </Route>
          </Routes>
        </BrowserRouter>
      </UserContextProvider>
    </ChakraProvider>
  </React.StrictMode>
);