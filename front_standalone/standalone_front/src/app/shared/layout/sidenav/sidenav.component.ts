import {Component, ViewChild} from '@angular/core';
import {MatSidenav, MatSidenavModule} from "@angular/material/sidenav";
import {MatButtonModule} from "@angular/material/button";
import {RouterModule, RouterOutlet} from "@angular/router";
import {MatIconModule} from "@angular/material/icon";
import {MatListModule} from "@angular/material/list";
import {LanguageSwitcherComponent} from "../../component/language-switcher/language-switcher.component";
import {TranslocoPipe} from "@jsverse/transloco";
import {SidenavService} from "../../../core/services/sidenav.service";

@Component({
  selector: 'app-sidenav',
  standalone: true,
  templateUrl: './sidenav.component.html',
  styleUrl: './sidenav.component.scss',
  imports: [MatSidenavModule, MatButtonModule, RouterOutlet, RouterModule, MatIconModule, MatListModule, LanguageSwitcherComponent, TranslocoPipe]
})
export class SidenavComponent {
  @ViewChild('sidenav') public sidenav!: MatSidenav;

  constructor(private sidenavService: SidenavService) { }

  ngAfterViewInit(): void {
    this.sidenavService.setSidenav(this.sidenav);
  }
}
