import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MatDialogConfig, MatDialogRef } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { NgToastService } from 'ng-angular-popup';
import { LoginComponent } from '../components/login/login.component';
import { User } from '../components/user';
import { LoginService } from '../login.service';
import { TodoserviceService } from '../todoservice.service';
@Component({
  selector: 'app-update-user',
  templateUrl: './update-user.component.html',
  styleUrls: ['./update-user.component.css']
})
export class UpdateUserComponent implements OnInit {
user!:User;
  userEmail:any;
  update=new FormGroup({
    FirstName:new FormControl('',[Validators.required]),
    LastName:new FormControl('',[Validators.required]),
    Password:new FormControl('',[Validators.required]),
    mobileNo:new FormControl('',[Validators.required,Validators.pattern("^[0-9]*$"),Validators.minLength(10), Validators.maxLength(10)])
  })
 
   
   hide=false;
 
   constructor(private service:TodoserviceService, private router: Router, private toast : NgToastService, private dialog:MatDialog,
    private dialogRef:MatDialogRef<UpdateUserComponent>,private loginservice:LoginService) { }
 
   ngOnInit(): void {
    this.userEmail=localStorage.getItem("user.Email");
    console.log(this.userEmail);
     this.loginservice.getUserByEmail(this.userEmail).subscribe(data => {this.user = data ;
      this.update.controls["FirstName"].setValue(this.user.firstName);
     this.update.controls["LastName"].setValue(this.user.lastName);
      this.update.controls["Password"].setValue(this.user.password);
      this.update.controls["mobileNo"].setValue(this.user.mobileNo);} );
     
    //  this.update.controls["FirstName"].setValue(this.user.firstName);
    //  this.update.controls["LastName"].setValue(this.user.lastName);
    //  this.update.controls["Password"].setValue(this.user.password);
    //  this.update.controls["mobileNo"].setValue(this.user.mobileNo);
  
   }
 
 get FirstName(){
   return this.update.get('FirstName');
 }
 
 get LastName(){
   return this.update.get('LastName');
 }
 
 get Password(){
   return this.update.get('Password');
 }
 
 get mobileNo(){
   return this.update.get('mobileNo');
 }
  updateNow(){
    let user:User=new User();
    user.firstName=this.update.value.FirstName;
    console.log(user.firstName)
    user.lastName=this.update.value.LastName;
    console.log(user.lastName)
    user.password=this.update.value.Password;
    console.log(user.password)
    user.mobileNo=this.update.value.mobileNo;
    console.log(user.mobileNo)
   this.service.updateUserdetails(user,this.userEmail).subscribe(result=>{this.toast.success({detail:"successMessage", summary:"Details are Updated",duration:5500})

   this.onClose();
   window.location.reload();
   this.router.navigate(['/todo'])
 },err => {
   this.toast.error({ detail: "Error Message", summary: "Faced issue in Updating task", duration: 5000 })
 });
   
   // window.alert("Register successfully");
   // window.location.href="./login"
   this.update.reset();
  }

  onFill(user: any) {
    this.loginservice.getUserByEmail(user);
    console.log(user);
  }

  onClose(){
    this.update.reset();
    this.dialogRef.close();
    // window.location.reload();
    
  }
}
