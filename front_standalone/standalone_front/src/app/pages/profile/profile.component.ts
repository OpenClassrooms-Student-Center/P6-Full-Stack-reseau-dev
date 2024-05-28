import {Component, OnInit} from '@angular/core';
import {MatButton} from "@angular/material/button";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {TranslocoPipe} from "@jsverse/transloco";
import {MddUserService} from "../../core/services/mdd-user.service";
import {Topic} from "../../core/models/topic";
import {ThemeLayoutComponent} from "../../shared/component/theme-layout/theme-layout.component";
import {TopicService} from "../../core/services/topic.service";
import {AuthService} from "../../core/services/auth.service";
import {Router} from "@angular/router";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [
    MatButton,
    MatFormField,
    MatInput,
    MatLabel,
    ReactiveFormsModule,
    TranslocoPipe,
    ThemeLayoutComponent,
    NgIf
  ],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.scss'
})
export class ProfileComponent implements OnInit {

  usernameControl = new FormControl('', [Validators.required]);
  mailControl = new FormControl('', [Validators.required, Validators.email]);
  submitted: boolean = false;
  userForm = new FormGroup({
    username: this.usernameControl,
    mail: this.mailControl,
  });

  myTopicsSubscriptions: Topic[] = [];
  isloaded = false

  constructor(
    private mddUserService: MddUserService,
    private topicService: TopicService,
    private authService: AuthService,
    private router: Router,
  ) {


  }

  ngOnInit(): void {
        this.mddUserService.getMe().subscribe({next: info => {
          this.usernameControl.setValue(info.username);
          this.mailControl.setValue(info.email);
          this.myTopicsSubscriptions = info.topicsIds;
          this.isloaded = true;
        }})
    }

  handleChange() {

  }

  newUserInfo(){

  }


  unsubscribeFromTopic(topicId: number){
    this.topicService.unsubscribeTopic(topicId).subscribe({
      next: res =>
        { this.mddUserService.getMe().subscribe({
          next: info => {
            this.myTopicsSubscriptions = info.topicsIds;
        }})}
    });
  }

  logout(){
    this.authService.logout();
    this.router.navigate(['/welcome'])
  }
}
