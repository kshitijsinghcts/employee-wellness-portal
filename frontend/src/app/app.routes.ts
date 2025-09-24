import { Routes } from '@angular/router';
import { Login } from './login/login';
import { DashboardComponent } from './dashboard/dashboard';
import { AdminPanelComponent } from './admin-panel/admin-panel';

export const routes: Routes = [
    {path: '', component: Login},
    {path: 'home', component: DashboardComponent},
    {path: 'admin', component: AdminPanelComponent}
];
