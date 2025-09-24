import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

export interface Resource {
  id: number;
  title: string;
  description: string;
  url: string;
  category: 'mental_health' | 'physical_health' | 'nutrition' | 'mindfulness';
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

  categories: Resource['category'][] = ['mental_health', 'physical_health', 'nutrition', 'mindfulness'];
  types: Resource['type'][] = ['article', 'video', 'pdf'];

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
    return category.replace(/_/g, ' ').replace(/\b\w/g, char => char.toUpperCase());
  }

  formatTypeName(type: string): string {
    return type.charAt(0).toUpperCase() + type.slice(1);
  }

  private getMockResources(): Resource[] {
    return [
      {
        id: 1,
        title: 'The Benefits of Mindfulness Meditation',
        description: 'Learn how daily meditation can reduce stress and improve focus. This comprehensive guide covers various techniques.',
        url: '#',
        category: 'mindfulness',
        type: 'article',
        tags: ['Stress Reduction', 'Focus', 'Well-being'],
        featured: true,
      },
      {
        id: 2,
        title: '10-Minute Morning Yoga for Energy',
        description: 'Start your day with this quick and energizing yoga routine. Perfect for all skill levels.',
        url: '#',
        category: 'physical_health',
        type: 'video',
        tags: ['Yoga', 'Exercise', 'Energy'],
        featured: true,
      },
      {
        id: 3,
        title: 'Healthy Eating Plate Guide',
        description: 'A downloadable guide to creating balanced and nutritious meals every day.',
        url: '#',
        category: 'nutrition',
        type: 'pdf',
        tags: ['Nutrition', 'Diet', 'Healthy Eating'],
        featured: true,
      },
      {
        id: 4,
        title: 'Understanding Anxiety and How to Cope',
        description: 'An in-depth article on the causes of anxiety and practical strategies for managing it.',
        url: '#',
        category: 'mental_health',
        type: 'article',
        tags: ['Anxiety', 'Mental Health', 'Coping'],
        featured: false,
      },
      {
        id: 5,
        title: 'Guide to Strength Training',
        description: 'A PDF guide covering the fundamentals of strength training, including sample workout plans.',
        url: '#',
        category: 'physical_health',
        type: 'pdf',
        tags: ['Fitness', 'Workout', 'Strength'],
        featured: false,
      },
      {
        id: 6,
        title: 'Cooking for Better Brain Health',
        description: 'A video tutorial on preparing meals that are packed with brain-boosting nutrients.',
        url: '#',
        category: 'nutrition',
        type: 'video',
        tags: ['Cooking', 'Brain Health', 'Recipe'],
        featured: false,
      },
    ];
  }
}
