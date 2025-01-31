import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import "./sidebar.css";
import { request } from "../../utils/http";
import { useContext } from "react";
import { Context } from "../../context/Context";
import { Logout } from "../../context/Actions";
export default function Sidebar() {
  const [cats, setCats] = useState([]);
  const {dispatch} = useContext(Context);
  useEffect(() => {
    const getCats = async () => {
      const res = await request("GET", "categories", null, true);
      if(res.data.statusCode === 403){
        dispatch(Logout())
      } else {
        setCats(res.data.data);
      }
    };
    getCats();
  }, [dispatch]);
  return (
    <div className="sidebar">
      <div className="sidebarItem">
        <span className="sidebarTitle">ABOUT ME</span>
        <img
          src="https://i.pinimg.com/236x/1e/3f/58/1e3f587572a7a7b20bbf1828595a1786--holiday-party-themes-holiday-gift-guide.jpg"
          alt=""
        />
        <p>
          Lorem ipsum dolor sit amet consectetur adipisicing elit. Voluptate qui
          necessitatibus nostrum illum reprehenderit.
        </p>
      </div>
      <div className="sidebarItem">
        <span className="sidebarTitle">CATEGORIES</span>
        <ul className="sidebarList">
          {cats && cats.length > 0 && cats.map((c) => (
            <Link to={`/?cat=${c.name}`} className="link" key={c.id}>
            <li className="sidebarListItem">{c.name}</li>
            </Link>
          ))}
        </ul>
      </div>
      <div className="sidebarItem">
        <span className="sidebarTitle">FOLLOW US</span>
        <div className="sidebarSocial">
          <i className="sidebarIcon fab fa-facebook-square"></i>
          <i className="sidebarIcon fab fa-twitter-square"></i>
          <i className="sidebarIcon fab fa-pinterest-square"></i>
          <i className="sidebarIcon fab fa-instagram-square"></i>
        </div>
      </div>
    </div>
  );
}