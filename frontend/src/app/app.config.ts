import { ApplicationConfig, provideBrowserGlobalErrorListeners, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { provideClientHydration, withEventReplay } from '@angular/platform-browser';
import { provideHttpClient, withFetch } from '@angular/common/http';

export const appConfig: ApplicationConfig = {
  providers: [
    // Centralized error catching
    provideBrowserGlobalErrorListeners(),
    
    // Use fetch api 
    // Stream and other advanced features
    // Modern Browser support
    provideHttpClient(withFetch()),

    // Multiple events into a single change detection cycle
    provideZoneChangeDetection({ eventCoalescing: true }),

    // Routing
    provideRouter(routes), provideClientHydration(withEventReplay())
  ]
};
