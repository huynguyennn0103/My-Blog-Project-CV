import { request } from "../../utils/http";
import { useContext, useEffect, useState } from "react";
import { useLocation } from "react-router";
import { Link } from "react-router-dom";
import { Context } from "../../context/Context";
import "./singlePost.css";
import { useNavigate } from "react-router-dom";
import { Logout } from "../../context/Actions";
import { ToastContainer, toast } from "react-toastify";
export default function SinglePost() {
  const location = useLocation();
  const navigate = useNavigate();
  const path = location.pathname.split("/")[2];
  const PF = process.env.REACT_APP_backend_URL + "public/posts/";
  const { user, dispatch } = useContext(Context);

  const [post, setPost] = useState({});
  const [title, setTitle] = useState("");
  const [desc, setDesc] = useState("");
  const [updateMode, setUpdateMode] = useState(false);

  useEffect(() => {
    const getPost = async () => {
      const res = await request("GET", "post/" + path, null, true);;
      if(res.data.statusCode === 403){
        dispatch(Logout())
      }
      else {
        setPost(res.data.data);
        setTitle(res.data.data.title);
        setDesc(res.data.data.description);
      }
    };
    getPost();
  }, [path, dispatch]);

  const handleDelete = async () => {
    try {
      const res = await request("DELETE",`post/${post.id}`, {
        author_id: user.id
      }, true);
      if(res.data.statusCode === 403){
        dispatch(Logout())
        return;
      }
      else if (res.data.statusCode === 500){
        toast.error(res.data.message)
      }
      navigate("/");
    } catch (err) {}
  };

  const handleUpdate = async () => {
    let categories = [];
    post.categories.map((element) => categories.push(element.id))
    try {
      const res = await request("PUT",`/post/${post.id}`, {
        author_id: user.id,
        title,
        description: desc,
        photo: post.photo,
        categories: categories.toString()
      }, true);
      if(res.data.statusCode === 403){
        dispatch(Logout())
      }
      else if (res.data.statusCode === 500){
        toast.error(res.data.message)
      }
      setUpdateMode(false)
    } catch (err) {}
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
      <div className="singlePost">
        <div className="singlePostWrapper">
          {post.photo && (
            <img
              src={PF + post.user?.id + "/" + post.photo}
              alt=""
              className="singlePostImg"
            />
          )}
          {updateMode ? (
            <input
              type="text"
              value={title}
              className="singlePostTitleInput"
              autoFocus
              onChange={(e) => setTitle(e.target.value)}
            />
          ) : (
            <h1 className="singlePostTitle">
              {title}
              {post.user?.username === user?.username && (
                <div className="singlePostEdit">
                  <i
                    className="singlePostIcon far fa-edit"
                    onClick={() => setUpdateMode(true)}
                  ></i>
                  <i
                    className="singlePostIcon far fa-trash-alt"
                    onClick={handleDelete}
                  ></i>
                </div>
              )}
            </h1>
          )}
          <div className="singlePostInfo">
            <span className="singlePostAuthor">
              Author:
              <Link to={`/?user=${post.user?.username}`} className="link">
                <b> {post.user?.username}</b>
              </Link>
            </span>
            <span className="singlePostDate">
              {new Date(post.createdAt).toDateString()}
            </span>
          </div>
          {updateMode ? (
            <textarea
              className="singlePostDescInput"
              value={desc}
              onChange={(e) => setDesc(e.target.value)}
            />
          ) : (
            <p className="singlePostDesc">{desc}</p>
          )}
          {updateMode && (
            <button className="singlePostButton" onClick={handleUpdate}>
              Update
            </button>
          )}
        </div>
      </div>
    </>
  );
}