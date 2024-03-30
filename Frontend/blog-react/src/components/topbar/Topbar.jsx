import {Link, useLocation, useNavigate} from 'react-router-dom'
import "./topbar.css";
import { useContext, useState } from 'react';
import { Context } from '../../context/Context';
import { Logout } from '../../context/Actions';
export default function Topbar() {
  const { user, dispatch } = useContext(Context);
  const PF = process.env.REACT_APP_backend_URL + "public/avas/"
  const navigate = useNavigate();
  const search = useLocation()
  const [inputTitle, setInputTitle] = useState("")

  const handleLogout = () => {
    dispatch(Logout());
    navigate("/login")
  };

  const handleSubmit = () => {
    navigate("/?title=" + inputTitle)
  }

  return (
    <div className="top">
      <div className="topLeft">
        <i className="topIcon fab fa-facebook-square"></i>
        <i className="topIcon fab fa-instagram-square"></i>
        <i className="topIcon fab fa-pinterest-square"></i>
        <i className="topIcon fab fa-twitter-square"></i>
      </div>
      <div className="topCenter">
        <ul className="topList">
          <li className="topListItem">
            <Link className="link" to="/">
              HOME
            </Link>
          </li>
          <li className="topListItem">
            <Link className="link" to="/about">
              ABOUT
            </Link>
          </li>
          <li className="topListItem">CONTACT</li>
          <li className="topListItem">
            <Link className="link" to="/write">
              WRITE
            </Link>
          </li>
          <li className="topListItem" onClick={handleLogout}>
            LOGOUT
          </li>
        </ul>
      </div>
      <div className="topRight">
        <Link className="link" to="/settings">
          <img
            className="topImg"
            src={user ? PF + user.id + "/" + user.profilePic : ""}
            alt=""
          />
        </Link>
        {search.pathname === "/" && (
          <>
            <i
              className="topSearchIcon fas fa-search"
              onClick={handleSubmit}
            ></i>
            <input
              type="text"
              className="searchInput"
              value={inputTitle}
              onChange={(e) => setInputTitle(e.target.value)}
              placeholder="Search post here..."
              onKeyDown={(e) => {
                if (e.key === "Enter") {
                  handleSubmit();
                }
              }}
            />
          </>
        )}
      </div>
    </div>
  );
}