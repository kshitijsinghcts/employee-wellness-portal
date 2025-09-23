import { Component, ElementRef, HostListener } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css'
})
export class Navbar {
  isProfileMenuOpen = false;

  constructor(private elementRef: ElementRef) {}

  toggleProfileMenu() {
    this.isProfileMenuOpen = !this.isProfileMenuOpen;
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
