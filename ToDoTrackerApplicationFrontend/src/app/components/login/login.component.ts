import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/login.service';
import { User } from '../user';
import { NgToastService } from 'ng-angular-popup';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  hide = true;
 credentials={
   userEmail:'',
   password:''
 }


  email = new FormControl('', [Validators.required, Validators.email]);
  password= new FormControl('',[Validators.required])
  getErrorMessage() {
    if (this.email.hasError('required')) {
      return 'You must enter a value';
    }

    return this.email.hasError('email') ? 'Not a valid email' : '';
  }
  getPasswordErrorMessage() {
    if (this.password.hasError('required')) {
      return 'You must enter a value';
    }

    return this.password.hasError('email') ? 'Not a valid password' : '';
  }
 
  constructor(private  LoginService:LoginService, private toast : NgToastService, private router:Router  ) { }

  ngOnInit(): void {

  }

onSubmit(){
  
  if((this.credentials.userEmail!=''&& this.credentials.password!='')&&(this.credentials.userEmail!=null && this.credentials.password!=null))
  {
  console.log("we are cheching your details");
  

 this.LoginService.doLogin(this.credentials).subscribe(
response=>{
  console.log(response);
  this.toast.success({detail:"successMessage", summary:"login is successfully",duration:15500})
  
  this.LoginService.loginUser(User);

  window.location.href="./todo"
  console.log(this.credentials.userEmail)
  console.log(User);
  //this.router.navigate(['/todo']);
  console.log(this.email=this.LoginService.getEmail());

},
error=>{
  this.toast.error({detail:"Error", summary:"check your detials or register first",duration:5500})
  // this.toast.error({detail:"Error", summary:"Are you register?",duration:5500})
  console.log(error);
  

}

 )


  }else{
    console.log("fill your details first fields are empty");
  }
}
 saveUserEmail(){
    console.log(this.credentials.userEmail);
    let valueofEmail=this.credentials.userEmail;
    console.log(valueofEmail)
    localStorage.setItem('user.Email',valueofEmail);// it will take key,value pair
  }

//  toTodopage(){
//   console.log("navigaating");
//     this.router.navigate(['/todo'])
//  }
}
