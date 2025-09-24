import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { tap } from 'rxjs/operators';
import { isPlatformBrowser } from '@angular/common';

export interface LoginCredentials {
    email: string;
    password?: string;
}

export interface AuthResponse {
    token: string;
}

@Injectable({
    providedIn: 'root'
})
export class AuthService {
    private apiUrl = 'http://localhost:9999/api/auth';
    private _isLoggedIn$ = new BehaviorSubject<boolean>(false);
    isLoggedIn$ = this._isLoggedIn$.asObservable();

    constructor(private http: HttpClient, @Inject(PLATFORM_ID) private platformId: Object) {
        if (isPlatformBrowser(this.platformId)) {
            // On service initialization, check if a token exists and update the login status.
            // This ensures the state is restored on page refresh.
            const token = localStorage.getItem('token');
            this._isLoggedIn$.next(!!token);
        }
    }

    login(credentials: LoginCredentials): Observable<string> {
        return this.http.post(`${this.apiUrl}/login`, credentials, { responseType: 'text' }).pipe(
            tap(response => {
                // 1. Save token to local storage for future API calls
                localStorage.setItem('token', response);

                // 2. Extract employee ID and role from token and save it
                const tokenParts = response.split('-');
                const role = tokenParts[3]; // 'admin' or 'employee'
                const employeeId = tokenParts[4];
                localStorage.setItem('employeeId', employeeId);
                localStorage.setItem('userRole', role.toUpperCase()); // Store as EMPLOYEE or ADMIN

                // 3. Update the logged-in status
                this._isLoggedIn$.next(true);
            })
        );
    }
    
    register(userData: any): Observable<any> {
        return this.http.post(`${this.apiUrl}/register`, userData, { responseType: 'text' });
    }

    // This method is now the single source of truth for the login status.
    isLoggedIn(): boolean {
        return this._isLoggedIn$.getValue();
    }

    logout(): void {
        if (isPlatformBrowser(this.platformId)) {
            localStorage.removeItem('token');
            localStorage.removeItem('employeeId');
            localStorage.removeItem('userRole');
        }
        this._isLoggedIn$.next(false);
    }
}
