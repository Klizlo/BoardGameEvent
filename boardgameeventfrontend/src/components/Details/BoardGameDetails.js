import { Cancel, Delete, Edit } from "@mui/icons-material";
import { Alert, Button, Dialog, DialogActions, DialogTitle, Grid, Snackbar, TextField, Typography } from "@mui/material";
import { Box } from "@mui/system";
import { useState } from "react";
import { authHeader } from "../../helpers/auth-header";
import { Role } from "../../helpers/role";
import { authenticationService } from "../../service/authenticateService";
import Variables from "../Globals/Variables";

export default function BoardGameDetails(boardGame) {
    const currentUser = authenticationService.currentUserValue;
    const [data, setData] = useState(boardGame.boardGame);

    const [open, setOpen] = useState(false);
    const [error, setError] = useState("");
    const [openAlert, setOpenAlert] = useState(false);

    const handleAlertClose = () => {
        setOpenAlert(false);
    }

    const handleClick = () => {
        setOpen(false);
        fetch(`${Variables.API}/boardGames/` + data.id, {
            method: "DELETE",
            headers: authHeader,
        }).then((response) => response.json())
            .then((result) => {
                console.log(result);
                if (result.msg){
                    setOpenAlert(true);
                    setError(result.message);
                }else if (result.status === 401){
                    window.location = '/login';
                }
            });
        if(!openAlert){
            window.location = '/boardGames';
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
                    label="Board Game Name"
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
                    id="numberOfPlayers"
                    fullWidth
                    label="Number of Players"
                    name="numberOfPlayers"
                    onChange={(e) => {}}
                    value= {data.minNumberOfPlayers + " - "  + data.maxNumberOfPlayers}
                />
            </Grid>
            <Grid item xs={12} sm={12} md={6} >
                <TextField
                    margin="normal"
                    InputProps={{
                        readOnly: true
                    }}
                    id="producerName"
                    fullWidth
                    label="Producer Name"
                    name="producerName"
                    onChange={(e) => {}}
                    value={data.producer.name}
                />
            </Grid>
            <Grid item xs={12} sm={12} md={6} >
                <TextField
                    margin="normal"
                    InputProps={{
                        readOnly: true
                    }}
                    id="tag"
                    fullWidth
                    label="Tag"
                    name="tag"
                    onChange={(e) => {}}
                    value={data.boardGameCategory.name}
                />
            </Grid>
            <Grid item xs={12} sm={12} md={6} >
                <TextField
                    margin="normal"
                    InputProps={{
                        readOnly: true
                    }}
                    id="age"
                    fullWidth
                    label="Age Restriction"
                    name="age"
                    onChange={(e) => {}}
                    value={data.ageRestriction}
                />
            </Grid>
            {currentUser && currentUser.user.roles.map((role) => role.name).includes(Role.Admin) ? (
                <Grid item xs={12} sm={12} md={12} >
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
                            window.location = '/boardGames/' + data.id + '/edit'
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
                Do you want to remove producer from the list?
            </DialogTitle>
            <DialogActions>
                <Button startIcon={<Cancel />} autoFocus onClick={() => {setOpen(false)}}>
                    Cancel
                </Button>
                <Button sx={{color: 'red'}} startIcon={<Delete />} onClick={handleClick}>Delete</Button>
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