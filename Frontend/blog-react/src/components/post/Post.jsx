import "./post.css";
import { Link } from "react-router-dom";

export default function Post({ post }) {
  const PF = process.env.REACT_APP_backend_URL + "public/posts/";
  return (
    <div className="post">
      {post.photo && <img className="postImg" src={PF + post.user?.id + '/' + post.photo} alt="" />}
      <div className="postInfo">
        <div className="postCats">
          {post.categories.map((c) => (
            <span key={Math.random()} className="postCat">{c.name}</span>
          ))}
        </div>
        <Link to={`/post/${post.id}`} className="link">
          <span className="postTitle">{post.title}</span>
        </Link>
        <hr />
        <span className="postDate">
          {new Date(post.createdAt).toDateString()}
        </span>
      </div>
      <p className="postDesc">{post.description}</p>
    </div>
  );
}