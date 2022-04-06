import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ArchiveService {

  constructor(private http:HttpClient) { }

  getOfficialTaskFromTodo(userEmail:any){
    console.log("archiveservice "+ userEmail);
    return this.http.get('http://localhost:9000/api/v3/archive/officialTask/'+userEmail);
  }

  getPersonalTaskFromTodo(userEmail:any){
    console.log("archiveservice "+ userEmail);
    return this.http.get('http://localhost:9000/api/v3/archive/personalTask/'+userEmail);
  }

  deletePersonalTaskFromTodo(archiveId:any, taskId:any){
    console.log("archiveservice "+ archiveId);
    return this.http.delete('http://localhost:9000/api/v3/archive/personalTask/'+archiveId+"/"+taskId);
  }
}
