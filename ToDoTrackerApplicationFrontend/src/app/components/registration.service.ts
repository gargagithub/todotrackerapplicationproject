import { Injectable } from '@angular/core';

import { HttpClient } from '@angular/common/http';
import { User } from './user';
// import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  constructor(private http:HttpClient) { }

register(User:any){
  return this.http.post("http://localhost:9000/api/v2/register",User);
}

}
