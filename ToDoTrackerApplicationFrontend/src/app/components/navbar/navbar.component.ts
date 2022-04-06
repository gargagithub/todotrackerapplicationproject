import { Component, OnInit } from '@angular/core';
import { LoginService } from 'src/app/login.service';
import { Router } from '@angular/router';
import { Task } from 'src/app/task';
import { TodoserviceService } from 'src/app/todoservice.service';
import { Observable, timer } from 'rxjs';



@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {



  userEmail: any;
  service: any;
  officialTask: any;
  personalTask: any;
  notiificationList!:Task[];
  notifyLength!:number;
  private timer: Observable<0> | undefined;
  constructor(private loginservice:LoginService,private router: Router,private todoservice:TodoserviceService) { }

  ngOnInit(): void {
    this.userEmail=localStorage.getItem("user.Email");
    this.timer = timer(1000);
    this.timer.subscribe((t) => this.onTimeOut());
    
    console.log(this.notiificationList.length+"ggggg");
  }
   

onTimeOut() {
  this.userEmail=localStorage.getItem("user.Email");
  this.todoservice.getNotifications(this.userEmail).subscribe(data => {this.notiificationList = data;
    this.notifyLength=this.notiificationList.length;});
 
}

    logoutUser()
    {
    this.loginservice.logout()
    // location.reload()
    this.router.navigate(['./login'])
    }
    

    moveToArchive(){
      this.router.navigate(['/archive'])
    }
    moveToTodo(){
      this.router.navigate(['/todo'])
    }
    moveToPendingTasks(){
      this.router.navigate(['/pendingtask'])
    }

     resetStorage(){
      localStorage.removeItem('user.Email')
    }

    loggedIn(){
      return localStorage.getItem('token')
    }
  }


