import { Component, ElementRef, HostListener, OnInit, OnDestroy, Inject, PLATFORM_ID } from '@angular/core';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { NavigationEnd, Router, RouterModule } from '@angular/router';
import { filter, Subscription, combineLatest } from 'rxjs'; // Import combineLatest
import { EmployeeService } from '../services/employee.service';
import { Employee } from '../models/employee.model';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css'
})
export class Navbar implements OnInit, OnDestroy { // Implement OnInit and OnDestroy
  isProfileMenuOpen = false;
  showNavbar = false;
  user: Employee | null = null;
  private subscriptions = new Subscription(); // Use a single subscription for cleanup

  constructor(
    private elementRef: ElementRef,
    private router: Router,
    private employeeService: EmployeeService,
    private authService: AuthService,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}

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
        // Show navbar only if logged in AND not on the login page
        this.showNavbar = isLoggedIn && !isLoginPage;

        if (isLoggedIn && !this.user && !isLoginPage) { // Only load user if logged in and not on login page
          this.loadUser();
        } else if (!isLoggedIn || isLoginPage) {
          this.user = null; // Clear user data if not logged in or on login page
        }
      })
    );
  }

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

  toggleProfileMenu() {
    this.isProfileMenuOpen = !this.isProfileMenuOpen;
  }
  
  logout() {
    this.authService.logout();
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

  ngOnDestroy() {
    // Clean up all subscriptions to prevent memory leaks
    this.subscriptions.unsubscribe();
  }
}
