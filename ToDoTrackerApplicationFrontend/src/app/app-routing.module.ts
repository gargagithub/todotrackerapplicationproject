import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ArchiveComponent } from './archive/archive.component';
import { AuthGuard } from './auth.guard';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { PendingtasksComponent } from './components/pendingtasks/pendingtasks.component';

import { RegistrationComponent } from './components/registration/registration.component';
import { TodoComponent } from './components/todo/todo.component';
import { FooterComponent } from './footer/footer.component';
import { HeaderComponent } from './header/header.component';
import { SidenavComponent } from './sidenav/sidenav.component';
import { SortingComponent } from './sorting/sorting.component';
import { TaskformComponent } from './taskform/taskform.component';
import { UpdateUserComponent } from './update-user/update-user.component';


const routes: Routes = [
  {path:'', component:HomeComponent},
  {path: 'login', component: LoginComponent},
  {path: 'registration', component: RegistrationComponent},
  {path: 'home', component: HomeComponent},

  {path:"sorting",component:SortingComponent},
  {path:"todo",component:TodoComponent , canActivate:[AuthGuard]},
  {path:"archive",component:ArchiveComponent , canActivate:[AuthGuard]},
  {path: "navbar",component:NavbarComponent},

  {path:"footer",component:FooterComponent},
  {path:"sidenav",component:SidenavComponent},
  {path:"header",component:HeaderComponent},

  {path: "taskform",component:TaskformComponent},

  {path: "update",component:UpdateUserComponent},
  {path: "pendingtasks",component:PendingtasksComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
