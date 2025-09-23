import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Reward {
  id: number;
  employeeId: number;
  title: string;
  description: string;
  achievedDate: string; // ISO date string e.g., "2024-07-20"
}

@Injectable({
  providedIn: 'root'
})
export class RewardsService {
  private apiUrl = 'http://localhost:9999/api/employeeRewards';

  constructor(private http: HttpClient) { }

  getRewards(employeeId: string): Observable<Reward[]> {
    return this.http.get<Reward[]>(`${this.apiUrl}/${employeeId}`);
  }
}