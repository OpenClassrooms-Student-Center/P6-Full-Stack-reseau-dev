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

    constructor(private authService: AuthService, private router: Router) {}

    ngOnInit(): void {
        // Initialisation de l'état de connexion
        this.isLoggedIn = this.authService.isLoggedIn();

        // Vérification initiale de la vue responsive lors du chargement
        this.checkResponsiveView();

        // Écouter les événements de changement de route
        this.router.events
            .pipe(filter(event => event instanceof NavigationEnd))
            .subscribe(() => {
                this.checkIfLoginPage();
                this.checkIfRegisterPage();
                this.checkIfArticlesPage(); // Vérification des pages d'articles
                this.checkIfThemesPage();
            });
    }

    // Écouter les changements de taille de la fenêtre
    @HostListener('window:resize', ['$event'])
    onResize(event: Event) {
        this.checkResponsiveView();
    }

    // Méthode pour vérifier la taille de la fenêtre et activer le mode mobile si nécessaire
    checkResponsiveView() {
        this.isResponsiveView = window.innerWidth <= 768;
        console.log('isResponsiveView:', this.isResponsiveView, 'Window width:', window.innerWidth);
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
        // Vérifiez si l'URL correspond à une page d'articles
        this.isArticlesPage = currentUrl.startsWith('/article') && currentUrl !== '/article'; // Assurez-vous de ne pas inclure la liste des articles
        console.log('URL actuelle:', currentUrl);
        console.log('isArticlesPage:', this.isArticlesPage);
    }

    private checkIfThemesPage() {
        this.isThemesPage = this.router.url === '/themes';
    }
    
}
