import { Component, OnInit } from '@angular/core';
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

    constructor(private authService: AuthService, private router: Router) {}

    ngOnInit(): void {
        this.isLoggedIn = this.authService.isLoggedIn();

        this.router.events
            .pipe(filter(event => event instanceof NavigationEnd))
            .subscribe(() => {
                this.checkIfLoginPage();
                this.checkIfRegisterPage();
                this.checkIfArticlesPage();
                this.checkIfThemesPage();
            });
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
        const currentUrl = this.router.url;
        this.isArticlesPage = currentUrl === '/article'; // Vérifie si la route actuelle est /article
        console.log('URL actuelle:', currentUrl); // Affiche l'URL actuelle
        console.log('isArticlesPage:', this.isArticlesPage); // Vérifie la valeur de isArticlesPage
    }

    private checkIfThemesPage() {
        this.isThemesPage = this.router.url === '/themes';
    }
}
