import { Routes } from '@angular/router';
import { Login } from './login/login'
import { DashboardComponent } from './dashboard/dashboard';
import { Resources } from './resources/resources';
import { Goals } from './goals/goals';
import { Surveys } from './surveys/surveys';
import { AdminPanelComponent } from './admin-panel/admin-panel';
import { authGuard } from './auth.guard';

export const routes: Routes = [
    // The login route at the root path, accessible to everyone.
    { path: '', component: Login, title: 'Login | Wellness Portal' },

    // Protected routes that require the user to be logged in.
    {path: 'dashboard', component: DashboardComponent, canActivate: [authGuard], title: 'Dashboard | Wellness Portal'},
    {path: 'resources', component: Resources, canActivate: [authGuard], title: 'Resources | Wellness Portal'},
    {path: 'goals', component: Goals, canActivate: [authGuard], title: 'Goals | Wellness Portal'},
    {path: 'surveys', component: Surveys, canActivate: [authGuard],     title: 'Surveys | Wellness Portal'},
    {path: 'admin-panel', component: AdminPanelComponent, canActivate: [authGuard], title: 'Admin Panel | Wellness Portal'},
    // A wildcard route to redirect any unknown paths back to the login page.
    { path: '**', redirectTo: '' }
];
