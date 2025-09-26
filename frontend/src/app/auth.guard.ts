import { inject } from '@angular/core';
import { CanActivateFn, Router, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { AuthService } from './services/auth.service';

export const authGuard: CanActivateFn = (route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (!authService.isLoggedIn()) {
    // If not logged in, redirect to the login page
    router.navigate(['/']);
    return false;
  }

  // At this point, user is logged in.
  const userRole = authService.getUserRole();
  const isTryingToAccessAdmin = state.url.startsWith('/admin-panel');

  if (userRole === 'ADMIN') {
    if (isTryingToAccessAdmin) {
      // Admin is trying to access the admin panel, which is allowed.
      return true;
    } else {
      // Admin is trying to access a non-admin page, redirect them to their panel.
      router.navigate(['/admin-panel']);
      return false;
    }
  } else if (userRole === 'EMPLOYEE') {
    if (isTryingToAccessAdmin) {
      // Employee is trying to access the admin panel, which is forbidden. Redirect them.
      router.navigate(['/dashboard']);
      return false;
    }
    // Employee is accessing a non-admin page, which is allowed.
    return true;
  }

  // Fallback for any other case (e.g., role not found), deny access and redirect to login.
  router.navigate(['/']);
  return false;
};