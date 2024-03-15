import {Component, EventEmitter, Output} from '@angular/core';
import {SessionService} from "../../../services/session.service";
import {Observable} from "rxjs";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent {

  @Output() public sidenavToggle = new EventEmitter();

  constructor(private sessionService: SessionService) {
  }

  public onToggleSidenav = () => {
    this.sidenavToggle.emit();
  }

  public $isLogged(): Observable<boolean> {
    return this.sessionService.$isLogged();
  }
}
