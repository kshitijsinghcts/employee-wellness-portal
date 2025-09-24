import { mergeApplicationConfig, ApplicationConfig } from '@angular/core';
import { provideServerRendering, withRoutes } from '@angular/ssr';
import { appConfig } from './app.config';
import { serverRoutes } from './app.routes.server';

const serverConfig: ApplicationConfig = {
  // Create server and route it to make it know how to render
  providers: [
    provideServerRendering(withRoutes(serverRoutes))
  ]
};

// Handle both server-side and client-side
// For heavy applications where server sends html beforehand
// Time to content also decreases
export const config = mergeApplicationConfig(appConfig, serverConfig);
