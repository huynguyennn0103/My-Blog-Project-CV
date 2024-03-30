import { useContext, useState } from "react";
import "./write.css";
import { request } from "../../utils/http";
import { Context } from "../../context/Context";
import { useNavigate } from "react-router-dom";
import { Logout } from "../../context/Actions";
import { ToastContainer, toast } from "react-toastify";
export default function Write() {
  const [title, setTitle] = useState("");
  const [desc, setDesc] = useState("");
  const [file, setFile] = useState(null);
  const [isFetching, setIsFetching] = useState(false);
  const { user, dispatch } = useContext(Context);
  const navigate = useNavigate();
  const handleSubmit = async (e) => {
    e.preventDefault();
    const newPost = {
      author_id: user.id,
      title,
      description: desc,
      categories: "[1,2]"
    };
    setIsFetching(true)
    if (file) {
      const data =new FormData();
      data.append("author_id", user.id);
      data.append("file", file);
      const res = await request("POST", "post/upload", data, true);
      if(res.data.statusCode === 403){
        setIsFetching(false)
        dispatch(Logout())
        return;
      }
      else if(res.data.statusCode === 500){
        toast.error(res.data.message)
        setIsFetching(false)
        return;
      }
      newPost.photo = res.data.data
    }

    const res = await request("POST", "/post", newPost, true);
    if(res.data.statusCode === 403){
      setIsFetching(false)
      dispatch(Logout())
      return;
    }
    else if(res.data.statusCode === 500){
      setIsFetching(false);
      if (res.data.message === "Validation error") {
        res.data.data.map((element) => toast.error(element));
        const formData = new FormData();
        formData.append("author_id", user.id)
        formData.append("img_name", newPost.photo)
        const resDelImg = await request("DELETE", "post/upload", formData, true);
        if(resDelImg.data.statusCode === 200){
          setFile(null);
          toast.info("Reset image of post success")
        }
      } else {
        toast.error(res.data.message);
      }
      return;
    }
    setIsFetching(false)
    navigate("/post/" + res.data.data.id);
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
      <div className="write">
        {file && (
          <img className="writeImg" src={URL.createObjectURL(file)} alt="" />
        )}
        <form className="writeForm" onSubmit={handleSubmit}>
          <div className="writeFormGroup">
            <label htmlFor="fileInput">
              <i className="writeIcon fas fa-plus"></i>
            </label>
            <input
              type="file"
              id="fileInput"
              style={{ display: "none" }}
              onChange={(e) => setFile(e.target.files[0])}
            />
            <input
              type="text"
              placeholder="Title"
              className="writeInput"
              autoFocus={true}
              onChange={(e) => setTitle(e.target.value)}
            />
          </div>
          <div className="writeFormGroup">
            <textarea
              placeholder="Tell your story..."
              type="text"
              className="writeInput writeText"
              onChange={(e) => setDesc(e.target.value)}
            ></textarea>
          </div>
          <button className="writeSubmit" type="submit" disabled={isFetching}>
            Publish
          </button>
        </form>
      </div>
    </>
  );
}