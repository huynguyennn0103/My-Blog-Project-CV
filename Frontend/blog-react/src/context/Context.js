import { createContext, useEffect, useReducer } from "react";
import Reducer from "./Reducer";

const INITIAL_STATE = {
  user: JSON.parse(localStorage.getItem("user")) || null,
  isFetching: false,
  error: false,
  isLogout: false
};

export const Context = createContext(INITIAL_STATE);

export const ContextProvider = ({ children }) => {
  const [state, dispatch] = useReducer(Reducer, INITIAL_STATE);

  useEffect(() => {
    console.log("isLogout:" ,state.isLogout )
    if(!state.user || (state.user && !localStorage.getItem("token"))){
      localStorage.removeItem("user")
      localStorage.removeItem("token")
      localStorage.removeItem("expiration")
      if(state.isLogout){
        window.location.replace("/login")
      }
    }
    else {
      localStorage.setItem("user", JSON.stringify(state.user));
    }
  }, [state.user, state.isLogout]);

  return (
    <Context.Provider
      value={{
        user: state.user,
        isFetching: state.isFetching,
        error: state.error,
        isLogout: false,
        dispatch,
      }}
    >
      {children}
    </Context.Provider>
  );
};