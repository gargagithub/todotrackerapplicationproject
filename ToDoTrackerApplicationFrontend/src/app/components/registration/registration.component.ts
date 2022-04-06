import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import { RegistrationService } from '../registration.service';
//import { FormsModule } from '@angular/forms';
// import {MatFormFieldModule} from '@angular/material/form-field';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { User } from '../user';
import { Message } from '@angular/compiler/src/i18n/i18n_ast';
import { Router } from '@angular/router';
import { NgToastService } from 'ng-angular-popup';
@Component({
  selector: 'app-registration', 
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  title = "RegistrationComponent";

  email = new FormControl('', [Validators.required, Validators.email]);
  
 register=new FormGroup({
  //  userEmail:new FormControl('',[Validators.required]),
   FirstName:new FormControl('',[Validators.required]),
   LastName:new FormControl('',[Validators.required]),
   Password:new FormControl('',[Validators.required]),
   mobileNo:new FormControl('',[Validators.required,Validators.pattern("^[0-9]*$"),Validators.minLength(10), Validators.maxLength(10)])
 })


  getErrorMessage() {
    if (this.email.hasError('required') && this.email.hasError('dirty')) {
      return 'You must enter a value';
    }

    return this.email.hasError('email') ? 'Not a valid email' : '';
  }
  
  hide=false;

  constructor(private service:RegistrationService, private router: Router, private toast : NgToastService) { }

  ngOnInit(): void {
  }

  get userEmail(){
    return this.register.get('userEmail');

  }

get FirstName(){
  return this.register.get('FirstName');
}

get LastName(){
  return this.register.get('LastName');
}

get Password(){
  return this.register.get('Password');
}

get mobileNo(){
  return this.register.get('mobileNo');
}
 registerNow(){
   let user:User=new User();
   user.userEmail=this.email.value;
   console.log(user.userEmail)
   user.firstName=this.register.value.FirstName;
   console.log(user.firstName)
   user.lastName=this.register.value.LastName;
   console.log(user.lastName)
   user.password=this.register.value.Password;
   console.log(user.password)
   user.mobileNo=this.register.value.mobileNo;
   console.log(user.mobileNo)
  this.service.register(user).subscribe(result=>{this.toast.success({detail:"successMessage", summary:"Register is successfully",duration:5500})
  this.router.navigate(['/login'])
},err => {
  this.toast.error({ detail: "Error Message", summary: "Faced issue in logging in", duration: 5000 })
});
  
  // window.alert("Register successfully");
  // window.location.href="./login"
  
  
 
  this.register.reset();
  this.email.reset();
  

  // this.register.reset();
  // this.email.reset();



 }

}