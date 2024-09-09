import { isDevMode } from '@angular/core';
import {
  ActionReducer,
  ActionReducerMap,
  createFeatureSelector,
  createSelector,
  MetaReducer,
} from '@ngrx/store';
import { userInfoReducer } from './user-info.reducer';

/**
 * Interface representing the application state.
 */
export interface State {}

/**
 * Action reducer map defining how actions modify the application state.
 */
export const reducers: ActionReducerMap<State> = {
  userInfo: userInfoReducer,
};

/**
 * Meta reducers that enhance or modify the behavior of action reducers.
 */
export const metaReducers: MetaReducer<State>[] = isDevMode() ? [] : [];
