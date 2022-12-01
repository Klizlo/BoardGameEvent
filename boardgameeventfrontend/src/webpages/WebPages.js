import {
    BrowserRouter as Router,
    Routes,
    Route
} from "react-router-dom";
import NavBar from "../components/navBar";
import BoardGames from "./BoardGames";

const WebPages = () => {
    const sites = [
        {
            name: "Board Games List",
            link: "/boardGames",
            visible: true,
        },
        {
            name: "Board Games Rooms",
            link: "/rooms",
            visible: true,
        },
        {
            name: "Create Room",
            link: "/createRoom",
            visible: false,
        },
        {
            name: "Administration",
            link: "/admin",
            visible: false,
        },
    ];

    return(
        <Router>
            <NavBar sites={sites}/>
            <Routes>
                <Route
                    path="/boardGames"
                    element={<BoardGames/>}
                />
            </Routes>
        </Router>
    )
}

export default WebPages;