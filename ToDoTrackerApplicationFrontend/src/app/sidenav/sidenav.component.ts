import { Component, OnInit, HostBinding } from '@angular/core';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Observable } from 'rxjs';
import { map, shareReplay } from 'rxjs/operators';
import { Router } from '@angular/router';
import { MatSidenav } from '@angular/material/sidenav';
import { MatSnackBar } from '@angular/material/snack-bar';
@Component({
  selector: 'app-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrls: ['./sidenav.component.css']
})
export class SidenavComponent implements OnInit {

  public loading!: boolean;
  public isAuthenticated!: boolean;
  public title!: string;

  public isBypass!: boolean;
  public mobile!: boolean;
  public isMenuInitOpen!: boolean;

  constructor(private breakpointObserver: BreakpointObserver,
              private router: Router,
              private _snackBar: MatSnackBar) { }

    private sidenav!: MatSidenav;

    public isMenuOpen = true;
    public contentMargin = 240;

    isHandset$: Observable<boolean> = this.breakpointObserver.observe(Breakpoints.Handset)
    .pipe(
      map(result => result.matches)
    );


  ngOnInit() {
    this.isMenuOpen = true;  // Open side menu by default
    this.title = 'Material Layout Demo';
  }

  ngDoCheck() {
      if (this.isHandset$) {
         this.isMenuOpen = false;
      } else {
         this.isMenuOpen = true;
      }
  }

  public openSnackBar(msg: string): void {
    this._snackBar.open(msg, 'X', {
      duration: 2000,
      horizontalPosition: 'center',
      verticalPosition: 'top',
      panelClass: 'notif-error'
    });
  }

  public onSelectOption(option: any): void {
    const msg = `Chose option ${option}`;
    this.openSnackBar(msg);

    /* To route to another page from here */
    // this.router.navigate(['/home']);
  }

 
}

