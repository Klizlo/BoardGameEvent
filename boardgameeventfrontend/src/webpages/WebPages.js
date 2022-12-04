import {
    BrowserRouter as Router,
    Routes,
    Route
} from "react-router-dom";
import NavBar from "../components/Navigation/NavBar";
import BoardGameList from "./BoardGame/BoardGameList";
import EventList from "./Event/EventList";

const WebPages = () => {
    const sites = [
        {
            name: "Board Games List",
            link: "/boardGames",
            visible: true,
        },
        {
            name: "Event list",
            link: "/eventList",
            visible: true,
        },
        {
            name: "Create Room",
            link: "/createEvent",
            visible: false,
        },
    ];

    return(
        <Router>
            <NavBar sites={sites}/>
            <Routes>
                <Route
                    path="/boardGames"
                    element={<BoardGameList/>}
                />
                <Route
                    path="/boardGames/{id}"
                    element={<BoardGameList/>}
                />
                <Route
                    path="/createEvent"
                    element={<BoardGameList/>}
                />
                <Route
                    path="/eventList"
                    element={<EventList/>}
                />
            </Routes>
        </Router>
    )
}

export default WebPages;