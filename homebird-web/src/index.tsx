import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';

import { theme } from '@chakra-ui/pro-theme';
import { ChakraProvider } from '@chakra-ui/react';
import { BrowserRouter } from "react-router-dom";

const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);
root.render(
  <React.StrictMode>
    <BrowserRouter>
      <ChakraProvider theme={theme}>
        <App />
      </ChakraProvider>
    </BrowserRouter>
  </React.StrictMode>
);