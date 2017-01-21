import { Component, OnInit } from '@angular/core';
import { NgRedux } from 'ng2-redux';

import { IAppState, rootReducer } from './store/index';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private ngRedux: NgRedux<IAppState>) {
    this.ngRedux.configureStore(rootReducer, {}, []);
  }

  ngOnInit() {
  }
}
