import { Component, ElementRef, HostListener, OnInit, OnDestroy, Inject, PLATFORM_ID, ChangeDetectorRef } from '@angular/core';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { NavigationEnd, Router, RouterModule } from '@angular/router';
import { filter, Subscription, combineLatest } from 'rxjs'; // Import combineLatest
import { EmployeeService } from '../services/employee.service';
import { Employee } from '../models/employee.model';
import { AuthService } from '../services/auth.service';

/**
 * @Component
 * @description
 * The main application navigation bar. It is conditionally displayed based on user
 * authentication status and route. It provides navigation links and a user profile
 * menu with a logout option.
 */
@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css'
})
export class Navbar implements OnInit, OnDestroy {
  /** A flag to control the visibility of the user profile dropdown menu. */
  isProfileMenuOpen = false;
  /** A flag that controls the overall visibility of the navbar. */
  showNavbar = false;
  /** Holds the data for the currently logged-in user. */
  user: Employee | null = null;
  /** The role of the currently logged-in user (e.g., 'ADMIN', 'EMPLOYEE'). */
  userRole: string | null = null;
  /** Manages all component subscriptions to prevent memory leaks. */
  private subscriptions = new Subscription();

  /**
   * @constructor
   * @param {ElementRef} elementRef - A reference to the component's host element, used for detecting outside clicks.
   * @param {Router} router - The Angular router for tracking navigation events.
   * @param {EmployeeService} employeeService - The service for fetching employee data.
   * @param {AuthService} authService - The service for handling authentication status.
   * @param {Object} platformId - An injection token to determine if the code is running on a browser platform.
   */
  constructor(
    private elementRef: ElementRef,
    private router: Router,
    private employeeService: EmployeeService,
    private authService: AuthService,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}

  /**
   * @description
   * Angular lifecycle hook that runs on component initialization.
   * Subscribes to login status and route changes to determine navbar visibility and load user data.
   */
  ngOnInit() {
    // Combine isLoggedIn status with router events to determine navbar visibility
    this.subscriptions.add(
      combineLatest([
        this.authService.isLoggedIn$,
        this.router.events.pipe(
          filter((event): event is NavigationEnd => event instanceof NavigationEnd)
        )
      ]).subscribe(([isLoggedIn, event]) => {
        const isLoginPage = event.urlAfterRedirects === '/';
        if (isPlatformBrowser(this.platformId)) {
          // Show navbar only if logged in AND not on the login page
          this.showNavbar = isLoggedIn && !isLoginPage;
          this.userRole = localStorage.getItem('userRole');
        }

        if (isLoggedIn && !this.user && !isLoginPage) { // Only load user if logged in and not on login page
          this.loadUser();
        } else if (!isLoggedIn || isLoginPage) {
          this.user = null; // Clear user data if not logged in or on login page
        }
      })
    );
  }

  /**
   * @description
   * A private helper method to fetch the current user's data from the backend.
   */
  private loadUser(): void {
    if (isPlatformBrowser(this.platformId)) {
      const employeeId = localStorage.getItem('employeeId');
      if (employeeId) {
        this.employeeService.getEmployeeById(employeeId).subscribe(userData => {
          this.user = userData;
        });
      }
    }
  }

  /**
   * @description
   * Toggles the visibility of the user profile dropdown menu.
   */
  toggleProfileMenu() {
    this.isProfileMenuOpen = !this.isProfileMenuOpen;
  }
  
  /**
   * @description
   * Logs the user out, closes the profile menu, and navigates to the login page.
   */
  logout() {
    this.authService.logout();
    this.isProfileMenuOpen = false;
    this.router.navigate(['']);
  }

  /**
   * @description
   * A host listener that detects clicks on the document to close the profile menu if the click is outside of it.
   */
  @HostListener('document:click', ['$event'])
  onDocumentClick(event: MouseEvent) {
    // Check if the click is outside of the profile menu
    if (
      this.isProfileMenuOpen &&
      !this.elementRef.nativeElement.querySelector('.profile').contains(event.target)
    ) {
      this.isProfileMenuOpen = false;
    }
  }

  /**
   * @description
   * Angular lifecycle hook that runs on component destruction.
   * Unsubscribes from all active subscriptions to prevent memory leaks.
   */
  ngOnDestroy() {
    // Clean up all subscriptions to prevent memory leaks
    this.subscriptions.unsubscribe();
  }
}
