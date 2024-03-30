import { redirect } from "react-router-dom";
const getTokenDuration = () => {
    const storedExpirationDate = localStorage.getItem('expiration');
    if(!storedExpirationDate){
        return null
    }
    const expirationDate = new Date(storedExpirationDate);
    const now = new Date();
    const duration = expirationDate.getTime() - now.getTime();
    return duration;
}

export async function checkAuthLoader(){
    const token = localStorage.getItem('token');
    const duration = getTokenDuration();
    console.log("Remaining duration in checkauth: ", duration)
    if(token === "null" || !token || !duration || duration <= 0){
        localStorage.removeItem('user')
        localStorage.removeItem('token')
        localStorage.removeItem('expiration')
        return redirect("/login")
    }
    return null
}