import { Component, OnInit } from '@angular/core';
import { Theme } from '../../interface/theme';
import { Observable, mergeMap, of, take } from 'rxjs';
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
export class ThemeComponent implements OnInit {

  // public user: Observable<User> | undefined;

  public user!: User;

  public theme! : Theme;

  public themes= this.themeService.all();

  public themesByUser$ : Observable<Theme[]> | undefined ;

  private userId: string;
  
  public isParticipate : boolean | undefined;

  public isFollow! : boolean;

  constructor(
    private themeService : ThemeService,
    private route: ActivatedRoute, 
    private userService : UserService,
    private sessionService : SessionService
    ) { 
      this.userId = this.sessionService.sessionInformation!.id.toString();
    }
  ngOnInit(): void {

    // this.themes$!.forEach(theme_array => {
    //     theme_array.forEach(theme => {
    //       console.log(theme.themeId);
    //       this.fetchTheme(theme.themeId);
    //   })
    // });

    // const themes= this.themeService.all().pipe(take(1));
    // const  user = this.userService.getById(this.userId).pipe(take(1));
      // faire une requette au back pour recuperer le user avec les themes associés
      this.userService
      .getById(this.userId)
      .subscribe((user: User) => 
        {
          this.user = user;
          this.themes!.forEach(theme_array => {
            theme_array.forEach(theme => {
              console.log(this.user);
              this.user?.themes.forEach(themeByUser => {
                console.log(theme.themeId);
                console.log(themeByUser.themeId);
                console.log(theme.follow);
                if(theme.themeId === themeByUser.themeId) {
                  console.log(themeByUser);
                  theme.follow=true;
                  this.isFollow=true;
                  console.log(theme.follow);
                } else {
                  theme.follow=false;
                  this.isFollow=false;
                  console.log(theme.follow);
                }
              })
            })
          });
        });

      // parcourrir la liste de tous les themes 
      // si le theme est egalement dans la liste des themes du user alors mettre l'attribut follow à true
  }

    public follow(themeId : any): void {
      console.log(themeId);
      this.userService.follow(themeId, this.userId).subscribe(_ => this.fetchTheme(themeId));
    }
  
    public unFollow(themeId : any): void {
      //theme_id=1;
      console.log(themeId);
      this.userService.unFollow(themeId, this.userId).subscribe(_ => this.fetchTheme(themeId));
    }

  
    private fetchTheme(themeId : any,): void {
      this.userService.getById(this.userId)
      .subscribe((user: User) => {
        this.user= user;
        // this.user?.themes.forEach(themeByUser => {
        //   console.log(themeByUser.themeId);
        //   if(themeId === themeByUser.themeId) {
        //     console.log(themeByUser);
        //     this.isParticipate = user.themes.some(u => u === themeId);
        //     // if( this.isParticipate === true) {
        //     // this.themeService.getById(themeId).subscribe( theme => { 
        //     //   // if(theme.follow=true) {
        //     //     this.isFollow = "true", console.log(theme.follow)
        //     //   // } else {

        //     //   // }
        //     //   })
        //     // }
        //     // console.log();
        //   } else {
        //     // this.themeService.getById(themeId).subscribe( theme => 
        //     //   this.isFollow="false")
        //     this.isParticipate = false;
        //   }
        // })
        console.log(themeId)
        // this.isParticipate = user.themes.some(u => u.themeId === themeId);
        console.log(this.isParticipate);
        this.themeService.getById
          (themeId)
          .subscribe((theme: Theme) => this.theme = theme);
      });

    }

}
