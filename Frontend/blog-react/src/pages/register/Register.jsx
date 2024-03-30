import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import "./register.css";
import { request } from "../../utils/http";
import {ToastContainer, toast} from 'react-toastify'
export default function Register() {
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState(false);
  const navigate = useNavigate();
  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(false);
    const res = await request("POST", "user/register", {
      username,
      email,
      password,
      profilePic: ""
    });
    if(res.data.statusCode === 500){
      toast.error(res.data.message);
      setError(true)
    }
    else {
      navigate("/login");
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
      <div className="register">
        <span className="registerTitle">Register</span>
        <form className="registerForm" onSubmit={handleSubmit}>
          <label>Username</label>
          <input
            type="text"
            className="registerInput"
            placeholder="Enter your username..."
            onChange={(e) => setUsername(e.target.value)}
          />
          <label>Email</label>
          <input
            type="text"
            className="registerInput"
            placeholder="Enter your email..."
            onChange={(e) => setEmail(e.target.value)}
          />
          <label>Password</label>
          <input
            type="password"
            className="registerInput"
            placeholder="Enter your password..."
            onChange={(e) => setPassword(e.target.value)}
          />
          <button className="registerButton" type="submit">
            Register
          </button>
        </form>
        <button className="registerLoginButton">
          <Link className="link" to="/login">
            Login
          </Link>
        </button>
        {error && (
          <span style={{ color: "red", marginTop: "10px" }}>
            Something went wrong!
          </span>
        )}
      </div>
    </>
  );
}