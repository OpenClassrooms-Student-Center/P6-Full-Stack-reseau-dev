import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

/**
 * Represents the home component, landing page of the application
 */
@Component({
  selector: 'app-home',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
})
export class HomeComponent {}
