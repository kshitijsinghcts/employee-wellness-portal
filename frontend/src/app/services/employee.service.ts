import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Employee } from '../models/employee.model';

/**
 * @Injectable
 * @description
 * Service for handling employee-related operations.
 */
@Injectable({
  providedIn: 'root'
})
export class EmployeeService {
  /**
   * The base URL for the employee API endpoints.
   */
  private apiUrl = 'http://localhost:9999/api/employees';

  /**
   * @constructor
   * @param {HttpClient} http - The Angular HttpClient for making HTTP requests.
   */
  constructor(private http: HttpClient) { }

  /**
   * Fetches an employee by their ID.
   * @param {string} id - The ID of the employee to fetch.
   * @returns {Observable<Employee>} An observable of the employee data.
   */
  getEmployeeById(id: string): Observable<Employee> {
    return this.http.get<Employee>(`${this.apiUrl}/${id}`);
  }
}