import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Goal } from '../goals/goals';

@Injectable({
  providedIn: 'root'
})
export class GoalsService {
  private apiUrl = 'http://localhost:9999/api/goals';

  constructor(private http: HttpClient) { }

  // Fetch goals for a specific employee
  getGoals(employeeId: number): Observable<Goal[]> {
    return this.http.get<Goal[]>(`${this.apiUrl}/${employeeId}`);
  }

  // Create a new goal
  createGoal(goal: Partial<Goal>): Observable<string> {
    return this.http.post(`${this.apiUrl}/create`, goal, { responseType: 'text' });
  }

  // Update an existing goal
  updateGoal(goal: Goal): Observable<string> {
    return this.http.put(`${this.apiUrl}/update`, goal, { responseType: 'text' });
  }

  // Delete a goal by its ID
  deleteGoal(goalId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/delete/${goalId}`);
  }
}