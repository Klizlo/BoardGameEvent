import { Add, Cancel, Delete, Edit } from "@mui/icons-material";
import { Alert, Button, Dialog, DialogActions, DialogTitle, Grid, Snackbar, TextField, Typography } from "@mui/material";
import { Box } from "@mui/system";
import { DateTimePicker, LocalizationProvider } from "@mui/x-date-pickers";
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { useState } from "react";
import { authHeader } from "../../helpers/auth-header";
import { Role } from "../../helpers/role";
import { authenticationService } from "../../service/authenticateService";
import { eventService } from "../../service/eventService";
import Variables from "../Globals/Variables";
import PlayersTable from "../Tables/PlayersTable";

export default function EventDetails(event) {
    const currentUser = authenticationService.currentUserValue;
    const [data, setData] = useState(event.event);

    const [open, setOpen] = useState(false);
    const [error, setError] = useState("");
    const [openAlert, setOpenAlert] = useState(false);

    const handleAlertClose = () => {
        setOpenAlert(false);
    }

    console.log(data);

    const handleClick = () => {
        setOpen(false);
        fetch(`${Variables.API}/events/` + data.id, {
            method: "DELETE",
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
                'Authorization': authHeader().Authorization
            },
        }).then((response) => response.json())
        .then((result) => {
            console.log(result);
            if (result.msg){
                setOpenAlert(true);
                setError(result.message);
            }
        });
        if(!openAlert){
            window.location = '/events';
        }
    }

    return (<Box>
        <Grid container spacing={{ xs: 2, md: 3 }}>
            <Grid item xs={12} sm={12} md={6} >
                <TextField
                    margin="normal"
                    InputProps={{
                        readOnly: true
                    }}
                    id="name"
                    fullWidth
                    label="Event name"
                    name="name"
                    onChange={(e) => {}}
                    value={data.name}
                />
            </Grid>
            <Grid item xs={12} sm={12} md={6} >
                <TextField
                    margin="normal"
                    InputProps={{
                        readOnly: true
                    }}
                    multiline
                    rows={4}
                    id="description"
                    fullWidth
                    label="Description"
                    name="description"
                    onChange={(e) => {}}
                    value={data.description}
                />
            </Grid>
            <Grid item xs={12} sm={12} md={6} >
                <TextField
                    margin="normal"
                    InputProps={{
                        readOnly: true
                    }}
                    type="number"
                    id="numberOfPlayers"
                    fullWidth
                    label="Number of wanted players"
                    name="numberOfPlayers"
                    onChange={(e) => {}}
                    value={data.numberOfPlayers}
                />
            </Grid>
            <Grid item xs={12} sm={12} md={6} >
                <LocalizationProvider dateAdapter={AdapterDayjs}>
                    <DateTimePicker
                        readOnly
                        id="date"
                        fullWidth
                        label="Date"
                        name="date"
                        onChange={(e) => {}}
                        value={data.date}
                        inputFormat="DD-MM-YYYY HH:mm"
                        renderInput={(params) => <TextField {...params} />}
                    />
                </LocalizationProvider>
            </Grid>
            <Grid item xs={12} sm={12} md={6} >
                <TextField
                    margin="normal"
                    InputProps={{
                        readOnly: true
                    }}
                    id="boardGame"
                    fullWidth
                    label="Board Game"
                    name="boardGame"
                    onChange={(e) => {}}
                    value={data.boardGame.name}
                />
            </Grid>
            <Grid item xs={12} sm={12} md={6} >
                <TextField
                    margin="normal"
                    InputProps={{
                        readOnly: true
                    }}
                    id="organizer"
                    fullWidth
                    label="Organizer"
                    name="organizer"
                    onChange={(e) => {}}
                    value={data.organizer.username}
                />
            </Grid>
            {
                currentUser && currentUser.user.roles.map(role => role.name).includes(Role.User) ?
                (
                    <Grid item xs={12} sm={12} md={12} >
                        <Typography
                        variant="h6"
                        sx={{
                            fontFamily: 'kdam-thmor-pro',
                            fontWeight: 700,
                            letterSpacing: '.3rem',
                            color: "white",
                            marginBottom: '3%'
                        }}>
                            Joined Players
                        </Typography>
                        <PlayersTable playersData={data.players} />
                    </Grid>
                ) : (
                    <></>
                )
            }
            {
                currentUser && data.players.length < data.numberOfPlayers && currentUser.user.id !== data.organizer.id 
                 && currentUser.user.roles.map(role => role.name).includes(Role.User) &&
                !data.players.map(player => player.id).includes(currentUser.user.id)
                 ? (
                    <Grid item xs={12} sm={12} md={6} >
                        <Button
                            sx={{
                                background: 'green',
                                color: 'white',
                                fontWeight: 700,
                                '&:hover': {
                                    color: 'black'
                                },
                                margin: '2%'
                            }}
                            startIcon={<Add />}
                            onClick={() => {
                                eventService.addPlayer(data.id, currentUser.user)
                                .then((response) => {
                                    window.location.reload(true);
                                })
                                .catch((reject) => setError(reject.msg));
                            }}>
                            Join
                        </Button>
                        </Grid>
                 ) : (
                    <></>
                 )
            }
            {currentUser && (currentUser.user.roles.map((role) => role.name).includes(Role.Admin) || currentUser.user.id === data.organizer.id) ? (
                <Grid item xs={12} sm={12} md={16} >
                <Button
                    sx={{
                        background: 'orange',
                        color: 'white',
                        fontWeight: 700,
                        '&:hover': {
                            color: 'black'
                        },
                        margin: '2%'
                    }}
                    startIcon={<Edit />}
                    onClick={() => {
                        window.location = '/events/' + data.id + '/edit'
                      }}>
                    Update
                </Button>
                <Button
                    sx={{
                        background: 'red',
                        color: 'white',
                        fontWeight: 700,
                        '&:hover': {
                            color: 'black'
                        }
                    }}
                    startIcon={<Delete />}
                    onClick={() => {
                        setOpen(true);
                      }}>
                    Remove
                </Button>
            </Grid>
            ) : (
                <></>
            )}
            
        </Grid>
        <Dialog
            open={open}
            onClose={() => setOpen(false)}
            aria-labelledby="draggable-dialog-title"
        >
            <DialogTitle style={{ cursor: 'move' }} id="draggable-dialog-title">
                Do you want to remove event?
            </DialogTitle>
            <DialogActions>
            <Button startIcon={<Cancel />} autoFocus onClick={() => {setOpen(false)}}>
                Cancel
            </Button>
            <Button sx={{color: 'red'}} startIcon={<Delete />} onClick={handleClick}>Yes</Button>
            </DialogActions>
        </Dialog>
        <Snackbar
            anchorOrigin={{ vertical: 'top', horizontal: 'center' }}
            open={openAlert}
            onClose={handleAlertClose}
            autoHideDuration={6000}
        >
            <Alert severity="error" sx={{ width: '100%' }}>
                {error}
            </Alert>
        </Snackbar>
    </Box>);
}