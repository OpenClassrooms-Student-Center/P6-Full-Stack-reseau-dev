import { Component } from '@angular/core';
import {LanguageSwitcherComponent} from "../../component/language-switcher/language-switcher.component";
import {TranslocoModule} from "@jsverse/transloco";
import {MatButtonModule} from "@angular/material/button";
import {MatIconModule} from "@angular/material/icon";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatSlideToggleModule} from "@angular/material/slide-toggle";
import {Router, RouterModule} from "@angular/router";
import {SidenavService} from "../../../core/services/sidenav.service";
import {MatMenuItem} from "@angular/material/menu";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [RouterModule, MatSlideToggleModule, MatToolbarModule, MatIconModule, MatButtonModule, TranslocoModule, LanguageSwitcherComponent, MatMenuItem, NgIf],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent {
  constructor(
    private sidenavService: SidenavService,
    public router: Router,
  ) { }

  toggleSidenav() {
    this.sidenavService.toggle();
  }
}
