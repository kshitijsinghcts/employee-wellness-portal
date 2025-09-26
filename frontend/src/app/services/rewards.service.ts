import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

/**
 * @interface Reward
 * @description Represents a reward achieved by an employee.
 */
export interface Reward {
  id: number;
  employeeId: number;
  title: string;
  description: string;
  achievedDate: string; // ISO date string e.g., "2024-07-20"
}

/**
 * @Injectable
 * @description
 * Service for fetching employee rewards.
 */
@Injectable({
  providedIn: 'root'
})
export class RewardsService {
  /**
   * The base URL for the employee rewards API endpoints.
   */
  private apiUrl = 'http://localhost:9999/api/employeeRewards';

  /**
   * @constructor
   * @param {HttpClient} http - The Angular HttpClient for making HTTP requests.
   */
  constructor(private http: HttpClient) { }

  /**
   * Fetches rewards for a specific employee.
   * @param {string} employeeId - The ID of the employee whose rewards are to be fetched.
   * @returns {Observable<Reward[]>} An observable of an array of rewards.
   */
  getRewards(employeeId: string): Observable<Reward[]> {
    return this.http.get<Reward[]>(`${this.apiUrl}/${employeeId}`);
  }
}