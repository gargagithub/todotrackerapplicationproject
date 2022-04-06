import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class PendingTaskService {

  constructor(private http:HttpClient) { }

    getPendingOfficialTask(userEmail:any){
      console.log("pendingservice "+ userEmail);
      return this.http.get('http://localhost:9000/api/v2/user/pending/officialTask/'+userEmail);
    }
  
    getPendingPersonalTask(userEmail:any){
      console.log("pendingservice "+ userEmail);
      return this.http.get('http://localhost:9000/api/v2/user/pending/personalTask/'+userEmail);
    }
  
}
