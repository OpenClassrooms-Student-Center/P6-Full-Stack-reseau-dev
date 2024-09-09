import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

/**
 * Represents the component for displaying the "404 Not Found" page.
 */
@Component({
  selector: 'app-not-found',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './not-found.component.html',
  styleUrl: './not-found.component.scss',
})
export class NotFoundComponent {}
