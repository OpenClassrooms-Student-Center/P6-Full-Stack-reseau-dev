import { Component, OnInit, HostListener } from '@angular/core';
import { AuthService } from '../../app/services/auth.service'; 
import { Router, NavigationEnd } from '@angular/router'; 
import { filter } from 'rxjs/operators';

@Component({
    selector: 'app-navbar',
    templateUrl: './navbar.component.html',
    styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {
    isLoggedIn: boolean = false; 
    isMenuOpen: boolean = false; 
    isResponsiveView: boolean = false; 
    isLoginPage: boolean = false; 
    isRegisterPage: boolean = false;
    isArticlesPage: boolean = false; 
    isThemesPage: boolean = false; 
    isProfilePage: boolean = false; 

    constructor(private authService: AuthService, private router: Router) {}

    ngOnInit(): void {
        this.isLoggedIn = this.authService.isLoggedIn();
        this.checkResponsiveView();

        this.router.events
            .pipe(filter(event => event instanceof NavigationEnd))
            .subscribe(() => {
                this.checkIfLoginPage();
                this.checkIfRegisterPage();
                this.checkIfArticlesPage();
                this.checkIfThemesPage(); // Vérifiez si vous êtes sur la page de thèmes
                this.checkIfProfilePage();
            });
    }

    @HostListener('window:resize', ['$event'])
    onResize(event: Event) {
        this.checkResponsiveView();
    }

    checkResponsiveView() {
        this.isResponsiveView = window.innerWidth <= 768;
    }

    toggleMenu() {
        this.isMenuOpen = !this.isMenuOpen;
    }    

    logout() {
        this.authService.logout();
        this.isLoggedIn = false;
    }

    private checkIfLoginPage() {
        this.isLoginPage = this.router.url === '/login';
    }

    private checkIfRegisterPage() {
        this.isRegisterPage = this.router.url === '/register';
    }

    private checkIfArticlesPage() {
        this.isArticlesPage = this.router.url === '/article'; // Seulement pour '/article'
    }

    private checkIfThemesPage() {
        this.isThemesPage = this.router.url === '/themes'; // Seulement pour '/themes'
    }

    private checkIfProfilePage() {
        this.isProfilePage = this.router.url === '/profile';
    }
}
