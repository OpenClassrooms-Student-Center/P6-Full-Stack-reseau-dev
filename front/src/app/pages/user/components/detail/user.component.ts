import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { SessionService } from 'src/app/services/session.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { User } from '../../interface/user.interface';
import { UserService } from '../../service/user.service';
import { Theme } from 'src/app/pages/theme/interface/theme';
import { ThemeService } from 'src/app/pages/theme/service/theme.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit {
  public user: User | undefined;

  public themes$: Theme[] | undefined;

  public editUserForm: FormGroup | undefined;

  private id: string | undefined;

  constructor(private router: Router,
              private sessionService: SessionService,
              private matSnackBar: MatSnackBar,
              private fb: FormBuilder,
              private userService: UserService) {
  }

  public ngOnInit(): void {
    this.userService
      .getById(this.sessionService.sessionInformation!.id.toString())
      .subscribe((user: User) => {
        this.user = user, 
        this.themes$ =  this.user?.themes
      });

    //this.themes$ =  this.user?.themes;
  }

  public back(): void {
    window.history.back();
  }

  public submit(): void {
    const user = this.editUserForm?.value as User;

      this.userService
        .update(this.id!, user)
        .subscribe((_: User) => this.exitPage('Session updated !'));
    }

    private exitPage(message: string): void {
      this.matSnackBar.open(message, 'Close', { duration: 3000 });
      this.router.navigate(['user']);
    }
}
