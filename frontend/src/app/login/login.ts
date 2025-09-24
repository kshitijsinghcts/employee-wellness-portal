import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class Login {
  activeTab: 'login' | 'register' = 'login';
  isLoading = false;
  showPassword = false;

  loginData = { email: '', password: '' };
  registerData = { employeeId: null as number | null, password: '', name: '', email: '' };

  constructor(private authService: AuthService, private router: Router) {}

  handleLogin() {
    this.isLoading = true;
    console.log('Logging in with:', this.loginData);
    this.authService.login(this.loginData).subscribe({
      next: (response) => {
        console.log('Login successful', response);
        // The AuthService now handles saving the token and updating the login state.
        this.isLoading = false;
        // Navigate to the dashboard on successful login.
        this.router.navigate(['/dashboard']);
      },
      error: (error) => {
        console.error('Login failed', error);
        // TODO: Show an error message to the user
        this.isLoading = false;
      }
    });
  }

  handleRegister() {
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
