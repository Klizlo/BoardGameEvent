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
import LoginPage from "./Account/LogIn";
import RegisterPage from "./Account/Register";
import ProducerList from "./Producer/ProducerList";
import Producer from "./Producer/Producer";
import EditProducer from "./Producer/EditProducer";
import AddProducer from "./Producer/AddProducer";

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
            name: "Producers",
            link: "/producers",
            role: "none",
        },
    ];

    return(
        <Router>
            <NavBar sites={sites}/>
            <Routes>
                <Route path='/boardGames' element={<BoardGameList />}/>
                <Route path='/boardGames/add' element={<Guard component={<BoardGameList/>} roles={[Role.Admin]} />} />
                <Route path="/boardGames/{id}" element={<BoardGameList/>} />
                <Route path="/createEvent" element={<BoardGameList/>} />
                <Route path="/eventList" element={<EventList/>} />
                <Route path='/producers' element={<ProducerList />} />
                <Route path='/producers/:id' element={<Producer />} />
                <Route path='/producers/:id/edit' element={<Guard component={<EditProducer />} roles={[Role.Admin]}/>} />
                <Route path='/producers/add' element={<Guard component={<AddProducer />} roles={[Role.Admin]}/>} />
                <Route path='/login' element={<LoginPage />}/>
                <Route path='/register' element={<RegisterPage />} />
            </Routes>
        </Router>
    )
}

export default WebPages;