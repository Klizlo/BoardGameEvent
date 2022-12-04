import {
    BrowserRouter as Router,
    Routes,
    Route
} from "react-router-dom";
import { Guard } from "../components/Guard";
import { Role } from "../helpers/role";
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
                <Route path='/boardGames' element={<Guard component={<BoardGameList/>} roles={[Role.Admin]} />}/>
                <Route path='/login' />
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