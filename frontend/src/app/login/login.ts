import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

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
  registerData = { email: '', password: '', name: '', department: '' };

  handleLogin() {
    this.isLoading = true;
    console.log('Logging in with:', this.loginData);
    // Mock API call
    setTimeout(() => { this.isLoading = false; }, 1500);
  }

  handleRegister() {
    this.isLoading = true;
    console.log('Registering with:', this.registerData);
    // Mock API call
    setTimeout(() => { this.isLoading = false; }, 1500);
  }
}
