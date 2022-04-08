import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Route, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { ArchiveService } from './archive.service';
import { PendingtasksComponent } from './components/pendingtasks/pendingtasks.component';
import { LoginService } from './login.service';
import { PendingTaskService } from './pending-task.service';
import { SortingComponent } from './sorting/sorting.component';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

constructor(private loginservice:LoginService , private router:Router ,private archive:ArchiveService,
  private pendingtasks:PendingTaskService)
{

}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    
    if(this.loginservice.isLoggedIn()){
      return true;
    }
    
    this.router.navigate(['login'])
   
      return false;
  }
  
}
