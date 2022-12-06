import {Alert, Button, Dialog, DialogActions, DialogTitle, Fab, Grid, Snackbar} from "@mui/material";
import {DataGrid} from '@mui/x-data-grid';
import InfoIcon from '@mui/icons-material/Info';
import DeleteIcon from '@mui/icons-material/Delete';
import {useNavigate} from "react-router-dom";
import {useEffect, useState} from "react";
import {authenticationService} from "../../service/authenticateService";
import Variables from "../Globals/Variables";
import {authHeader} from "../../helpers/auth-header";
import {Role} from "../../helpers/role";
import {Edit} from "@mui/icons-material";
import CancelIcon from "@mui/icons-material/Cancel";

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
        fetch(`${Variables.API}/boardGames/` + params.params.row.id, {
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
            })
        if(!openAlert){
            window.location = '/boardGames';
        }
    }

    return (
        <strong key={params.params.row.id}>
            <Fab
                color={"info"}
                size={"small"}
                onClick={() => {
                    navigate("/boardGames/" + params.params.row.id)
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
                            navigate('/boardGames/' + params.params.row.id + '/edit')
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
                            Do you want to delete Board Game from list?
                        </DialogTitle>
                        <DialogActions>
                            <Button startIcon={<CancelIcon />} autoFocus onClick={() => {setOpen(false)}}>
                                Cancel
                            </Button>
                            <Button sx={{color: 'red'}} startIcon={<DeleteIcon />} onClick={handleClick}>Delete</Button>
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
    {field: 'name', headerName: 'Game Name', width: 130},
    {field: 'minNumberOfPlayers', headerName: 'Min Players', width: 100},
    {field: 'maxNumberOfPlayers', headerName: 'Max Players', width: 100},
    {field: 'ageRestriction', headerName: 'Age Restriction', width: 120},
    {field: 'producerName', headerName: 'Producer', width: 200},
    {field: 'boardGameCategoryName', headerName: 'Category', width: 200},
    {
        field: 'Options',
        sortable: false,
        renderCell: (props) => {return <ShowOptions params={props} />},
        width: 200,
    }
];

const BoardGameListTable = BoardGamesData => {
    const [boardGames, setBoardGames] = useState([]);
    useEffect(() => {
        const temp = [];
        BoardGamesData.BoardGamesData.map(item => {
            temp.push({...item,producerName : item.producer.name,boardGameCategoryName :item.boardGameCategory.name});
        })
        setBoardGames(temp);
    },[BoardGamesData.BoardGamesData]);
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
            rows={boardGames}
            columns={columns}
            pageSize={20}
            rowsPerPageOptions={[20]}
        />
    </Grid>
    );
}

export default BoardGameListTable;
