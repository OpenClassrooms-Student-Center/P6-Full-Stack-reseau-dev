import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {FormBuilder, Validators} from "@angular/forms";
import {Topic} from "../../../topic/interfaces/topic.interface";
import {User} from "../../interfaces/user.interface";
import {AuthService} from "../../../auth/services/auth.service";
import {UserService} from "../../services/user.service";
import {TopicService} from "../../../topic/services/topic.service";
import {SessionInformation} from "../../../auth/interfaces/sessionInformation.interface";

@Component({
  selector: 'app-me',
  templateUrl: './me.component.html',
  styleUrls: ['./me.component.scss']
})
export class MeComponent implements OnInit {

  public onError = false;
  public errorMessage = "";

  public form = this.fb.group({
    email: [
      '',
      [
        Validators.email
      ]
    ],
    username: ['', []],
    password: [
      '',
      [
        Validators.required,
        Validators.min(8),
        Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};:"\\\\|,.<>\\/?]).+$')
      ]
    ]
  });

  public user: User | undefined;

  public topics: Topic[] = [];

  constructor(private router: Router,
              private authService: AuthService,
              private fb: FormBuilder,
              private userService: UserService,
              private topicService: TopicService) {
  }

  public submit(): void
  {
    const updatedUser = this.form.value as User;
    this.userService.updateInfo(updatedUser).subscribe({
      next: (sessionInformation: SessionInformation) => {
        this.authService.logIn(sessionInformation);
        this.router.navigate(['/me']);
      },
      error: (error) => {
        this.onError = true;
        this.errorMessage = error.error.message;
      }
    });
  }

  public ngOnInit(): void
  {
    this.userService
      .me()
      .subscribe((user: User) => {
        this.user = user;
        this.form.patchValue({
          username: user.username,
          email: user.email,
          // Ne pas inclure password ici pour conserver les validateurs
        });
      });
    this.topicService.getUserSubscriptions().subscribe((topics: Topic[]) => {
      this.topics = topics;
    });
  }

  public unsubscribe(id:number): void{
    this.topicService.unfollow(id).subscribe({
      next: (topics: Topic[]) => {
        this.topics = topics;
      },
      error: (error) => {
        this.onError = true;
        this.errorMessage = error.error.message;
      }
    });
  }

  public logout(): void {
    this.authService.logOut();
    this.router.navigate([''])
  }

}
