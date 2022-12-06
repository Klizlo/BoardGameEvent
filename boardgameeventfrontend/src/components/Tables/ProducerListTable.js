import {Alert, Button, Dialog, DialogActions, DialogTitle, Fab, Grid, Snackbar} from "@mui/material";
import {DataGrid} from '@mui/x-data-grid';
import InfoIcon from '@mui/icons-material/Info';
import DeleteIcon from '@mui/icons-material/Delete';
import CancelIcon from '@mui/icons-material/Cancel';
import {useNavigate} from "react-router-dom";
import {useState} from "react";
import { Edit } from "@mui/icons-material";
import { authenticationService } from "../../service/authenticateService";
import { Role } from "../../helpers/role";
import Variables from "../Globals/Variables";
import { authHeader } from "../../helpers/auth-header";

const ShowOptions = (params) => {
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
        fetch(`${Variables.API}/producers/` + params.params.row.id, {
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
            }else{
                window.location = '/producers';
            }
        })
    }

    return (
        <strong key={params.params.row.id}>
            <Fab
                color={"info"}
                size={"small"}
                onClick={() => {
                    navigate("/producers/" + params.params.row.id)
                }}
            >
                <InfoIcon/>
            </Fab>
            { currentUser && currentUser.user.roles.map((role) => role.name).includes(Role.Admin) ? (
            <strong>
                <Fab
                color={"warning"}
                size={"small"}
                onClick={() => {
                    navigate('/producers/' + params.params.row.id + '/edit')
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
                Czy chcesz usunąć grę z listy?
                </DialogTitle>
                <DialogActions>
                <Button startIcon={<CancelIcon />} autoFocus onClick={() => {setOpen(false)}}>
                    Anuluj
                </Button>
                <Button sx={{color: 'red'}} startIcon={<DeleteIcon />} onClick={handleClick}>Usuń</Button>
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
    {field: 'name', headerName: 'Producer Name', width: 250, flex: 4},
    {
        field: 'Options',
        sortable: false,
        renderCell: (props) => {return <ShowOptions params={props} />},
        width: 150,
    }
];

const ProducerTable = producerData => {

    const producers = producerData.ProducerData;
    
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
            rows={producers}
            columns={columns}
            pageSize={20}
            rowsPerPageOptions={[20]}
        />
    </Grid>
    );
}

export default ProducerTable;
