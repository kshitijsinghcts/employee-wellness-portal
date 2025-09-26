import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { WellnessMetric } from '../models/wellness-metric.model';

/**
 * @Injectable
 * @description
 * Service for managing employee wellness metrics, including fetching and submitting metrics.
 */
@Injectable({
  providedIn: 'root'
})
export class WellnessService {
  /**
   * The base URL for the wellness API endpoints.
   */
  private apiUrl = 'http://localhost:9999/api/wellness';

  /**
   * @constructor
   * @param {HttpClient} http - The Angular HttpClient for making HTTP requests.
   */
  constructor(private http: HttpClient) { }

  /**
   * Fetches wellness metrics for a specific employee.
   * @param {string} employeeId - The ID of the employee whose metrics are to be fetched.
   * @returns {Observable<WellnessMetric[]>} An observable of an array of wellness metrics.
   */
  getWellnessMetrics(employeeId: string): Observable<WellnessMetric[]> {
    return this.http.get<WellnessMetric[]>(`${this.apiUrl}/${employeeId}`);
  }

  /**
   * Submits wellness metrics for an employee.
   * @param {Partial<WellnessMetric>} metric - The wellness metric data to submit.
   * @returns {Observable<WellnessMetric>} An observable of the submitted wellness metric.
   */
  submitMetrics(metric: Partial<WellnessMetric>): Observable<WellnessMetric> {
    return this.http.post<WellnessMetric>(`${this.apiUrl}/submit-metrics`, metric);
  }
}
