import { Component, HostListener, OnInit } from '@angular/core';
import { SessionService } from '../../services/session.service';
import { Subscription } from 'rxjs';

@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrl: './header.component.scss',
    standalone: false
})
export class HeaderComponent implements OnInit{
  showRightMenu:boolean =false;
  private subscription: Subscription = new Subscription();
isMobile: boolean= false;

  constructor(    private sessionService: SessionService  ){}
  ngOnInit(): void {
    this.subscription = this.sessionService.$isLogged().subscribe((isLogged) => {
      this.showRightMenu = isLogged;
    });
    this.checkScreenSize();
 
  }

   // Écoute les changements de taille d'écran
   @HostListener('window:resize', [])
   onResize() {
     this.checkScreenSize();
   }
 
   // Détermine si l'écran est de type mobile
   private checkScreenSize() {
     this.isMobile = window.innerWidth <= 768; // Par exemple, 768px est la largeur max pour mobile
   }

 
  }




