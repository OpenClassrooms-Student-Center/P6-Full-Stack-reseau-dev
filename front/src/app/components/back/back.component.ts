import {Component, inject, Input} from '@angular/core';
import {Location} from "@angular/common";
import {Router} from "@angular/router";

@Component({
  selector: 'app-back',
  standalone: true,
  imports: [],
  templateUrl: './back.component.html',
  styleUrl: './back.component.scss'
})
export class BackComponent {
  location: Location = inject(Location);
  router:Router = inject(Router);
  @Input() path!: string;
  /**
   * Navigates the user to a specified path or goes back to the previous location.
   * If a `path` is defined, the user is navigated to that path using the router.
   * If no `path` is provided, the browser's history is navigated back to the previous page.
   */
  back(): void{
    (this.path) ? this.router.navigate([this.path]) : this.location.back();
  }
}
