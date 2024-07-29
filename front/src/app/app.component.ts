import {ChangeDetectorRef, Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {NavigationCancel, NavigationEnd, NavigationStart, Router} from '@angular/router';
import {BehaviorSubject, filter, Observable, Subscription} from 'rxjs';
import {MatSidenav} from "@angular/material/sidenav";
import {AuthService} from "./features/auth/services/auth.service";
import {LoaderService} from "./shared/services/loading.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit, OnDestroy{

  @ViewChild('sidenav') sidenav!: MatSidenav;

  currentRoute:string = "";

  public loading$ = new BehaviorSubject<boolean>(false);

  private routerSubscription: Subscription = new Subscription();
  private loadingSubscription: Subscription = new Subscription();

  constructor(
    private changeDetector : ChangeDetectorRef,
    private loaderService: LoaderService,
    private router: Router,
    private authService: AuthService
  )
  {}

  ngOnInit() {
    this.loadingSubscription = this.loaderService.loading$.subscribe((loading) => {
      this.loading$.next(loading);
    });
    this.routerSubscription = this.router.events.subscribe(event => {
      if (event instanceof NavigationStart) {
        this.loaderService.show();
      }
      if (event instanceof NavigationEnd) {
        this.currentRoute = event.url;
      }
      if(event instanceof  NavigationCancel){
        this.loaderService.hide();
      }
    });
  }

  public openSidenav(): void {
    this.sidenav.open();
  }

  public isLogged(): boolean {
    return this.authService.isLogged();
  }

  ngOnDestroy() {
    if(this.routerSubscription){
      this.routerSubscription.unsubscribe();
    }
    if(this.loadingSubscription){
      this.loadingSubscription.unsubscribe();
    }
  }

  ngAfterViewChecked(){
    this.changeDetector.detectChanges();
  }

}
