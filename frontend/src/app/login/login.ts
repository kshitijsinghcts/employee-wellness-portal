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
        // 1. Save token to local storage for future API calls
        localStorage.setItem('token', response.token);

        // 2. Extract employee ID from token and save it
        const tokenParts = response.token.split('-');
        const employeeId = tokenParts[tokenParts.length - 1];
        const role = tokenParts[tokenParts.length - 2]; // 'employee' or 'admin'
        localStorage.setItem('employeeId', employeeId);
        localStorage.setItem('userRole', role.toUpperCase()); // Store as EMPLOYEE or ADMIN

        this.isLoading = false;
        // 3. Navigate to the dashboard
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
