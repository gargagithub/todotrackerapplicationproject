import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { NgToastService } from 'ng-angular-popup';
import { User } from '../components/user';
import { LoginService } from '../login.service';
import { OuTaskFormComponent } from '../ou-task-form/ou-task-form.component';
import { PuTaskFormComponent } from '../pu-task-form/pu-task-form.component';
import { Task } from '../task';
import { TaskformComponent } from '../taskform/taskform.component';
import { TodoserviceService } from '../todoservice.service';

@Component({
  selector: 'app-sorting',
  templateUrl: './sorting.component.html',
  styleUrls: ['./sorting.component.css']
})
export class SortingComponent implements OnInit {
  min: any = "2022-03-26T16:07";
  values: any;
  priorityList: any = ['HIGH', 'MEDIUM', 'LOW'];
  officialTask: any;
  personalTask: any;
  dataSaved = false;
  message: any = null;
  taskIdToUpdate: any = null;
  officialTaskById: any;
  personalTaskById: any;
  addbuttonhidden: boolean = false;
  officialbuttonDisabled: boolean = true;
  userEmail:any;
  personalbuttonDisabled: boolean = true;
  taskform = new FormGroup({
    taskId: new FormControl(''),
    title: new FormControl('', [Validators.required]),
    deadline: new FormControl('', [Validators.required]),
    priority: new FormControl('', [Validators.required]),
    description: new FormControl('', [Validators.required]),
  });

  constructor(private service: TodoserviceService, private loginservice: LoginService,
    private toast: NgToastService, private router: Router, private dialog:MatDialog) { }

  ngOnInit(): void {
    this.pastDateTime();
    this.userEmail=localStorage.getItem("user.Email");
    console.log(this.userEmail);
    this.service.sortByPriorityOfOfficialTask(this.userEmail).subscribe(data=>this.officialTask=data);
    this.service.sortByPriorityOfPersonalTask(this.userEmail).subscribe(data=>this.personalTask=data);
  }  get title() {
    return this.taskform.get('title');
  };
  get deadline() {
    return this.taskform.get('deadline');
  };
  get priority() {
    return this.taskform.get('priority');
  }
  get description() {
    return this.taskform.get('description');
  }

  resetButton() {
    this.taskform.reset();
    this.addbuttonhidden=false;
    this.officialbuttonDisabled=true;
    this.personalbuttonDisabled=true;
  }
  pastDateTime() {
    console.log("hello")
    var tdate: any = new Date();
    console.log(tdate)
    var date: any = tdate.getDate();
    if (date < 10) { // adding 0 before date if it is single digit (09)
      date = "0" + date;
    }
    var month: any = tdate.getMonth() + 1;
    if (month < 10) { // adding 0 before date if it is single digit (09)
      month = "0" + month; //adding +1 because it returns previous month
    }
    var year: any = tdate.getFullYear();
    var hours: any = tdate.getHours();
    var minutes: any = tdate.getMinutes();
    this.min = year + "-" + month + "-" + date + "T" + hours + ":" + minutes;
    console.log(this.min);
  }


  onChange(value: any) {
    var presentDate: any = new Date().getTime();
    var selectedDate: any = new Date(value).getTime();

    if (selectedDate < presentDate) {
      this.values = ""
    }
  }

  user: User = new User();

  saveToOfficialTask() {
    console.log(this.user)
    let task = new Task();
    task.taskTitle = this.taskform.value.title;
    // task.taskStatus=this.taskform.value.taskStatus;  
    task.taskPriority = this.taskform.value.priority;
    task.taskDescription = this.taskform.value.description;
    task.taskDeadline = this.taskform.value.deadline;

    this.service.saveOfficialTask(task, this.userEmail).subscribe(data => { 
      this.toast.success({ detail: " Success Message", summary: "OfficiaTtask is added successfully", duration: 5000 })
      this.ngOnInit();
    },
      err => {
        this.toast.error({ detail: "Error Message", summary: "OfficialTask is not added", duration: 5000 })
      });
    this.taskform.reset();
  }

  saveToPersonalTask() {
    console.log(this.user)
    let task = new Task();
    task.taskTitle = this.taskform.value.title;
    // task.taskStatus=this.taskform.value.taskStatus;  
    task.taskPriority = this.taskform.value.priority;
    task.taskDescription = this.taskform.value.description;
    task.taskDeadline = this.taskform.value.deadline;

    this.service.savePersonalTask(task, this.userEmail).subscribe(data =>{
      this.ngOnInit();
      this.toast.success({ detail: " Success Message", summary: "PersonalTask is added successfully", duration: 5000 })
      
    },
    err => {
      this.toast.error({ detail: "Error Message", summary: "PersonalTask is not added", duration: 5000 })
    });
    this.taskform.reset();

  }

  isDeletedOfficialTask(userEmail: any, id: any) {
    console.log(this.user.userEmail);
    console.log(id);
    this.service.deleteOfficialTask(this.userEmail, id).subscribe(data =>{
      this.toast.success({ detail: " Success Message", summary: "OfficialTask is deleted successfully", duration: 5000 })
      this.ngOnInit();
    },
    err => {
      this.toast.error({ detail: "Error Message", summary: "OfficialTask is not deleted", duration: 5000 })
    });

  }

  isDeletedPersonalTask(userEmail: any, id: any) {
    console.log(this.user.userEmail);
    console.log(id);
    this.service.deletePersonalTask(this.userEmail, id).subscribe(data =>{
      this.toast.success({ detail: " Success Message", summary: "PersonalTask is deleted successfully", duration: 5000 })
      this.ngOnInit();
    },
    err => {
      this.toast.error({ detail: "Error Message", summary: "PersonalTask is not deleted", duration: 5000 })
    });

  }

  isUpdatedOfficialTask() {
    console.log(this.user)
    let task = new Task();
    task.taskId = this.officialTaskById.taskId;
    task.taskTitle = this.taskform.value.title;
    // task.taskStatus=this.taskform.value.taskStatus;  
    task.taskPriority = this.taskform.value.priority;
    task.taskDescription = this.taskform.value.description;
    task.taskDeadline = this.taskform.value.deadline;
    console.log(task.taskId)

    this.service.updateOfficialTask(task, this.userEmail, task.taskId).subscribe(data =>{
      
      this.toast.success({ detail: " Success Message", summary: "OfficialTask is updated successfully", duration: 5000 })
      this.ngOnInit();
    },
    err => {
      this.toast.error({ detail: "Error Message", summary: "OfficialTask is not updated", duration: 5000 })
    });
    console.log("taskform values ", this.taskform.value);
    this.taskform.reset();

  }

  isUpdatedPersonalTask() {
    console.log(this.user)
    let task = new Task();
    task.taskId = this.personalTaskById.taskId;
    task.taskTitle = this.taskform.value.title;
    // task.taskStatus=this.taskform.value.taskStatus;  
    task.taskPriority = this.taskform.value.priority;
    task.taskDescription = this.taskform.value.description;
    task.taskDeadline = this.taskform.value.deadline;
    console.log(task.taskId)
    this.service.updatePersonalTask(task, this.userEmail, task.taskId).subscribe(data =>{
      this.toast.success({ detail: " Success Message", summary: "PersonalTask is updated successfully", duration: 5000 })
      this.ngOnInit();
    },
    err => {
      this.toast.error({ detail: "Error Message", summary: "PersonalTask is not updated", duration: 5000 })
    });
    console.log("taskform values ", this.taskform.value);
    this.taskform.reset();

  }

  loadOfficialTaskToUpdate(userEmail: any, taskId: any) {
    this.service.getOfficialTaskById(this.userEmail, taskId).subscribe(data => {
      this.officialTaskById = data;
      this.message = null;
      this.taskform.controls["title"].setValue(this.officialTaskById.taskTitle);
      this.taskform.controls["deadline"].setValue(this.officialTaskById.taskDeadline);
      this.taskform.controls["priority"].setValue(this.officialTaskById.taskPriority);
      this.taskform.controls["description"].setValue(this.officialTaskById.taskDescription);
    });
  }

  loadPersonalTaskToUpdate(userEmail: any, taskId: any) {
    this.service.getPersonalTaskById(this.userEmail, taskId).subscribe(data => {
      this.personalTaskById = data;
      this.message = null;
      this.taskform.controls["title"].setValue(this.personalTaskById.taskTitle);
      this.taskform.controls["deadline"].setValue(this.personalTaskById.taskDeadline);
      this.taskform.controls["priority"].setValue(this.personalTaskById.taskPriority);
      this.taskform.controls["description"].setValue(this.personalTaskById.taskDescription);
    });
  }

  completeOfficialTask(taskId:any){
    this.service.completedOfficialTask(this.userEmail,taskId).subscribe(data =>{
      this.toast.success({ detail: "Task is Completed", summary: "Moved to Archive", duration: 5000 })
      this.ngOnInit();
    },
    err => {
      this.toast.error({ detail: "Error Message", summary: "Faced issue to Complete Task", duration: 5000 })
    });
  }

  completePersonalTask(taskId:any){
    this.service.completedPersonalTask(this.userEmail,taskId).subscribe(data =>{
      this.toast.success({ detail: "Task is Completed", summary: "Moved to Archive", duration: 5000 })
      this.ngOnInit();
    },
    err => {
      this.toast.error({ detail: "Error Message", summary: "Faced issue to Complete Task", duration: 5000 })
    });
  }

  onCreate(){
    
    const dialogConfig=new MatDialogConfig();
    dialogConfig.disableClose=true;
    dialogConfig.autoFocus=true;
    dialogConfig.width="45%";
    this.dialog.open(TaskformComponent);
  }

  
  onEditPersonal(task1:any){
    this.service.populatePersonalForm(task1);
    console.log(task1);
  }
  onEditOfficial(task:any){
    this.service.populateOfficialForm(task);
    console.log(task);
  }
  onCreatePersonal(){
    
    const dialogConfig=new MatDialogConfig();
    dialogConfig.disableClose=true;
    dialogConfig.autoFocus=true;
    dialogConfig.width="45%";
    this.dialog.open(PuTaskFormComponent); 
  }
  onCreateOfficial(){
    
    const dialogConfig=new MatDialogConfig();
    dialogConfig.disableClose=true;
    dialogConfig.autoFocus=true;
    dialogConfig.width="45%";
    this.dialog.open(OuTaskFormComponent); 
  }
}
