import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { NgToastService } from 'ng-angular-popup';
import { LoginService } from '../login.service';
import { Task } from '../task';
import { TaskformComponent } from '../taskform/taskform.component';
import { TodoserviceService } from '../todoservice.service';

@Component({
  selector: 'app-pu-task-form',
  templateUrl: './pu-task-form.component.html',
  styleUrls: ['./pu-task-form.component.css']
})
export class PuTaskFormComponent implements OnInit {

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
  forFillingForm:any;
  personalbuttonDisabled: boolean = true;
  taskform = new FormGroup({
    taskId: new FormControl(''),
    title: new FormControl('', [Validators.required]),
    deadline: new FormControl('', [Validators.required]),
    priority: new FormControl('', [Validators.required]),
    description: new FormControl('', [Validators.required]),
  });

  constructor(private service: TodoserviceService, private loginservice: LoginService, private toast: NgToastService,
     private router: Router,private dialogRef:MatDialogRef<PuTaskFormComponent>) { }

  ngOnInit(): void {
    this.pastDateTime();
    this.userEmail=localStorage.getItem("user.Email");
    console.log(this.userEmail);
    this.forFillingForm=this.service.sendPopulatedData();
    console.log(this.forFillingForm);
    
    this.taskform.controls["title"].setValue(this.forFillingForm.taskTitle);
      this.taskform.controls["deadline"].setValue(this.forFillingForm.taskDeadline);
      this.taskform.controls["priority"].setValue(this.forFillingForm.taskPriority);
      this.taskform.controls["description"].setValue(this.forFillingForm.taskDescription);
  }

  get title() {
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
      this.taskform.value.deadline= ""
    }
  }

  isUpdatedPersonalTask() {
    
    let task = new Task();
    task.taskId = this.forFillingForm.taskId;
    task.taskTitle = this.taskform.value.title;
    // task.taskStatus=this.taskform.value.taskStatus;  
    task.taskPriority = this.taskform.value.priority;
    task.taskDescription = this.taskform.value.description;
    task.taskDeadline = this.taskform.value.deadline;
    console.log(task.taskId)
    this.service.updatePersonalTask(task, this.userEmail, task.taskId).subscribe(data =>{
      this.toast.success({ detail: " Success Message", summary: "PersonalTask is updated successfully", duration: 5000 })
      
      this.onClose();
    },
    err => {
      this.toast.error({ detail: "Error Message", summary: "PersonalTask is not updated", duration: 5000 })
    });
    console.log("taskform values ", this.taskform.value);
    this.taskform.reset();

  }
  onClose(){
    this.taskform.reset();
    this.dialogRef.close();
    window.location.reload();
    
  }
}
