import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  getEmail(): any {
    throw new Error('Method not implemented.');
  }

  url="http://localhost:9000/api/v1"

  userEmailfromLogin:any;
  
  constructor(private http:HttpClient) { }

 doLogin(credentials:any){
  //  token generate
return this.http.post(`${this.url}/login`, credentials)

 }
 

// you ensure wher user is login or not we stored a token in the local strogae so when token is in local stroge we assumed user is in application 
loginUser(credentials:any)
{
 localStorage.setItem("token",credentials)
 return true;
}
//  to check user is logged in or not
isLoggedIn()
{
let token=localStorage.getItem("token");
if(token==undefined || token=='' || token==null){
  return false;
} else {
  return true;
}
}

// for logout user
logout()
{
localStorage.removeItem('token')
return true;
}

// for get token 

getToken(){
  return localStorage.getItem("token")
}

  getUserByEmail(userEmail:any){ 
    return this.http.get("http://localhost:9000/api/v1/userservice/users/"+userEmail);
  }
}



