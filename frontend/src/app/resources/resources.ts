// import { Component } from '@angular/core';

// @Component({
//   selector: 'app-resources',
//   imports: [],
//   templateUrl: './resources.html',
//   styleUrl: './resources.css'
// })
// export class Resources {

// }
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

// Define the Resource interface based on wellness.ts
export interface Resource {
  id: string;
  title: string;
  description: string;
  type: 'article' | 'video' | 'pdf' | 'link';
  url: string;
  category: 'fitness' | 'nutrition' | 'mental-health' | 'ergonomics' | 'general';
  tags: string[];
  featured: boolean;
}

@Component({
  selector: 'app-resources',
  standalone: true,
  imports: [CommonModule, FormsModule], // Import necessary modules
  templateUrl: './resources.html',
  styleUrls: ['./resources.css']
})
export class Resources implements OnInit {
  // Mock data and state properties
  allResources: Resource[] = [
    {
      id: '1',
      title: '10-Minute Morning Meditation',
      description: 'Start your day with mindfulness and positive energy through this guided meditation session.',
      type: 'video',
      url: 'https://example.com/meditation-video',
      category: 'mental-health',
      tags: ['meditation', 'mindfulness', 'morning routine'],
      featured: true,
    },
    {
      id: '2',
      title: 'Ergonomic Workspace Setup Guide',
      description: 'Learn how to set up your home office or workspace to prevent back pain and improve posture.',
      type: 'pdf',
      url: 'https://example.com/ergonomic-guide.pdf',
      category: 'ergonomics',
      tags: ['ergonomics', 'posture', 'workspace', 'back pain'],
      featured: true,
    },
    {
      id: '3',
      title: 'Healthy Meal Prep for Busy Professionals',
      description: 'Simple, nutritious recipes and meal prep strategies for maintaining a healthy diet with a busy schedule.',
      type: 'article',
      url: 'https://example.com/meal-prep-article',
      category: 'nutrition',
      tags: ['nutrition', 'meal prep', 'healthy eating', 'recipes'],
      featured: false,
    },
    {
      id: '4',
      title: '15-Minute Desk Exercises',
      description: 'Quick and effective exercises you can do at your desk to stay active during the workday.',
      type: 'video',
      url: 'https://example.com/desk-exercises',
      category: 'fitness',
      tags: ['fitness', 'desk exercises', 'workplace wellness'],
      featured: true,
    },
    {
      id: '5',
      title: 'Stress Management Techniques',
      description: 'Evidence-based strategies for managing stress and building resilience in challenging situations.',
      type: 'article',
      url: 'https://example.com/stress-management',
      category: 'mental-health',
      tags: ['stress management', 'resilience', 'mental health'],
      featured: false,
    },
    {
      id: '6',
      title: 'Sleep Hygiene Checklist',
      description: 'A comprehensive checklist to improve your sleep quality and establish better sleep habits.',
      type: 'pdf',
      url: 'https://example.com/sleep-checklist.pdf',
      category: 'general',
      tags: ['sleep', 'sleep hygiene', 'rest', 'recovery'],
      featured: false,
    },
  ];

  // State for filters and search
  searchTerm: string = '';
  selectedCategory: string = 'all';
  selectedType: string = 'all';

  // Derived state
  filteredResources: Resource[] = [];
  featuredResources: Resource[] = [];
  categories: string[] = [];
  types: string[] = [];

  // Icon mappings
  categoryIcons: { [key: string]: string } = {
    fitness: 'dumbbell',
    nutrition: 'utensils',
    'mental-health': 'brain',
    ergonomics: 'monitor',
    general: 'heart',
  };

  typeIcons: { [key: string]: string } = {
    article: 'file-text',
    video: 'play',
    pdf: 'file-text',
    link: 'link',
  };

  ngOnInit(): void {
    // Initialize properties on component load
    this.featuredResources = this.allResources.filter(r => r.featured);
    this.categories = Array.from(new Set(this.allResources.map(r => r.category)));
    this.types = Array.from(new Set(this.allResources.map(r => r.type)));
    this.applyFilters();
  }

  applyFilters(): void {
    const term = this.searchTerm.toLowerCase();
    this.filteredResources = this.allResources.filter(resource => {
      const matchesSearch =
        resource.title.toLowerCase().includes(term) ||
        resource.description.toLowerCase().includes(term) ||
        resource.tags.some(tag => tag.toLowerCase().includes(term));

      const matchesCategory = this.selectedCategory === 'all' || resource.category === this.selectedCategory;
      const matchesType = this.selectedType === 'all' || resource.type === this.selectedType;

      return matchesSearch && matchesCategory && matchesType;
    });
  }

  // Helper to format category names for display
  formatCategoryName(category: string): string {
    return category
      .split('-')
      .map(word => word.charAt(0).toUpperCase() + word.slice(1))
      .join(' ');
  }

  // Helper to format type names for display
  formatTypeName(type: string): string {
    return type.charAt(0).toUpperCase() + type.slice(1);
  }
}
