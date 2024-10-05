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

    constructor(private authService: AuthService, private router: Router) {}

    ngOnInit(): void {
        this.isLoggedIn = this.authService.isLoggedIn();

        // Vérifiez l'état de la route
        this.router.events
            .pipe(filter(event => event instanceof NavigationEnd))
            .subscribe(() => {
                this.checkIfLoginPage();
                this.checkIfRegisterPage();
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
}
