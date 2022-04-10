import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { RegistrationComponent } from './components/registration/registration.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatButtonModule} from '@angular/material/button';
import {MatSnackBar, MatSnackBarModule} from '@angular/material/snack-bar';

import {MatToolbar, MatToolbarModule, MatToolbarRow} from '@angular/material/toolbar';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
//import { FormsModule } from '@angular/forms';
import {MatIconModule} from '@angular/material/icon';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import {MatCardModule} from '@angular/material/card';
import {MatSelectModule} from '@angular/material/select';
import {MatProgressBarModule} from '@angular/material/progress-bar';
import {MatDividerModule} from '@angular/material/divider';
// import {FlexLayoutModule} from '@angular/flex-layout';
import { SortingComponent } from './sorting/sorting.component'
import { NgToastModule } from 'ng-angular-popup';
// import { TodoComponent } from './todo/todo.component';
import {MatMenuModule} from '@angular/material/menu';
import {FlexLayoutModule} from '@angular/flex-layout';
import { ArchiveComponent } from './archive/archive.component';
import { TodoComponent } from './components/todo/todo.component';
import { TaskformComponent } from './taskform/taskform.component';
// import { TodoComponent } from './todo/todo.component';
// import { FlexLayoutModule } from '@angular/flex-layout';
import {MatSidenavModule} from '@angular/material/sidenav';
import { FooterComponent } from './footer/footer.component';
import { SidenavComponent } from './sidenav/sidenav.component';
import { HeaderComponent } from './header/header.component';
import {MatDialogModule} from '@angular/material/dialog';
import { MatBadgeModule } from '@angular/material/badge';
import { PendingtasksComponent } from './components/pendingtasks/pendingtasks.component';
import { UpdateUserComponent } from './update-user/update-user.component';
import { PuTaskFormComponent } from './pu-task-form/pu-task-form.component';
import { OuTaskFormComponent } from './ou-task-form/ou-task-form.component';
// import { PendingtasksComponent } from './pendingtasks/pendingtasks.component';
import {MatListModule} from '@angular/material/list';
import { NavbarComponent } from './components/navbar/navbar.component';
import { Router } from '@angular/router';
import { AboutUsComponent } from './about-us/about-us.component';
import { ContactUsComponent } from './contact-us/contact-us.component';
import {MatPaginatorModule} from '@angular/material/paginator';
@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent,
    RegistrationComponent,
    NavbarComponent,
    TodoComponent,
    SortingComponent,
    ArchiveComponent,

    FooterComponent,
    SidenavComponent,
    HeaderComponent,

    TaskformComponent,
    
    PendingtasksComponent,
          UpdateUserComponent,
          PuTaskFormComponent,
          OuTaskFormComponent,
          
          AboutUsComponent,
                      ContactUsComponent
    // PendingtasksComponent
    
    

    
   

  ],
exports: [ MatInputModule,MatCardModule,MatProgressBarModule,MatDialogModule
],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatSnackBarModule,
    MatToolbarModule,
    MatFormFieldModule,
    MatInputModule,
    FormsModule,
    MatIconModule,
    ReactiveFormsModule,
    HttpClientModule,
    MatCardModule,
    MatSelectModule,
    MatProgressBarModule,
    MatDividerModule,
    NgToastModule,
    MatMenuModule,
    MatDialogModule,
    MatBadgeModule,

    MatPaginatorModule,
    FlexLayoutModule,
    NgToastModule,
    MatSidenavModule,
    MatListModule,
    // MatSnackBar,
    // Router
    
    
    
  ],
  providers: [],
  bootstrap: [AppComponent],
  entryComponents:[TaskformComponent]
})
export class AppModule {

 }