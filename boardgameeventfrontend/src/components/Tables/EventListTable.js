import {Alert, Button, Dialog, DialogActions, DialogTitle, Fab, Grid, Snackbar} from "@mui/material";
import {DataGrid} from '@mui/x-data-grid';
import InfoIcon from '@mui/icons-material/Info';
import DeleteIcon from '@mui/icons-material/Delete';
import CancelIcon from '@mui/icons-material/Cancel';
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

    console.log(params.params.row);

    return (
        <strong key={params.params.row.id}>
            <Fab
                color={"info"}
                size={"small"}
                onClick={() => {
                    navigate("/events/" + params.params.row.id)
                }}
            >
                <InfoIcon/>
            </Fab>
            { currentUser && (currentUser.user.roles.map((role) => role.name).includes(Role.Admin) || currentUser.user.username === params.params.row.organizer) ? (
            <strong>
                <Fab
                color={"warning"}
                size={"small"}
                onClick={() => {
                    navigate('/events/' + params.params.row.id + '/edit')
                }}
                >
                    <Edit />
                </Fab>
                <Fab
                    color={"error"}
                    size={"small"}
                    onClick={() => {setOpen(true);}}
                >
                    <DeleteIcon/>
                </Fab>
                <Dialog
                open={open}
                onClose={() => setOpen(false)}
                aria-labelledby="draggable-dialog-title"
            >
                <DialogTitle style={{ cursor: 'move' }} id="draggable-dialog-title">
                    Do you want remove event from list?
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
    {field: 'name', headerName: 'Producer Name', width: 250},
    {field: 'numberOfPlayers', headerName: "Number of wanted players", width: 100},
    {field: 'date', headerName: "Event date", width: 150},
    {field: 'boardGame', headerName: "Board Game", width: 250},
    {field: 'organizer', headerName: "Organizer", width: 150},
    {
        field: 'Options',
        sortable: false,
        renderCell: (props) => {return <GoToDetails params={props} />},
        width: 150,
    }
];

const EventListTable = eventData => {

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
        border={2}
        borderColor={"dimgrey"}
        borderRadius={"12px"}
        container
        alignSelf={"center"}
        alignItems={"center"}
        bgcolor={'action.hover'}
        width={'90%'}
        height={700}
    >
        <DataGrid
            rows={events}
            columns={columns}
            pageSize={20}
            rowsPerPageOptions={[20]}
        />
    </Grid>
    );
}

export default EventListTable;