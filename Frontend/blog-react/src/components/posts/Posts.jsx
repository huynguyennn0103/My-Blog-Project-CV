import Post from "../post/Post";
import "./posts.css";

export default function Posts({ posts }) {
  return (
    <div className="posts">
      {posts && posts.length > 0 && posts.map((p) => (
        <Post key={p.id} post={p} />
      ))}
    </div>
  );
}
