import Homepage from "./pages/homepage/Homepage";
import Single from "./pages/single/Single";
import Write from "./pages/write/Write";
import Settings from "./pages/settings/Settings";
import Login from "./pages/login/Login";
import Register from "./pages/register/Register";
import { RouterProvider, createBrowserRouter } from "react-router-dom";
import RootLayout from "./pages/root/RootLayout";
import { checkAuthLoader } from "./utils/auth";
import 'react-toastify/dist/ReactToastify.css';
import About from "./pages/about/About";
const router = createBrowserRouter([
  {
    path: "/",
    element: <RootLayout />,
    children: [
      { index: true, element: <Homepage /> , loader: checkAuthLoader},
      { path: "posts", element: <Homepage />, loader: checkAuthLoader },

      { path: "post/:id", element: <Single />, loader: checkAuthLoader },
      { path: "write", element: <Write />, loader: checkAuthLoader },
      { path: "settings", element: <Settings />, loader: checkAuthLoader },
      { path: "about", element: <About/>, loader: checkAuthLoader}
    ],
  },
  {
    path: "/login",
    element: <Login />,
  },
  { 
    path: "/register",
    element: <Register /> 
  },
]);
function App() {
  return <RouterProvider router={router}/>;
}

export default App;
