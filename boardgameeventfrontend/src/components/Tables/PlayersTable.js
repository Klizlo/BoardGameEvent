import {Alert, Button, Dialog, DialogActions, DialogTitle, Grid, IconButton, Snackbar} from "@mui/material";
import {DataGrid} from '@mui/x-data-grid';
import DeleteIcon from '@mui/icons-material/Delete';
import CancelIcon from '@mui/icons-material/Cancel';
import {useParams} from "react-router-dom";
import {useState} from "react";
import { authenticationService } from "../../service/authenticateService";
import { Role } from "../../helpers/role";
import Variables from "../Globals/Variables";
import { authHeader } from "../../helpers/auth-header";
import { Cancel } from "@mui/icons-material";

const GoToDetails = (params) => {
    // eslint-disable-next-line react-hooks/rules-of-hooks
    const currentUser = authenticationService.currentUserValue;

    const [open, setOpen] = useState(false);
    const [error, setError] = useState("");
    const [openAlert, setOpenAlert] = useState(false);

    const handleAlertClose = () => {
        setOpenAlert(false);
    }

    const eventId = useParams();

    console.log(eventId);

    const handleClick = () => {
        setOpen(false);
        fetch(`${Variables.API}/events/${eventId.id}/players/${params.params.row.id}`, {
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
            } else if (result.status === 401){
                window.location = '/login';
            }
        });
        if(!openAlert){
            window.location = '/events/' + eventId.id;
        }
    }

    return (
        <strong key={params.params.row.id}>
            { currentUser && (currentUser.user.roles.map((role) => role.name).includes(Role.Admin) || currentUser.user.id === params.params.row.id) ? (
            <strong>
                <IconButton
                    color={"error"}
                    onClick={() => {setOpen(true);}}
                >
                    <Cancel/>
                </IconButton>
                <Dialog
                open={open}
                onClose={() => setOpen(false)}
                aria-labelledby="draggable-dialog-title"
            >
                <DialogTitle style={{ cursor: 'move' }} id="draggable-dialog-title">
                    Do you want to cancel participation?
                </DialogTitle>
                <DialogActions>
                <Button startIcon={<CancelIcon />} autoFocus onClick={() => {setOpen(false)}}>
                    Cancel
                </Button>
                <Button sx={{color: 'red'}} startIcon={<DeleteIcon />} onClick={handleClick}>Yes</Button>
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
    {field: 'username', headerName: 'Name', width: 250, flex: 4},
    {
        field: ' ',
        sortable: false,
        renderCell: (props) => {return <GoToDetails params={props} />},
        width: 70,
    }
];

const PlayersTable = playersData => {

    const players = playersData.playersData;
    
    return (
    <Grid
        marginLeft={"auto"}
        marginRight={"auto"}
        p={2}
        container
        alignSelf={"center"}
        alignItems={"center"}
        width={'100%'}
    >
        <DataGrid
            rows={players}
            columns={columns}
            autoHeight {...players}
            pageSize={5}
            rowsPerPageOptions={[5]}
        />
    </Grid>
    );
}

export default PlayersTable;