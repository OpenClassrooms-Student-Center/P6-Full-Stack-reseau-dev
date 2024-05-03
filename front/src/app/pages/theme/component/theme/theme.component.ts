import { Component, OnInit } from '@angular/core';
import { Theme } from '../../interface/theme';
import { Observable } from 'rxjs';
import { ThemeService } from '../../service/theme.service';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from 'src/app/pages/user/service/user.service';
import { SessionInformation } from 'src/app/interfaces/sessionInformation.interface';
import { SessionService } from 'src/app/services/session.service';
import { User } from 'src/app/pages/user/interface/user.interface';

@Component({
  selector: 'app-theme',
  templateUrl: './theme.component.html',
  styleUrls: ['./theme.component.scss']
})
export class ThemeComponent  {

  public theme: Theme | undefined;

  public user: User | undefined;

  public themes$: Observable<Theme[]> = this.themeService.all();

  private userId: string;

  private themeId : string;

  public isFollow = false;

  constructor(
    private themeService : ThemeService,
    private route: ActivatedRoute, 
    private userService : UserService,
    private sessionService : SessionService
    ) { 
      this.themeId = this.route.snapshot.paramMap.get('id')!;
      this.userId = this.sessionService.sessionInformation!.id.toString();
      console.log(this.themeId)
    }

    public follow(): void {
      this.userService.follow(this.themeId, this.userId).subscribe(_ => this.fetchTheme());
    }
  
    public unFollow(): void {
      this.userService.unFollow(this.themeId, this.userId).subscribe(_ => this.fetchTheme());
    }
  
    private fetchTheme(): void {
      this.userService.getById(this.userId)
      .subscribe((user: User) => {
        this.user = user;
        this.isFollow = user.themes.some(u => u === this.themeService.getById(this.themeId));
        console.log(this.isFollow)
      });

    }

}
