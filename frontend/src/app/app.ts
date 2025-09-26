import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, NavigationEnd, Router, RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Navbar } from './navbar/navbar';
import { Footer } from './footer/footer';
import { Title } from '@angular/platform-browser';
import { filter, map } from 'rxjs/operators';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, CommonModule, Navbar, Footer],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App implements OnInit {
  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private titleService: Title
  ) {}

  ngOnInit() {
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd),
      map(() => {
        let child = this.activatedRoute.firstChild;
        while (child?.firstChild) {
          child = child.firstChild;
        }
        // Use the title from the route data, or a default title
        return child?.snapshot.data['title'] || 'Wellness Portal';
      })
    ).subscribe((title: string) => {
      this.titleService.setTitle(title);
    });
  }
}
