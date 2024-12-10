import {Component, inject} from '@angular/core';
import {SessionService} from "./services/session.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  private sessionService: SessionService = inject(SessionService);
  ngOnInit(){
    this.sessionService.initSession();
    if (!this.sessionService.isAuthenticated()) {
      this.sessionService.router.navigate(['/login']);
    }
  }
}
