import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../app/services/auth.service'; // Assurez-vous que le chemin est correct
import { Router } from '@angular/router'; // Importer Router pour vérifier la route actuelle

@Component({
    selector: 'app-navbar',
    templateUrl: './navbar.component.html',
    styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {
    isLoggedIn: boolean = false; // Initialiser à false
    isMenuOpen: boolean = false; // Pour la gestion du menu
    isResponsiveView: boolean = false; // À définir selon votre logique
    isLoginPage: boolean = false; // Variable pour savoir si on est sur la page de login

    constructor(private authService: AuthService, private router: Router) {}

    ngOnInit(): void {
        // Vérifier l'état de connexion au démarrage
        this.isLoggedIn = this.authService.isLoggedIn();
        this.checkIfLoginPage();
    }

    toggleMenu() {
        this.isMenuOpen = !this.isMenuOpen; // Changer l'état du menu
    }

    logout() {
        this.authService.logout(); // Appelle la méthode de déconnexion
        this.isLoggedIn = false; // Met à jour l'état de connexion
    }

    private checkIfLoginPage() {
        // Vérifiez si la route actuelle est la page de login
        this.isLoginPage = this.router.url === '/login';
    }
}
