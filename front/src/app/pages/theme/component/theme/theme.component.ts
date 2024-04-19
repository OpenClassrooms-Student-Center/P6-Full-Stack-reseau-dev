import { Component, OnInit } from '@angular/core';
import { Theme } from '../../interface/theme';
import { Observable } from 'rxjs';
import { ThemeService } from '../../service/theme.service';

@Component({
  selector: 'app-theme',
  templateUrl: './theme.component.html',
  styleUrls: ['./theme.component.scss']
})
export class ThemeComponent  {

  public themes$: Observable<Theme[]> = this.themeService.all();

  constructor(
    private themeService : ThemeService
    ) { }

}
