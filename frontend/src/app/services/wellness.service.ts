import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { WellnessMetric } from '../models/wellness-metric.model';

@Injectable({
  providedIn: 'root'
})
export class WellnessService {
  private apiUrl = 'http://localhost:9999/api/wellness';

  constructor(private http: HttpClient) { }

  getWellnessMetrics(employeeId: string): Observable<WellnessMetric[]> {
    return this.http.get<WellnessMetric[]>(`${this.apiUrl}/${employeeId}`);
  }

  submitMetrics(metric: Partial<WellnessMetric>): Observable<WellnessMetric> {
    return this.http.post<WellnessMetric>(`${this.apiUrl}/submit-metrics`, metric);
  }
}
