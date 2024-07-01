import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../../interfaces/user.interface';
import { SessionService } from '../../services/session.service';
import { UserService } from '../../services/user.service';
import {FormBuilder, Validators} from "@angular/forms";
import {AuthService} from "../../features/auth/services/auth.service";
import {SessionInformation} from "../../interfaces/sessionInformation.interface";

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

  constructor(private router: Router,
              private sessionService: SessionService,
              private fb: FormBuilder,
              private authService: AuthService) {
  }

  public submit(): void {
    const updatedUser = this.form.value as User;
    console.log(updatedUser);
    this.authService.updateInfo(updatedUser).subscribe({
      next: (response: SessionInformation) => {
        this.sessionService.logIn(response);
        this.router.navigate(['/me']);
      },
      error: error => this.onError = true,
    });
  }

  public ngOnInit(): void {
    this.authService
      .me()
      .subscribe((user: User) => {
        this.user = user;
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
