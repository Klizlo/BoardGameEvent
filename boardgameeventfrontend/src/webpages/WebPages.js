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
import LogIn from "./Account/LogIn";

const WebPages = () => {
    const sites = [
        {
            name: "Board Games List",
            link: "/boardGames",
            role: "none"
        },
        {
            name: "Event list",
            link: "/eventList",
            role: "none"
        },
        {
            name: "Create Room",
            link: "/createEvent",
            role: Role.User,
        },
    ];

    return(
        <Router>
            <NavBar sites={sites}/>
            <Routes>
                <Route path='/boardGames' element={<BoardGameList />}/>
                <Route path='/login' element={<LogIn />}/>
                <Route path='/boardGames/add' element={<Guard component={<BoardGameList/>} roles={[Role.Admin]} />} />
                <Route path="/boardGames/{id}" element={<BoardGameList/>} />
                <Route path="/createEvent" element={<BoardGameList/>} />
                <Route path="/eventList" element={<EventList/>} />
            </Routes>
        </Router>
    )
}

export default WebPages;