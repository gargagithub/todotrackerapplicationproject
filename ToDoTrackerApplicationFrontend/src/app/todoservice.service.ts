import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Subject, tap } from 'rxjs';
import { Observable } from 'rxjs/internal/Observable';
import { Task } from './task';

@Injectable({
  providedIn: 'root'
})
export class TodoserviceService {

  
  constructor(private http:HttpClient) { }

  private refreshNeeded$= new Subject<void>();
  get refreshNeeded(){
    return this.refreshNeeded$;
  }
  saveOfficialTask(task:any,userEmail:any){
    console.log("serviceTask"+ task.value);
    return this.http.post('http://localhost:9000/api/v2/user/officialTask/'+userEmail,task)
                    .pipe(
                      tap(()=>{
                        this.refreshNeeded$.next();
                      })
                    );
  }
  savePersonalTask(task:any,userEmail:any){
    console.log("serviceTask"+ task.value);
    return this.http.post('http://localhost:9000/api/v2/user/personalTask/'+userEmail,task)
    .pipe(
      tap(()=>{
        this.refreshNeeded$.next();
      })
    );
  }

  getOfficialTask(user:any){
    return this.http.get("http://localhost:9000/api/v2/user/officialTask/"+user);
  }
  getPersonalTask(user:any){
    return this.http.get("http://localhost:9000/api/v2/user/personalTask/"+user);
  }

  deleteOfficialTask(userEmail:any,taskId:any){
    return this.http.delete("http://localhost:9000/api/v2/user/officialTask/"+userEmail+"/"+taskId);
  }
  deletePersonalTask(userEmail:any,taskId:any){
    return this.http.delete("http://localhost:9000/api/v2/user/personalTask/"+userEmail+"/"+taskId);
  }

  updateOfficialTask(task:any,userEmail:any,taskId:any){
    return this.http.put("http://localhost:9000/api/v2/user/officialTask/"+userEmail+"/"+taskId,task)
    .pipe(
      tap(()=>{
        this.refreshNeeded$.next();
      })
    );
  }
  updatePersonalTask(task:any,userEmail:any,taskId:any){
    return this.http.put("http://localhost:9000/api/v2/user/personalTask/"+userEmail+"/"+taskId,task)
    .pipe(
      tap(()=>{
        this.refreshNeeded$.next();
      })
    );
  }
  
  getOfficialTaskById(userEmail:any,taskId:any){
    return this.http.get("http://localhost:9000/api/v2/user/officialTaskById/"+userEmail+"/"+taskId);
  }
  getPersonalTaskById(userEmail:any,taskId:any){
    return this.http.get("http://localhost:9000/api/v2/user/personalTaskById/"+userEmail+"/"+taskId);
  }

  sortByPriorityOfOfficialTask(userEmail:any){
    return this.http.get("http://localhost:9000/api/v2/user/sortedOfficialTask/"+userEmail);
  }
  sortByPriorityOfPersonalTask(userEmail:any){
    return this.http.get("http://localhost:9000/api/v2/user/sortedPersonalTask/"+userEmail);
  }

  completedOfficialTask(userEmail:any,taskId:any){
    return this.http.delete("http://localhost:9000/api/v2/user/complete/officialTask/"+userEmail+"/"+taskId);
  }
  completedPersonalTask(userEmail:any,taskId:any){
    return this.http.delete("http://localhost:9000/api/v2/user/complete/personalTask/"+userEmail+"/"+taskId);
  }

  getNotifications(userEmail:any):Observable<Task[]> {
    return this.http.get<Task[]>("http://localhost:9000/api/v2/user/notifications/"+userEmail)
    .pipe(
      tap(()=>{
        this.refreshNeeded$.next();
      })
    );
  }

  updateUserdetails(user:any,userEmail:any){
    return this.http.put("http://localhost:9000/api/v2/update/"+userEmail,user);
  }

   populateTask1:any;
   populateTask:any
   personalbuttonDisabled:any;
   officialbuttonDisabled:any;
   addbuttonhidden:any;
  populatePersonalForm(task1:any){
    this.populateTask1=task1;
  }
  populateOfficialForm(task:any){
    this.populateTask=task;
  }

  sendPopulatedData(){
    return this.populateTask1;
  }
  sendPopulatedOfficialData(){
    return this.populateTask;
  }
}
