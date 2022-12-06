import { Alert, Box, Snackbar, Typography } from "@mui/material";
import { useEffect, useState } from "react";
import { useParams } from "react-router";
import EditEventForm from "../../components/Edits/EditEventForm";
import Variables from "../../components/Globals/Variables";
import { authHeader } from "../../helpers/auth-header";
import { Role } from "../../helpers/role";
import { authenticationService } from "../../service/authenticateService";

export default function EditEvent() {

    const { id } = useParams();
    const currentUser = authenticationService.currentUserValue;

    const [openAlert, setOpenAlert] = useState(false);
    const [error, setError] = useState("");

    const [data, setData] = useState(null);

    useEffect(() => {
        fetch(`${Variables.API}/events/` + id, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
                'Authorization': authHeader().Authorization
            },
        })
        .then((response) => response.json())
        .then((result) => {
            if(result.msg){
                setOpenAlert(true);
                setError(result.msg);
            } else if(result.status === 401) {
                window.location = '/login';
            } else {
                if(result.organizer.id === currentUser.user.id || currentUser.user.roles.map(role => role.name).includes(Role.Admin)){
                    setData(result);
                } else {
                    window.location = '/events';
                }
            }
        })
    }, []);

    const handleAlert = (e) => {
        setOpenAlert(false);
    };

    
    return (
        <Box
        sx={{
            margin: '2%'
        }}>
            <Typography
                variant="h5"
                sx={{
                    fontFamily: 'kdam-thmor-pro',
                    fontWeight: 700,
                    letterSpacing: '.3rem',
                    color: 'white'
                }}
            >
                Edit Event
            </Typography>
            {data != null  ? <EditEventForm event={data} /> : <Typography >Fetching...</Typography>}
            <Snackbar
                anchorOrigin={{ vertical: 'top', horizontal: 'center' }}
                open={openAlert}
                onClose={handleAlert}
                autoHideDuration={6000}
            >
                <Alert severity="error" sx={{ width: '100%' }}>
                    {error}
                </Alert>
            </Snackbar>
        </Box>
    );
}