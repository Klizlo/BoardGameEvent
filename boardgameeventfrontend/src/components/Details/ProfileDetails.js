import {useState} from "react";
import {Box} from "@mui/system";
import {
    Alert,
    Button,
    Dialog,
    DialogActions,
    DialogTitle,
    Divider,
    Grid,
    Snackbar,
    TextField,
    Typography
} from "@mui/material";
import {Role} from "../../helpers/role";
import {Cancel, Delete, Edit} from "@mui/icons-material";
import {authenticationService} from "../../service/authenticateService";
import EventList from "../../webpages/Event/EventList";

export default function ProfileDetails(events) {

    const eventData = events.events;

    const currentUser = authenticationService.currentUserValue;

    console.log(eventData);

    console.log(currentUser)

    return (
        <Grid
            marginLeft={"auto"}
            marginRight={"auto"}
            p={2}
            container
            direction={"column"}
            justifyContent={"space-between"}
            alignSelf={"center"}
            width={'100%'}
        >
            <Grid
                marginLeft={"auto"}
                marginRight={"auto"}
                p={1}
                m={2}
                container
                direction={"column"}
                justifyContent={"space-between"}
                alignSelf={"center"}
                width={'100%'}
                border={2}
                borderRadius={5}
                borderColor={"action.hover"}
            >
                <Box sx={{pr:2, pt:1, pb:1, pl:2}}>
                    <Typography
                        variant="h5"
                        sx={{
                            fontFamily: 'kdam-thmor-pro',
                            fontWeight: 700,
                            letterSpacing: '.3rem',
                            color: 'white'
                        }}
                    >
                        About you:
                    </Typography>
                    <Divider/>
                    <Grid
                        marginLeft={"auto"}
                        marginRight={"auto"}
                        pt={2}
                        pb={2}
                        container
                        direction={"column"}
                        justifyContent={"space-between"}
                        alignSelf={"center"}
                    >
                        <Typography
                            variant="h6"
                            sx={{
                                fontFamily: 'kdam-thmor-pro',
                                fontWeight: 700,
                                letterSpacing: '.3rem',
                                color: 'white'
                            }}
                        >
                            UserName - {currentUser.user.username}
                        </Typography>
                        <Typography
                            variant="h6"
                            sx={{
                                fontFamily: 'kdam-thmor-pro',
                                fontWeight: 700,
                                letterSpacing: '.3rem',
                                color: 'white'
                            }}
                        >
                            Email - {currentUser.user.email}
                        </Typography>
                        <Typography
                            variant="h6"
                            sx={{
                                fontFamily: 'kdam-thmor-pro',
                                fontWeight: 700,
                                letterSpacing: '.3rem',
                                color: 'white'
                            }}
                        >
                            Role - {currentUser.user.roles[0].name}
                        </Typography>
                    </Grid>
                </Box>
            </Grid>
            <Grid
                marginLeft={"auto"}
                marginRight={"auto"}
                p={1}
                m={2}
                container
                direction={"column"}
                justifyContent={"space-between"}
                alignSelf={"center"}
                width={'100%'}
                border={2}
                borderRadius={5}
                borderColor={"action.hover"}
            >
                <Box sx={{pr:2, pt:1, pb:1, pl:2}}>
                    <Typography
                        variant="h5"
                        sx={{
                            fontFamily: 'kdam-thmor-pro',
                            fontWeight: 700,
                            letterSpacing: '.3rem',
                            color: 'white'
                        }}
                    >
                       Your Events:
                    </Typography>
                    <Divider/>
                    <EventListTable eventData={eventData}/>
                </Box>
            </Grid>
        </Grid>
    );
}