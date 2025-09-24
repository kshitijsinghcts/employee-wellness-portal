import { Routes } from '@angular/router';
import { Login } from './login/login'
import { DashboardComponent } from './dashboard/dashboard';
import { Resources } from './resources/resources';
import { Goals } from './goals/goals';
import { Surveys } from './surveys/surveys';
import { authGuard } from './auth.guard';

export const routes: Routes = [
    // The login route at the root path, accessible to everyone.
    { path: '', component: Login },

    // Protected routes that require the user to be logged in.
    {path: 'dashboard', component: DashboardComponent, canActivate: [authGuard]},
    {path: 'resources', component: Resources, canActivate: [authGuard]},
    {path: 'goals', component: Goals, canActivate: [authGuard]},
    {path: 'surveys', component: Surveys, canActivate: [authGuard]},
    // A wildcard route to redirect any unknown paths back to the login page.
    { path: '**', redirectTo: '' }
];
