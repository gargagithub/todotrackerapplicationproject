import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { ArchiveService } from '../archive.service';
import { LoginService } from '../login.service';
import { TaskformComponent } from '../taskform/taskform.component';

@Component({
  selector: 'app-archive',
  templateUrl: './archive.component.html',
  styleUrls: ['./archive.component.css']
})
export class ArchiveComponent implements OnInit {
userEmail:any;
officialTask:any;
personalTask:any;
  constructor(private service: ArchiveService,private loginservice: LoginService,private dialog:MatDialog) { }

  ngOnInit(): void {
    this.userEmail=localStorage.getItem("user.Email");
    console.log(this.userEmail);
    this.service.getOfficialTaskFromTodo(this.userEmail).subscribe(data => this.officialTask = data  );
    this.service.getPersonalTaskFromTodo(this.userEmail).subscribe(data => {this.personalTask = data});
  }
archiveId:any;
taskId:any;
  // deleteCompeltedTask(archiveId: any,taskId: any){
  //   this.service.deletePersonalTaskFromTodo(archiveId,taskId).subscribe(data=>console.log(data));
  // }

  onCreate(){
    
    const dialogConfig=new MatDialogConfig();
    dialogConfig.disableClose=true;
    dialogConfig.autoFocus=true;
    dialogConfig.width="45%";
    this.dialog.open(TaskformComponent); 
  }
}
