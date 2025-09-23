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
      title: '10-Minute MORNING MEDITATION: Wake Up Early & Start a Good Day!',
      description: 'A guided morning meditation to help you start your day with clarity, intention, and inner peace.',
      type: 'video',
      url: 'https://www.youtube.com/watch?v=FfWVh5ieG3c',
      category: 'mental-health',
      tags: ['meditation', 'mindfulness', 'morning routine'],
      featured: true,
    },
    {
      id: '2',
      title: 'Computer Workstation Ergonomics Self-Setup Guide',
      description: 'A practical PDF guide to setting up your workstation ergonomically to prevent discomfort and injury.',
      type: 'pdf',
      url: 'https://ehs.prod.fbweb.psu.edu/sites/ehs/files/ergonomics_self_setup_guide.pdf',
      category: 'ergonomics',
      tags: ['ergonomics', 'posture', 'workspace', 'back pain'],
      featured: true,
    },
    {
      id: '3',
      title: 'Healthy Meal Prep Ideas for Busy Professionals',
      description: 'Quick, high-protein recipes and smart strategies to simplify healthy eating for busy schedules.',
      type: 'article',
      url: 'https://blog.eatfitlifefoods.com/healthy-meal-prep-ideas-for-busy-professionals/',
      category: 'nutrition',
      tags: ['nutrition', 'meal prep', 'healthy eating', 'recipes'],
      featured: false,
    },
    {
      id: '4',
      title: '15 Min Desk Exercises for Beginners',
      description: 'Burn fat and stay active during work hours with this no-equipment desk workout video.',
      type: 'video',
      url: 'https://www.youtube.com/watch?v=_tQMtw_0QLA',
      category: 'fitness',
      tags: ['fitness', 'desk exercises', 'workplace wellness'],
      featured: true,
    },
    {
      id: '5',
      title: 'Stress Management: 10 Techniques & Quick Tips',
      description: 'Evidence-based strategies to reduce anxiety, prevent burnout, and build resilience.',
      type: 'article',
      url: 'https://positivepsychology.com/stress-management-techniques-tips-burn-out/',
      category: 'mental-health',
      tags: ['stress management', 'resilience', 'mental health'],
      featured: false,
    },
    {
      id: '6',
      title: '30 Sleep Hygiene Checklist',
      description: 'A printable checklist with 30 actionable tips to improve your sleep quality and habits.',
      type: 'pdf',
      url: 'https://thesleepsavvy.com/wp-content/uploads/2019/10/30-SLEEP-HYGIENE-CHECKLIST.pdf',
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
