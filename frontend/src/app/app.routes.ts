import { Routes } from '@angular/router';
import { Login } from './login/login';
import { DashboardComponent } from './dashboard/dashboard';
import { Goals } from './goals/goals';

export const routes: Routes = [
    {path: '', component: Login},
    {path: 'home', component: DashboardComponent},
    {path: 'goals', component: Goals}
];
