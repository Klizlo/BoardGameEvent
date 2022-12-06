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
import BoardGame from "./BoardGame/BoardGame";
import EditBoardGame from "./BoardGame/EditBoardGame";
import AddBoardGame from "./BoardGame/AddBoardGame";
import BoardGameCategoryList from "./BoardGameCategory/BoardGameCategoryList";
import BoardGameCategory from "./BoardGameCategory/BoardGameCategory";
import EditBoardGameCategory from "./BoardGameCategory/EditBoardGameCategory";
import AddBoardGameCategory from "./BoardGameCategory/AddBoardGameCategory";
import Profile from "./Account/Profile";

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
        {
            name: "BoardGameCategory",
            link: "/boardGamesCategories",
            role: "none",
        },
    ];

    return(
        <Router>
            <NavBar sites={sites}/>
            <Routes>
                <Route path='/profile' element={<Guard component={<Profile/>} roles={[Role.User]}/>} />
                <Route path='/boardGames' element={<BoardGameList />}/>
                <Route path="/boardGames/:id" element={<BoardGame/>} />
                <Route path='/boardGames/:id/edit' element={<Guard component={<EditBoardGame/>} roles={[Role.Admin]}/>} />
                <Route path='/boardGames/add' element={<Guard component={<AddBoardGame/>} roles={[Role.Admin]} />} />
                <Route path="/createEvent" element={<BoardGameList/>} />
                <Route path="/eventList" element={<EventList/>} />
                <Route path='/producers' element={<ProducerList />} />
                <Route path='/producers/:id' element={<Producer />} />
                <Route path='/producers/:id/edit' element={<Guard component={<EditProducer />} roles={[Role.Admin]}/>} />
                <Route path='/producers/add' element={<Guard component={<AddProducer />} roles={[Role.Admin]}/>} />
                <Route path='/boardGamesCategories' element={<BoardGameCategoryList />} />
                <Route path='/boardGamesCategories/:id' element={<BoardGameCategory />} />
                <Route path='/boardGamesCategories/:id/edit' element={<Guard component={<EditBoardGameCategory />} roles={[Role.Admin]}/>} />
                <Route path='/boardGamesCategories/add' element={<Guard component={<AddBoardGameCategory />} roles={[Role.Admin]}/>} />
                <Route path='/login' element={<LoginPage />}/>
                <Route path='/register' element={<RegisterPage />} />
            </Routes>
        </Router>
    )
}

export default WebPages;