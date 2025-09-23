import { Routes } from '@angular/router';
import { Login } from './login/login';
import { DashboardComponent } from './dashboard/dashboard';

export const routes: Routes = [
    {path: '', component: Login},
    {path: 'dashboard', component: DashboardComponent}
];
