import {combineReducers, configureStore} from "@reduxjs/toolkit";
import userReducer from "./slice/userReducer";
import createSagaMiddleware from "redux-saga";
import rootSaga from "./sagas/index";

const rootReducer = combineReducers({
    userReducer,
});

const sagaMiddleware = createSagaMiddleware()

export const  store = configureStore({
    reducer:rootReducer,
    middleware:getDefaultMiddleware => {
        return getDefaultMiddleware({serializableCheck: false}).concat(sagaMiddleware);
    }
})

sagaMiddleware.run(rootSaga);