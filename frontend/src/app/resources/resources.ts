import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

export interface Resource {
  id: string;
  title: string;
  description: string;
  url: string;
  category: 'mental-health' | 'ergonomics' | 'nutrition' | 'fitness' | 'general';
  type: 'article' | 'video' | 'pdf';
  tags: string[];
  featured: boolean;
}

@Component({
  selector: 'app-resources',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './resources.html',
  styleUrls: ['./resources.css']
})
export class Resources implements OnInit {
  allResources: Resource[] = [];
  filteredResources: Resource[] = [];
  featuredResources: Resource[] = [];

  searchTerm = '';
  selectedCategory: 'all' | Resource['category'] = 'all';
  selectedType: 'all' | Resource['type'] = 'all';

  categories: Resource['category'][] = ['mental-health', 'ergonomics', 'nutrition', 'fitness', 'general'];
  types: Resource['type'][] = ['video', 'pdf', 'article'];

  ngOnInit() {
    this.allResources = this.getMockResources();
    this.applyFilters();
  }

  applyFilters() {
    let resources = this.allResources;

    if (this.searchTerm) {
      resources = resources.filter(r =>
        r.title.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        r.description.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        r.tags.some(tag => tag.toLowerCase().includes(this.searchTerm.toLowerCase()))
      );
    }

    if (this.selectedCategory !== 'all') {
      resources = resources.filter(r => r.category === this.selectedCategory);
    }

    if (this.selectedType !== 'all') {
      resources = resources.filter(r => r.type === this.selectedType);
    }

    this.filteredResources = resources;
    this.featuredResources = this.allResources.filter(r => r.featured);
  }

  formatCategoryName(category: string): string {
    return category
      .split('-')
      .map(word => word.charAt(0).toUpperCase() + word.slice(1))
      .join(' ');
  }

  formatTypeName(type: string): string {
    return type.charAt(0).toUpperCase() + type.slice(1);
  }

  private getMockResources(): Resource[] {
    return [
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
  }
}
