import {Alert, Box, Button, Dialog, DialogActions, DialogTitle, Grid, IconButton, Snackbar, Tooltip} from "@mui/material";
import {DataGrid} from '@mui/x-data-grid';
import InfoIcon from '@mui/icons-material/Info';
import DeleteIcon from '@mui/icons-material/Delete';
import CancelIcon from '@mui/icons-material/Cancel';
import AddIcon from "@mui/icons-material/Add";
import {useNavigate} from "react-router-dom";
import { authenticationService } from "../../service/authenticateService";
import { useEffect, useState } from "react";
import Variables from "../Globals/Variables";
import { authHeader } from "../../helpers/auth-header";
import { Edit } from "@mui/icons-material";
import { Role } from "../../helpers/role";

const GoToDetails = (params) => {
    // eslint-disable-next-line react-hooks/rules-of-hooks
    const navigate = useNavigate();
    const currentUser = authenticationService.currentUserValue;

    const [open, setOpen] = useState(false);
    const [error, setError] = useState("");
    const [openAlert, setOpenAlert] = useState(false);

    const handleAlertClose = () => {
        setOpenAlert(false);
    }

    const handleClick = () => {
        setOpen(false);
        fetch(`${Variables.API}/events/` + params.params.row.id, {
            method: "DELETE",
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
                'Authorization': authHeader().Authorization
            },
        }).then((response) => response.json())
        .then((result) => {
            if (result.msg){
                setOpenAlert(true);
                setError(result.message);
            } else if (result.status === 401){
                window.location = '/login';
            }
        });
        if(!openAlert){
            window.location = '/events';
        }
    }

    return (
        <strong key={params.params.row.id}>
            <IconButton
                color={"info"}
                onClick={() => {
                    navigate("/events/" + params.params.row.id)
                }}
            >
                <InfoIcon/>
            </IconButton>
            { currentUser && (currentUser.user.roles.map((role) => role.name).includes(Role.Admin) || currentUser.user.username === params.params.row.organizer) ? (
            <strong>
                <IconButton
                color={"warning"}
                onClick={() => {
                    navigate('/events/' + params.params.row.id + '/edit')
                }}
                >
                    <Edit />
                </IconButton>
                <IconButton
                    color={"error"}
                    onClick={() => {setOpen(true);}}
                >
                    <DeleteIcon/>
                </IconButton>
                <Dialog
                open={open}
                onClose={() => setOpen(false)}
                aria-labelledby="draggable-dialog-title"
            >
                <DialogTitle style={{ cursor: 'move' }} id="draggable-dialog-title">
                    Do you want remove {params.params.row.name} from list?
                </DialogTitle>
                <DialogActions>
                <Button startIcon={<CancelIcon />} autoFocus onClick={() => {setOpen(false)}}>
                    Cancel
                </Button>
                <Button sx={{color: 'red'}} startIcon={<DeleteIcon />} onClick={handleClick}>Remove</Button>
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
            </strong>
            ) : (
                <></>
            )}
        </strong>
    )
}

let columns = [
    {field: 'name', headerName: 'Producer Name', flex:2},
    {field: 'numberOfPlayers', headerName: "Number of wanted players", flex:1},
    {field: 'date', headerName: "Event date", flex:1},
    {field: 'boardGame', headerName: "Board Game", flex:2},
    {field: 'organizer', headerName: "Organizer", flex:1},
    {
        field: 'Options',
        sortable: false,
        renderCell: (props) => {return <GoToDetails params={props} />},
        width: 150,
        headerAlign: 'center',
        align: 'center'
    }
];

const EventListTable = eventData => {

    const currentUser = authenticationService.currentUserValue;

    const [events, setEvents] = useState([]);

    useEffect(() => {
        const temp = [];
        eventData.eventData.map(item => {
            temp.push({...item,boardGame : item.boardGame.name, organizer :item.organizer.username});
        })
        setEvents(temp);
    },[eventData.eventData]);
    
    return (
    <Grid
        marginLeft={"auto"}
        marginRight={"auto"}
        p={2}
        container
        alignSelf={"center"}
        alignItems={"center"}
        width={'90%'}
    >
        <DataGrid
            rows={events}
            columns={columns}
            autoHeight {...events}
            pageSize={10}
            rowsPerPageOptions={[10]}
        />
        {currentUser && currentUser.user.roles.map((role) => role.name).includes(Role.User) ? (
            <Grid
                p={1}
                container
                direction={"row"}
                width={'100%'}
            >
                <Box sx={{ flexGrow: 1 }} />
                <Box
                    justifyContent="flex-end"
                >
                    <Tooltip title={<h4>Add new Event</h4>}>
                        <IconButton
                            edge="end"
                            color={"success"}
                            onClick={() => {window.location = '/producers/add'}}
                            size="large"
                        >
                            <AddIcon/>
                        </IconButton>
                    </Tooltip>
                </Box>
            </Grid>
        ) : (
            <></>
        )}
    </Grid>
    );
}

export default EventListTable;