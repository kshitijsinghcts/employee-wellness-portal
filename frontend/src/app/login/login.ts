import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../services/auth.service';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class Login implements OnInit {
  activeTab: 'login' | 'register' = 'login';
  isLoading = false;
  showPassword = false;

  loginData = { email: '', password: '' };
  registerData = { employeeId: null as number | null, password: '', name: '', email: '' };

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    // If user is already logged in, redirect them
    const userRole = localStorage.getItem('userRole');
    if (userRole) {
      this.redirectUser(userRole);
    }
  }

  private redirectUser(role: string): void {
    if (role === 'ADMIN') {
      this.router.navigate(['/admin-panel']);
    } else {
      this.router.navigate(['/dashboard']);
    }
  }

  handleLogin(): void {
    this.isLoading = true;
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
        console.error('Login failed', error);
        // TODO: Show an error message to the user
        this.isLoading = false;
      }
    });
  }

  handleRegister(): void {
    this.isLoading = true;
    console.log('Registering with:', this.registerData);
    this.authService.register(this.registerData).subscribe({
      next: (response) => {
        console.log('Registration successful', response);
        // TODO: Handle successful registration, e.g., switch to login tab
        this.isLoading = false;
        this.activeTab = 'login';
      },
      error: (error) => {
        console.error('Registration failed', error);
        // TODO: Show an error message to the user
        this.isLoading = false;
      }
    });
  }
}
