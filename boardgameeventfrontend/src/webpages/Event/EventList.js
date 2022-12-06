import {useEffect, useState} from "react";
import Variables from "../../components/Globals/Variables";
import Box from "@mui/material/Box";
import {Button, Grid, Typography} from "@mui/material";
import EventListTable from "../../components/Tables/EventListTable";
import { Role } from "../../helpers/role";
import { authenticationService } from "../../service/authenticateService";

const EventList = () => {
    const currentUser = authenticationService.currentUserValue;

    const [error, setError] = useState(null);
    const [isLoaded, setIsLoaded] = useState(false);
    const [events, setEvents] = useState([]);
    const endpoint = Variables.API + '/events';
    useEffect(() => {
        fetch(endpoint, {
            method: 'GET',
        })
            .then(res => res.json())
            .then(
                (data) => {
                    setIsLoaded(true);
                    setEvents(data);
                },
                (error) => {
                    setIsLoaded(true);
                    setError(error);
                    console.log(error);
                }
            )
    }, []);

    if (error){
        return <div>ERROR: {error.message}</div>;
    } else if (!isLoaded){
        return <div>Fetching Data...</div>;
    } else {
        return (
            <Box
                sx={{
                    width: 1,
                    bgcolor: "background.default",
                }}
            >
                <Grid
                    marginLeft={"auto"}
                    marginRight={"auto"}
                    p={2}
                    container
                    direction={"column"}
                    justifyContent={"space-between"}
                    alignSelf={"center"}
                    alignItems={"center"}
                    width={'100%'}
                >
                    <Typography sx={{fontSize: 35, fontWeight: 'bold'}} color={"text.secondary"} gutterBottom>
                       Events
                    </Typography>
                    <EventListTable eventData={events}/>
                </Grid>
            </Box>
        );
    }
}

export default EventList;