import { RenderMode, ServerRoute } from '@angular/ssr';

export const serverRoutes: ServerRoute[] = [
  {
    // SSR for all application routes at build time
    path: '**',
    renderMode: RenderMode.Prerender
  }
];
