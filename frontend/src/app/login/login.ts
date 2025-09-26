import { Component, OnInit, Inject, PLATFORM_ID } from '@angular/core';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../services/auth.service';
import { Router, RouterModule } from '@angular/router';

/**
 * @Component
 * @description
 * The Login component handles both user login and registration.
 * It provides a tabbed interface for switching between the two forms.
 */
@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class Login implements OnInit {
  /** The currently active tab, either 'login' or 'register'. */
  activeTab: 'login' | 'register' = 'login';
  /** A flag to indicate when an async operation (login/register) is in progress. */
  isLoading = false;
  /** A flag to toggle password visibility in the form. */
  showPassword = false;

  /** Holds the error message for a failed login attempt. */
  loginError: string | null = null;
  /** Holds the error message for a failed registration attempt. */
  registerError: string | null = null;

  /** The data model for the login form. */
  loginData = { email: '', password: '' };
  /** The data model for the registration form. */
  registerData = { employeeId: null as number | null, password: '', name: '', email: '' };

  /**
   * @constructor
   * @param {AuthService} authService - The service for handling authentication logic.
   * @param {Router} router - The Angular router for navigation.
   * @param {Object} platformId - An injection token to determine if the code is running on a browser platform.
   */
  constructor(
    private authService: AuthService,
    private router: Router,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}

  /**
   * @description
   * Angular lifecycle hook that runs on component initialization.
   * Checks if a user is already logged in and redirects them if necessary.
   */
  ngOnInit(): void {
    if (isPlatformBrowser(this.platformId)) {
      // If user is already logged in, redirect them
      const userRole = localStorage.getItem('userRole');
      if (userRole) {
        this.redirectUser(userRole);
      }
    }
  }

  /**
   * @description
   * Redirects the user to the appropriate dashboard based on their role.
   * @param {string} role - The user's role ('ADMIN' or 'EMPLOYEE').
   */
  private redirectUser(role: string): void {
    if (role === 'ADMIN') {
      this.router.navigate(['/admin-panel']);
    } else {
      this.router.navigate(['/dashboard']);
    }
  }

  /**
   * @description
   * Handles the login form submission. It calls the AuthService to log the user in
   * and redirects upon success or displays an error on failure.
   */
  handleLogin(): void {
    this.isLoading = true;
    this.loginError = null;
    this.authService.login(this.loginData).subscribe({
      next: (response) => {
        this.isLoading = false;        
        // AuthService handles storing token/info.
        // We just need to get the role to redirect.
        const userRole = localStorage.getItem('userRole');
        if (userRole) {
          this.redirectUser(userRole);
        } else {
          // Fallback or error handling if role isn't set
          console.error('User role not found after login.');
          this.router.navigate(['/dashboard']); // Default redirect
        }
      },
      error: (error) => {
        this.loginError = error.error || 'An unknown login error occurred.';
        console.error('Login failed', this.loginError);
        this.isLoading = false;
      }
    });
  }

  /**
   * @description
   * Handles the registration form submission. It calls the AuthService to register
   * a new user and switches to the login tab on success or displays an error on failure.
   */
  handleRegister(): void {
    this.isLoading = true;
    this.registerError = null;
    this.authService.register(this.registerData).subscribe({
      next: (response) => {
        console.log('Registration successful', response);
        // TODO: Handle successful registration, e.g., switch to login tab
        this.isLoading = false;
        this.activeTab = 'login';
      },
      error: (error) => {
        this.registerError = error.error || 'An unknown registration error occurred.';
        console.error('Registration failed', this.registerError);
        this.isLoading = false;
      }
    });
  }
}
