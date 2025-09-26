import { Component, OnInit, OnDestroy, Inject, PLATFORM_ID } from '@angular/core';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { NavigationEnd, Router } from '@angular/router';
import { filter, Subscription, combineLatest } from 'rxjs';
import { AuthService } from '../services/auth.service';

/**
 * @Component
 * @description
 * The application's footer component. It displays copyright information and is
 * conditionally shown based on the user's authentication status and current route.
 */
@Component({
  selector: 'app-footer',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './footer.html',
  styleUrl: './footer.css',
})
export class Footer implements OnInit, OnDestroy {
  /** The current year, used for the copyright notice. */
  currentYear = new Date().getFullYear();
  /** A flag that controls the visibility of the footer in the template. */
  showFooter = false;
  /** Manages subscriptions to prevent memory leaks. */
  private subscriptions = new Subscription();

  /**
   * @constructor
   * @param {Router} router - The Angular router for tracking navigation events.
   * @param {AuthService} authService - The service for checking user authentication status.
   * @param {Object} platformId - An injection token to determine if the code is running on a browser platform.
   */
  constructor(
    private router: Router,
    private authService: AuthService,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}

  /**
   * @description
   * Angular lifecycle hook that runs on component initialization.
   * It subscribes to login status and route changes to determine if the footer should be visible.
   * The footer is shown only when the user is logged in and not on the login page.
   */
  ngOnInit() {
    this.subscriptions.add(
      combineLatest([
        this.authService.isLoggedIn$,
        this.router.events.pipe(
          filter((event): event is NavigationEnd => event instanceof NavigationEnd)
        ),
      ]).subscribe(([isLoggedIn, event]) => {
        const isLoginPage = event.urlAfterRedirects === '/';
        if (isPlatformBrowser(this.platformId)) {
          this.showFooter = isLoggedIn && !isLoginPage;
        }
      })
    );
  }

  /**
   * @description
   * Angular lifecycle hook that runs on component destruction.
   * It unsubscribes from all active subscriptions to prevent memory leaks.
   */
  ngOnDestroy() {
    this.subscriptions.unsubscribe();
  }
}
