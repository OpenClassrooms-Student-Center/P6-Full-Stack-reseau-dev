import { UserBasicInfo, UserInfo } from '@core/types/user.type';
import { createAction, props } from '@ngrx/store';

/**
 * This action is used to set the user information in the state.
 * It omits the 'token' property from the user information.
 *
 * @example
 * dispatch(setInfo({id: 1, username: 'John', email: 'john@example.com'}));
 *
 * @returns {object} An action object with a type of `'set-info'` and a payload of user information.
 */
export const setInfo = createAction('set-info', props<UserBasicInfo>());
