import { Component, OnInit, HostBinding } from '@angular/core';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Observable, timer } from 'rxjs';
import { map, shareReplay } from 'rxjs/operators';
import { Router } from '@angular/router';
import { MatSidenav } from '@angular/material/sidenav';
import { MatSnackBar } from '@angular/material/snack-bar';
import { LoginService } from '../login.service';
import { TodoserviceService } from '../todoservice.service';
import { Task } from 'src/app/task';
import { UpdateUserComponent } from '../update-user/update-user.component';
import { MatDialog } from '@angular/material/dialog';
@Component({
  selector: 'app-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrls: ['./sidenav.component.css']
})
export class SidenavComponent implements OnInit {
  public loading!: boolean;
  public isAuthenticated!: boolean;
  public title!: string;
  public isBypass!: boolean;
  public mobile!: boolean;
  public isMenuInitOpen!: boolean;
  userEmail: any;
  service: any;
  officialTask: any;
  personalTask: any;
  notiificationList!:Task[];
  notifyLength!:number;
  imageSrc='assets/nTask-logo-32.png';
  imageAlt='webSite'
  private timer!: Observable<0>;
  constructor(private breakpointObserver: BreakpointObserver,
              private router: Router,
              private _snackBar: MatSnackBar  , private loginservice:LoginService , private todoservice:TodoserviceService,private dialog:MatDialog) { }
    private sidenav!: MatSidenav;
    public isMenuOpen = true;
    public contentMargin = 240;
    isHandset$: Observable<boolean> = this.breakpointObserver.observe(Breakpoints.Handset)
    .pipe(
      map(result => result.matches)
    );
  ngOnInit() {
    this.isMenuOpen = true;  // Open side menu by default
    this.title = 'Material Layout Demo';
    this.userEmail=localStorage.getItem("user.Email");
    // this.todoservice.refreshNeeded.subscribe(()=>(this.onTimeOut()));
    this.timer = timer(10000);
    this.todoservice.refreshNeeded.subscribe(() =>{this.timer.subscribe((t) => this.onTimeOut());});
    this.timer.subscribe((t) => this.onTimeOut());
  }
  
  onTimeOut() {
    this.userEmail=localStorage.getItem("user.Email");
    this.todoservice.getNotifications(this.userEmail).subscribe(data => {this.notiificationList = data;
      this.notifyLength=this.notiificationList.length;});
  }
  ngDoCheck() {
      if (this.isHandset$) {
         this.isMenuOpen = false;
      } else {
         this.isMenuOpen = true;
      }
  }
  public openSnackBar(msg: string): void {
    this._snackBar.open(msg, 'X', {
      duration: 2000,
      horizontalPosition: 'center',
      verticalPosition: 'top',
      panelClass: 'notif-error'
    });
  }
  public onSelectOption(option: any): void {
    const msg = `Chose option ${option}`;
    this.openSnackBar(msg);
    /* To route to another page from here */
    // this.router.navigate(['/home']);
  }
  logoutUser()
    {
    this.loginservice.logout()
    // location.reload()
    this.router.navigate(['./login'])
    }
    moveToTodo(){
      this.router.navigate(['/todo'])
    }
    moveToArchive(){
      this.router.navigate(['/archive'])
    }
    resetStorage(){
      localStorage.removeItem('user.Email')
    }
    loggedIn(){
      return localStorage.getItem('token')
    }
    moveToPendingTasks(){
      this.router.navigate(['/pendingtask'])
    }
    onCreateUser() {
      this.dialog.open(UpdateUserComponent);
    }


    
}