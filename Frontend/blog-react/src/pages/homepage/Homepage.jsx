import React, { useEffect } from 'react'
import './homepage.css'
import Header from "../../components/header/Header";
import Sidebar from '../../components/sidebar/Sidebar';
import Posts from '../../components/posts/Posts';
import { useLocation } from 'react-router-dom';
import { useState } from 'react';
import { request } from '../../utils/http';
import { useContext } from 'react';
import { Context } from '../../context/Context';
import { Logout } from '../../context/Actions';
import ReactPaginate from 'react-paginate'
export default function Homepage() {
  const [posts, setPosts] = useState([]);
  const [page, setPage] = useState(0);
  const [pageCount, setPageCount] = useState(5);
  const { search } = useLocation();
  const {dispatch} = useContext(Context);

  const handlePageClick = (e) => {
    window.scrollTo({ top: 0, behavior: 'smooth' });
    setPage(e.selected)
  }

  useEffect(() => {
    const fetchPosts = async () => {
      const res = await request("GET", !search?  `post?page=${page}`: "post" + search + `&page=${page}`, null, true);
      if(res.data.statusCode === 403){
        dispatch(Logout())
      }
      else {
        setPosts(res.data.data.content);
        setPageCount(res.data.data.pageCount);
      }
    };
    fetchPosts();
  }, [search, dispatch, page]);
  return (
    <>
      <Header/>
      <div className="home">       
          <Posts posts={posts}/>
          <Sidebar/>        
      </div>
      <ReactPaginate
            breakLabel="..."
            nextLabel="next >"
            onPageChange={handlePageClick}
            pageRangeDisplayed={5}
            pageCount={pageCount}
            previousLabel="< previous"
            renderOnZeroPageCount={null}
            className={'pagination'}
            pageClassName={'page'}
            pageLinkClassName={'link_page'}
            activeClassName={'page_active'}
            previousClassName={'previous'}
            nextClassName={'next'}
       />
    </>
  )
}
