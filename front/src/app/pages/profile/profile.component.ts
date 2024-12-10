import {Component, inject} from '@angular/core';
import {BackComponent} from "../../components/back/back.component";
import {MatButton} from "@angular/material/button";
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {SessionService} from "../../services/session.service";
import {UserService} from "../../services/user.service";
import {UserModel} from "../../models/UserModel";
import {MessageModel} from "../../models/MessageModel";
import {TokenModel} from "../../models/TokenModel";
import {CardThemeComponent} from "../themes/card-theme/card-theme.component";
import {ThemeModelDto} from "../../models/ThemeModelDto";

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [
    BackComponent,
    MatButton,
    ReactiveFormsModule,
    CardThemeComponent
  ],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.scss'
})
export class ProfileComponent {
  user!: UserModel;
  fb:FormBuilder = inject(FormBuilder);
  email: string | null = "";
  public sessionService:SessionService = inject(SessionService);
  userService:UserService = inject(UserService);
  public form: FormGroup = this.fb.group({
    username: ['', [Validators.required]],
    email: ['', [Validators.required, Validators.email]],
  });
  ngOnInit(): void {
    this.getProfile()
  }
  /**
   * Retrieves the profile of the authenticated user from the server and updates the form with the user's data.
   * The profile data is logged to the console, and if valid, sets the form's username and email fields.
   */
  getProfile(){
    this.userService.profil().subscribe( (result: UserModel): void=>{
      console.log(result)
      if(result){
        this.user = result;
        this.form.setValue(
          {
            username: result.username,
            email: result.email,
          }
        )
      }
    })
  }
  /**
   * Sends the updated profile data from the form to the server to save changes.
   * Logs the server's response (MessageModel) to the console.
   */
  save(): void{
    this.userService.editProfil(this.form.value).subscribe( (result:MessageModel): void=>{
      console.log(result)
    })
  }

}
