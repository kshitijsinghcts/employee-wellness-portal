export interface AuthUser {
  employeeId: number;   // Unique ID for the user
  email: string;        // Used for login and role detection
  password: string;     // Authentication credential
  role: string;         // Either 'ADMIN' or 'EMPLOYEE'
}
