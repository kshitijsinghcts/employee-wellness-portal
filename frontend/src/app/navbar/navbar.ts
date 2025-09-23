import { Component, ElementRef, HostListener, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavigationEnd, Router, RouterModule } from '@angular/router';
import { filter } from 'rxjs';
import { EmployeeService } from '../services/employee.service';
import { Employee } from '../models/employee.model';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css'
})
export class Navbar {
  isProfileMenuOpen = false;
  showNavbar = true;
  user: Employee | null = null;

  constructor(
    private elementRef: ElementRef,
    private router: Router,
    private employeeService: EmployeeService
  ) {
    this.router.events
      .pipe(filter((event): event is NavigationEnd => event instanceof NavigationEnd))
      .subscribe((event: NavigationEnd) => {
        // Hide navbar on the login page (path: '')
        const isLoginPage = event.urlAfterRedirects === '/';
        this.showNavbar = !isLoginPage;

        if (this.showNavbar && !this.user) {
          this.loadUser();
        } else if (isLoginPage) {
          this.user = null; // Clear user data when on login page
        }
      });
  }

  private loadUser(): void {
    const employeeId = localStorage.getItem('employeeId');
    if (employeeId) {
      this.employeeService.getEmployeeById(employeeId).subscribe(userData => {
        this.user = userData;
      });
    }
  }

  toggleProfileMenu() {
    this.isProfileMenuOpen = !this.isProfileMenuOpen;
  }

  logout() {
    localStorage.removeItem('employeeId');
    localStorage.removeItem('userRole');
    localStorage.removeItem('token');
    this.isProfileMenuOpen = false;
    this.router.navigate(['']);
  }

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
}
