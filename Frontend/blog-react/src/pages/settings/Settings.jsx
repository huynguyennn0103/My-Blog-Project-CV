import "./settings.css";
import Sidebar from "../../components/sidebar/Sidebar";
import { useContext, useState } from "react";
import { Context } from "../../context/Context";
import { request } from "../../utils/http";
import { Logout, UpdateFailure, UpdateStart, UpdateSuccess } from "../../context/Actions";
import { ToastContainer, toast } from "react-toastify";
export default function Settings() {  
  const { user, dispatch } = useContext(Context);
  const [file, setFile] = useState(null);
  const [username, setUsername] = useState(user.username);
  const [email, setEmail] = useState(user.email);
  const [password, setPassword] = useState(user.password);
  const [success, setSuccess] = useState(false);

  const PF = process.env.REACT_APP_backend_URL + "public/avas/"

  const handleSubmit = async (e) => {
    e.preventDefault();
    dispatch(UpdateStart());
    const updatedUser = {
      id: user.id,
      username,
      email,
      password,
      profilePic: user.profilePic
    };
    if (file) {
      const data = new FormData();
      // const filename = Date.now() + file.name;
      data.append("id", user.id);
      data.append("file", file )
      const res = await request("POST", "user/upload", data, true);
      if(res.data.statusCode === 500){
        toast.error(res.data.message)
      }
      else if( res.data.statusCode === 403){
        dispatch(Logout())
      }
    }

    const res = await request("PUT","/user/" + user.id, updatedUser, true);
    if(res.data.statusCode === 500){
      dispatch(UpdateFailure());
      toast.error(res.data.message)
    }
    else if (res.data.statusCode === 403){
      dispatch(UpdateFailure());
      dispatch(Logout())
    }
    else {
      setSuccess(true);
      dispatch(UpdateSuccess(res.data.data));
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
      <div className="settings">
        <div className="settingsWrapper">
          <div className="settingsTitle">
            <span className="settingsUpdateTitle">Update Your Account</span>
          </div>
          <form className="settingsForm" onSubmit={handleSubmit}>
            <label>Profile Picture</label>
            <div className="settingsPP">
              <img
                src={
                  file
                    ? URL.createObjectURL(file)
                    : PF + user.id + "/" + user.profilePic
                }
                alt=""
              />
              <label htmlFor="fileInput">
                <i className="settingsPPIcon far fa-user-circle"></i>
              </label>
              <input
                type="file"
                id="fileInput"
                style={{ display: "none" }}
                onChange={(e) => setFile(e.target.files[0])}
              />
            </div>
            <label>Username</label>
            <input
              type="text"
              placeholder={user.username}
              onChange={(e) => setUsername(e.target.value)}
              value={username}
            />
            <label>Email</label>
            <input
              type="email"
              placeholder={user.email}
              onChange={(e) => setEmail(e.target.value)}
              value={email}
            />
            <label>Password</label>
            <input
              type="password"
              onChange={(e) => setPassword(e.target.value)}
              value={password}
            />
            <button className="settingsSubmit" type="submit">
              Update
            </button>
            {success && (
              <span
                style={{
                  color: "green",
                  textAlign: "center",
                  marginTop: "20px",
                }}
              >
                Profile has been updated...
              </span>
            )}
          </form>
        </div>
        <Sidebar />
      </div>
    </>
  );
}