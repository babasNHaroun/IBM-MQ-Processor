import { Routes } from '@angular/router';

export const routes: Routes = [
  { path: '', redirectTo: '/messages', pathMatch: 'full' },
  { 
    path: 'messages', 
    loadComponent: () => import('./components/message-list/message-list.component').then(m => m.MessageListComponent) 
  },
  { 
    path: 'partners', 
    loadComponent: () => import('./components/partner-list/partner-list.component').then(m => m.PartnerListComponent) 
  },
  { path: '**', redirectTo: '/messages' }
];