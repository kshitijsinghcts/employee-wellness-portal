import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Employee } from '../models/employee.model';

/**
 * @Injectable
 * @description
 * Service for administrative tasks, such as fetching all employees.
 */
@Injectable({
  providedIn: 'root'
})
export class AdminService {
  /**
   * The base URL for the admin API endpoints.
   */
  private apiUrl = 'http://localhost:9999/api';

  /**
   * @constructor
   * @param {HttpClient} http - The Angular HttpClient for making HTTP requests.
   */
  constructor(private http: HttpClient) {}

  /**
   * Fetches all employees for the admin panel.
   * @returns {Observable<Employee[]>} An observable of an array of employees.
   */
  getAllEmployees(): Observable<Employee[]> {
    return this.http.get<Employee[]>(`${this.apiUrl}/employees`);
  }
}