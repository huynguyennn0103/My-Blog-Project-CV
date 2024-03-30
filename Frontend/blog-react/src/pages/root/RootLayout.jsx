import { Outlet } from "react-router-dom";
import Topbar from "../../components/topbar/Topbar";
export default function RootLayout() {
  return (
    <>
        <Topbar/>
        <Outlet/>
    </>
  )
}
