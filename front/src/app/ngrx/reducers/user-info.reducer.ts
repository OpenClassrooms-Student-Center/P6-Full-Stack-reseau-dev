import { createReducer, on } from '@ngrx/store';
import { setInfo } from '@mdd-global-state-ngrx/actions/user-info.actions';
import { UserBasicInfo } from '@core/types/user.type';

/**
 * Initial state for user information.
 */
export const userInfoInitialState: UserBasicInfo = {
  id: null,
  username: null,
  email: null,
};

/**
 * Reducer function for user information.
 */
export const userInfoReducer = createReducer(
  userInfoInitialState,
  on(setInfo, (state, info: UserBasicInfo) => {
    const { id, email, username } = info;

    return { ...state, id, username, email };
  })
);
