import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit {
  constructor() {}

  ngOnInit(): void {}

  Inscription() {
    alert("btn S'inscrire clicked !");
  }
  SeConnecter() {
    alert("btn Se connecter clicked !");
  }
}
