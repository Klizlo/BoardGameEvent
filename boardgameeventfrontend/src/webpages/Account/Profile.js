import {useEffect, useState} from "react";
import Variables from "../../components/Globals/Variables";
import {authenticationService} from "../../service/authenticateService";
import {authHeader} from "../../helpers/auth-header";
import {Alert, Grid, Snackbar, Typography} from "@mui/material";
import {Box} from "@mui/system";
import ProfileDetails from "../../components/Details/ProfileDetails";

export default function Profile() {

    const [error, setError] = useState('');
    const [openAlert, setOpenAlert] = useState(false);
    const [eventsData, setEventsData] = useState([]);
    const currentUser = authenticationService.currentUserValue;
    const unauthorized = ['unauthorized', 'token_invalid', 'token_absent', 'token_expired', 'user_not_found'];
    useEffect(() => {
        if(!currentUser){
            window.location = '/login';
        }
        fetch(Variables.API + '/users/' + currentUser.user.id + '/events', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
                'Authorization': authHeader().Authorization
            },
        })
            .then((response) => response.json())
            .then((result) => {
                console.log(result);
                if (result.msg) {
                    setOpenAlert(true);
                    setError(result.msg);
                } else if (unauthorized.includes(result.message)) {
                    window.location = '/';
                } else {
                    setEventsData(result);
                }
            })
    }, []);

    console.log(eventsData)

    const handleAlert = (e) => {
        setOpenAlert(false);
    };

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
                <Typography
                    variant="h4"
                    sx={{
                        fontFamily: 'kdam-thmor-pro',
                        fontWeight: 700,
                        letterSpacing: '.3rem',
                        color: "white"
                    }}
                >
                    Hello {currentUser.user.username}
                </Typography>
                {eventsData != null ? (<ProfileDetails events={eventsData}/>) : (<Typography>Fetching Data...</Typography>)}
            </Grid>
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
    )
}