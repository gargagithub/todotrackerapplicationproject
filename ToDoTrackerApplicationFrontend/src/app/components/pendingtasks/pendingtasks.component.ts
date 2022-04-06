import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { NgToastService } from 'ng-angular-popup';
import { OuTaskFormComponent } from 'src/app/ou-task-form/ou-task-form.component';
import { PendingTaskService } from 'src/app/pending-task.service';
import { PuTaskFormComponent } from 'src/app/pu-task-form/pu-task-form.component';
import { TaskformComponent } from 'src/app/taskform/taskform.component';
import { TodoserviceService } from 'src/app/todoservice.service';

@Component({
  selector: 'app-pendingtasks',
  templateUrl: './pendingtasks.component.html',
  styleUrls: ['./pendingtasks.component.css']
})
export class PendingtasksComponent implements OnInit {
  userEmail:any;
  officialTask:any;
  personalTask:any;
  constructor(private service:  PendingTaskService,private dialog:MatDialog ,private todoservice:TodoserviceService,private toast: NgToastService,) { }

  ngOnInit(): void {
    this.userEmail=localStorage.getItem("user.Email");
    console.log(this.userEmail);
    this.todoservice.refreshNeeded.subscribe(() =>{this.getAllOf();});
    this.getAllOf();
    
  }
  getAllOf(){
  this.service.getPendingOfficialTask(this.userEmail).subscribe(data => this.officialTask = data  );
    
  this.service.getPendingPersonalTask(this.userEmail).subscribe(data => {this.personalTask = data});
  }


  isDeletedOfficialTask(userEmail: any, id: any) {

    console.log(id);
    this.todoservice.deleteOfficialTask(this.userEmail, id).subscribe(data => {
      this.toast.success({ detail: " Success Message", summary: "Pending officialTask is deleted successfully", duration: 5000 })
      this.ngOnInit();
    },
      err => {
        this.toast.error({ detail: "Error Message", summary: "Pending officialTask is not deleted", duration: 5000 })
      });

  }

  isDeletedPersonalTask(userEmail: any, id: any) {
    console.log(this.userEmail);
    console.log(id);
    this.todoservice.deletePersonalTask(this.userEmail, id).subscribe(data => {
      this.toast.success({ detail: " Success Message", summary: "Pending personalTask is deleted successfully", duration: 5000 })
      this.ngOnInit();
    },
      err => {
        this.toast.error({ detail: "Error Message", summary: "Pending personalTask is not deleted", duration: 5000 })
      });

  }

  // isUpdatedOfficialTask() {
  //   console.log(this.user)
  //   let task = new Task();
  //   task.taskId = this.officialTaskById.taskId;
  //   task.taskTitle = this.taskform.value.title;
  //   // task.taskStatus=this.taskform.value.taskStatus;  
  //   task.taskPriority = this.taskform.value.priority;
  //   task.taskDescription = this.taskform.value.description;
  //   task.taskDeadline = this.taskform.value.deadline;
  //   console.log(task.taskId)

  //   this.service.updateOfficialTask(task, this.userEmail, task.taskId).subscribe(data =>{

  //     this.toast.success({ detail: " Success Message", summary: "OfficialTask is updated successfully", duration: 5000 })
  //     this.ngOnInit();
  //   },
  //   err => {
  //     this.toast.error({ detail: "Error Message", summary: "OfficialTask is not updated", duration: 5000 })
  //   });
  //   console.log("taskform values ", this.taskform.value);
  //   this.taskform.reset();

  // }

  // isUpdatedPersonalTask() {
  //   console.log(this.user)
  //   let task = new Task();
  //   task.taskId = this.personalTaskById.taskId;
  //   task.taskTitle = this.taskform.value.title;
  //   // task.taskStatus=this.taskform.value.taskStatus;  
  //   task.taskPriority = this.taskform.value.priority;
  //   task.taskDescription = this.taskform.value.description;
  //   task.taskDeadline = this.taskform.value.deadline;
  //   console.log(task.taskId)
  //   this.service.updatePersonalTask(task, this.userEmail, task.taskId).subscribe(data =>{
  //     this.toast.success({ detail: " Success Message", summary: "PersonalTask is updated successfully", duration: 5000 })
  //     this.ngOnInit();
  //   },
  //   err => {
  //     this.toast.error({ detail: "Error Message", summary: "PersonalTask is not updated", duration: 5000 })
  //   });
  //   console.log("taskform values ", this.taskform.value);
  //   this.taskform.reset();

  // }

  // loadOfficialTaskToUpdate(userEmail: any, taskId: any) {
  //   this.service.getOfficialTaskById(this.userEmail, taskId).subscribe(data => {
  //     this.officialTaskById = data;
  //     this.message = null;
  //     this.taskform.controls["title"].setValue(this.officialTaskById.taskTitle);
  //     this.taskform.controls["deadline"].setValue(this.officialTaskById.taskDeadline);
  //     this.taskform.controls["priority"].setValue(this.officialTaskById.taskPriority);
  //     this.taskform.controls["description"].setValue(this.officialTaskById.taskDescription);
  //   });

  // }

  // loadPersonalTaskToUpdate(userEmail: any, taskId: any) {
  //   this.service.getPersonalTaskById(this.userEmail, taskId).subscribe(data => {
  //     this.personalTaskById = data;
  //     this.message = null;
  //     this.taskform.controls["title"].setValue(this.personalTaskById.taskTitle);
  //     this.taskform.controls["deadline"].setValue(this.personalTaskById.taskDeadline);
  //     this.taskform.controls["priority"].setValue(this.personalTaskById.taskPriority);
  //     this.taskform.controls["description"].setValue(this.personalTaskById.taskDescription);

  //   });
  // }

  completeOfficialTask(taskId: any) {
    this.todoservice.completedOfficialTask(this.userEmail, taskId).subscribe(data => {
      this.toast.success({ detail: "Task is Completed", summary: "Moved to Archive", duration: 5000 })
      this.ngOnInit();

    },
      err => {
        this.toast.error({ detail: "Error Message", summary: "Faced issue to Complete Task", duration: 5000 })
      });
  }

  completePersonalTask(taskId: any) {
    this.todoservice.completedPersonalTask(this.userEmail, taskId).subscribe(data => {
      this.toast.success({ detail: "Task is Completed", summary: "Moved to Archive", duration: 5000 })
      this.ngOnInit();

    },
      err => {
        this.toast.error({ detail: "Error Message", summary: "Faced issue to Complete Task", duration: 5000 })
      });
  }

  onCreate() {

    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.width = "45%";
    this.dialog.open(TaskformComponent);
  }
  onEditPersonal(task1: any) {
    this.todoservice.populatePersonalForm(task1);
    console.log(task1);
  }
  onEditOfficial(task: any) {
    this.todoservice.populateOfficialForm(task);
    console.log(task);
  }
  onCreatePersonal() {

    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.width = "45%";
    this.dialog.open(PuTaskFormComponent);
  }
  onCreateOfficial() {

    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.width = "45%";
    this.dialog.open(OuTaskFormComponent);
  }
}
