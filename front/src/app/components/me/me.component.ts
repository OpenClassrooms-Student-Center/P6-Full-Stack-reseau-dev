import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../../interfaces/user.interface';
import { SessionService } from '../../services/session.service';
import { UserService } from '../../services/user.service';
import {FormBuilder, Validators} from "@angular/forms";
import {TopicService} from "../../features/auth/services/topic.service";
import {Topic} from "../../interfaces/topic.interface";
import {Token} from "../../interfaces/token.interface";

@Component({
  selector: 'app-me',
  templateUrl: './me.component.html',
  styleUrls: ['./me.component.scss']
})
export class MeComponent implements OnInit {

  public onError = false;

  public form = this.fb.group({
    email: [
      '',
      [
        Validators.email
      ]
    ],
    username: ['', []]
  });

  public user: User | undefined;

  public topics: Topic[] = [];

  constructor(private router: Router,
              private sessionService: SessionService,
              private fb: FormBuilder,
              private userService: UserService,
              private topicService: TopicService) {
  }

  public submit(): void {
    const updatedUser = this.form.value as User;
    console.log(updatedUser);
    this.userService.updateInfo(updatedUser).subscribe({
      next: (response: Token) => {
        this.sessionService.logIn(response);
        this.router.navigate(['/me']);
      },
      error: error => this.onError = true,
    });
  }

  public ngOnInit(): void {
    this.userService
      .me()
      .subscribe((user: User) => {
        this.user = user;
        console.log(user);
        this.form = this.fb.group({
          username: [user.username],
          email: [user.email]
        });
      });
  }

  public back(): void {
    window.history.back();
  }

  public logout(): void {
    this.sessionService.logOut();
    this.router.navigate([''])
  }

}
