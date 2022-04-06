import { Component, OnInit } from '@angular/core';
import { PendingTaskService } from 'src/app/pending-task.service';

@Component({
  selector: 'app-pendingtasks',
  templateUrl: './pendingtasks.component.html',
  styleUrls: ['./pendingtasks.component.css']
})
export class PendingtasksComponent implements OnInit {
  userEmail:any;
  officialTask:any;
  personalTask:any;
  constructor(private service:  PendingTaskService ) { }

  ngOnInit(): void {
    this.userEmail=localStorage.getItem("user.Email");
    console.log(this.userEmail);
    this.service.getPendingOfficialTask(this.userEmail).subscribe(data => this.officialTask = data  );
    
    this.service.getPendingPersonalTask(this.userEmail).subscribe(data => {this.personalTask = data});
  }

}
