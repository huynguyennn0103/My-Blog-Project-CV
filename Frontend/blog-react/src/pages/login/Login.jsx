import { request } from "../../utils/http";
import { useContext, useRef } from "react";
import { Link } from "react-router-dom";
import { Context } from "../../context/Context";
import "./login.css";
import { LoginFailure, LoginStart, LoginSuccess } from "../../context/Actions";
import { ToastContainer, toast } from "react-toastify";
export default function Login() {
  const userRef = useRef();
  const passwordRef = useRef();
  const { dispatch, isFetching} = useContext(Context);
  console.log("Is fetching: ", isFetching)
  const handleSubmit = async (e) => {
      e.preventDefault();
      dispatch(LoginStart());

      const formData = new FormData();
      formData.append("email", userRef.current.value)
      formData.append("password", passwordRef.current.value)
      const res = await request("POST","signin", formData, false, {
        'Content-Type': 'multipart/form-data'
      });
      if(res.data.statusCode === 401){
        toast.warning(res.data.message)
        dispatch(LoginFailure())
      }
      else {
        localStorage.setItem("token", JSON.stringify(res.data.data.access_token))

        const expiration = new Date();
        expiration.setMinutes(expiration.getMinutes()+ 10);
        localStorage.setItem('expiration', expiration.toISOString())
  
        const userRes = await request("GET", `user/email?email=${userRef.current.value}`, null , false, {
          'Authorization': 'Bearer ' + res.data.data.access_token,
          'x-api-key' : 'huynguyenn0103'
        })
        dispatch(LoginSuccess(userRes.data.data));
        window.location.replace("/")
      }
  };

  return (
    <>
      <ToastContainer
        position="top-right"
        autoClose={5000}
        hideProgressBar={false}
        newestOnTop
        closeOnClick
        rtl={false}
        pauseOnFocusLoss
        draggable
        pauseOnHover
        theme="colored"
      />
      <div className="login">
        <span className="loginTitle">Login</span>
        <form className="loginForm" onSubmit={handleSubmit}>
          <label>Email</label>
          <input
            type="text"
            className="loginInput"
            placeholder="Enter your email..."
            ref={userRef}
          />
          <label>Password</label>
          <input
            type="password"
            className="loginInput"
            placeholder="Enter your password..."
            ref={passwordRef}
          />
          <button className="loginButton" type="submit" disabled={isFetching}>
            Login
          </button>
        </form>
        <button className="loginRegisterButton">
          <Link className="link" to="/register">
            Register
          </Link>
        </button>
      </div>
    </>
  );
}