import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { tap } from 'rxjs/operators';
import { isPlatformBrowser } from '@angular/common';

/**
 * @interface LoginCredentials
 * @description Represents the credentials required for a user to log in.
 */
export interface LoginCredentials {
    email: string;
    password?: string;
}

/**
 * @interface AuthResponse
 * @description Represents the response from the authentication API, containing a token.
 */
export interface AuthResponse {
    token: string;
}

/**
 * @Injectable
 * @description
 * Service for handling user authentication, including login, registration, and logout.
 * It manages the user's login state and stores authentication tokens.
 */
@Injectable({
    providedIn: 'root'
})
export class AuthService {
    /** The base URL for the authentication API endpoints. */
    private apiUrl = 'http://localhost:9999/api/auth';
    /** BehaviorSubject to hold the current login status. */
    private _isLoggedIn$ = new BehaviorSubject<boolean>(false);
    /** Observable to subscribe to for login status changes. */
    isLoggedIn$ = this._isLoggedIn$.asObservable();

    /**
     * @constructor
     * @param {HttpClient} http - The Angular HttpClient for making HTTP requests.
     * @param {Object} platformId - An injection token to determine if the code is running on a browser platform.
     */
    constructor(private http: HttpClient, @Inject(PLATFORM_ID) private platformId: Object) {
        if (isPlatformBrowser(this.platformId)) {
            // On service initialization, check if a token exists and update the login status.
            // This ensures the state is restored on page refresh.
            const token = localStorage.getItem('token');
            this._isLoggedIn$.next(!!token);
        }
    }

    /**
     * Logs in a user with the given credentials.
     * @param {LoginCredentials} credentials - The user's email and password.
     * @returns {Observable<string>} An observable of the authentication token string.
     */
    login(credentials: LoginCredentials): Observable<string> {
        return this.http.post(`${this.apiUrl}/login`, credentials, { responseType: 'text' }).pipe(
            tap(response => {
                if (isPlatformBrowser(this.platformId)) {
                    // 1. Save token to local storage for future API calls
                    localStorage.setItem('token', response);

                    // 2. Extract employee ID and role from token and save it
                    const tokenParts = response.split('-');
                    const role = tokenParts[3]; // 'admin' or 'employee'
                    const employeeId = tokenParts[4];
                    localStorage.setItem('employeeId', employeeId);
                    localStorage.setItem('userRole', role.toUpperCase()); // Store as EMPLOYEE or ADMIN
                }

                // 3. Update the logged-in status
                this._isLoggedIn$.next(true);
            })
        );
    }
    
    /**
     * Registers a new user.
     * @param {any} userData - The data for the new user.
     * @returns {Observable<any>} An observable of the registration response.
     */
    register(userData: any): Observable<any> {
        return this.http.post(`${this.apiUrl}/register`, userData, { responseType: 'text' });
    }

    /**
     * Checks if the user is currently logged in.
     * This method is the single source of truth for the login status.
     * @returns {boolean} True if the user is logged in, false otherwise.
     */
    isLoggedIn(): boolean {
        return this._isLoggedIn$.getValue();
    }

    /**
     * Logs out the current user by clearing authentication data from local storage.
     */
    logout(): void {
        if (isPlatformBrowser(this.platformId)) {
            localStorage.removeItem('token');
            localStorage.removeItem('employeeId');
            localStorage.removeItem('userRole');
        }
        this._isLoggedIn$.next(false);
    }

    /**
     * Retrieves the user's role from local storage.
     * @returns {string | null} The user's role ('ADMIN', 'EMPLOYEE') or null if not found.
     */
    getUserRole(): string | null {
        if (isPlatformBrowser(this.platformId)) {
            return localStorage.getItem('userRole');
        }
        return null;
    }
}
