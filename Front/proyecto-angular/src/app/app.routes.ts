import { Routes } from '@angular/router';
import { AppLayout } from './layout/component/app.layout';
import { Landing } from './pages/landing/landing';
import { Notfound } from './pages/notfound/notfound';


export const routes: Routes = [
   
    {
        path: '',
        component: Landing
    },
    { path: 'notfound', component: Notfound },
    { path: 'auth', loadChildren: () => import('./pages/auth/auth.routes') },
    { path: '**', redirectTo: '/notfound' }
];
