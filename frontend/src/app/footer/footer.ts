import { Component, OnInit, OnDestroy, Inject, PLATFORM_ID } from '@angular/core';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { NavigationEnd, Router } from '@angular/router';
import { filter, Subscription, combineLatest } from 'rxjs';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-footer',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './footer.html',
  styleUrl: './footer.css',
})
export class Footer implements OnInit, OnDestroy {
  currentYear = new Date().getFullYear();
  showFooter = false;
  private subscriptions = new Subscription();

  constructor(
    private router: Router,
    private authService: AuthService,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}

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

  ngOnDestroy() {
    this.subscriptions.unsubscribe();
  }
}
