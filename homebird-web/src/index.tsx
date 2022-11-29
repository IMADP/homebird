import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import Auth from './Auth';

import { ChakraProvider } from '@chakra-ui/react'
import { theme } from '@chakra-ui/pro-theme' 

const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);
root.render(
  <React.StrictMode>
    <ChakraProvider theme={theme}>
    <Auth />
    </ChakraProvider>
  </React.StrictMode>
);