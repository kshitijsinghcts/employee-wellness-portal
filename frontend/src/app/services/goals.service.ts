import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Goal } from '../goals/goals';

/**
 * @Injectable
 * @description
 * Service for managing employee goals, including fetching, creating, updating, and deleting goals.
 */
@Injectable({
  providedIn: 'root'
})
export class GoalsService {
  /**
   * The base URL for the goals API endpoints.
   */
  private apiUrl = 'http://localhost:9999/api/goals';

  /**
   * @constructor
   * @param {HttpClient} http - The Angular HttpClient for making HTTP requests.
   */
  constructor(private http: HttpClient) { }

  /**
   * Fetches goals for a specific employee.
   * @param {number} employeeId - The ID of the employee whose goals are to be fetched.
   * @returns {Observable<Goal[]>} An observable of an array of goals.
   */
  getGoals(employeeId: number): Observable<Goal[]> {
    return this.http.get<Goal[]>(`${this.apiUrl}/${employeeId}`);
  }

  /**
   * Creates a new goal.
   * @param {Partial<Goal>} goal - The goal data to create.
   * @returns {Observable<string>} An observable of the response message.
   */
  createGoal(goal: Partial<Goal>): Observable<string> {
    return this.http.post(`${this.apiUrl}/create`, goal, { responseType: 'text' });
  }

  /**
   * Updates an existing goal.
   * @param {Goal} goal - The goal data to update.
   * @returns {Observable<string>} An observable of the response message.
   */
  updateGoal(goal: Goal): Observable<string> {
    return this.http.put(`${this.apiUrl}/update`, goal, { responseType: 'text' });
  }

  /**
   * Deletes a goal by its ID.
   * @param {number} goalId - The ID of the goal to delete.
   * @returns {Observable<void>} An observable that completes upon deletion.
   */
  deleteGoal(goalId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/delete/${goalId}`);
  }
}