import {
    Alert,
    Button,
    Dialog,
    DialogActions,
    DialogTitle,
    Grid,
    IconButton,
    Snackbar,
    Tooltip
} from "@mui/material";
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
import Box from "@mui/material/Box";
import AddIcon from "@mui/icons-material/Add";

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
                } else if (result.status === 401){
                    window.location = '/login';
                }
            })
        if(!openAlert){
            window.location = '/boardGames';
        }
    }

    return (
        <strong key={params.params.row.id}>
            <IconButton
                color={"info"}
                onClick={() => {
                    navigate("/boardGames/" + params.params.row.id)
                }}
            >
                <InfoIcon/>
            </IconButton>
            { currentUser && currentUser.user.roles.map((role) => role.name).includes(Role.Admin) ? (
                <strong>
                    <IconButton
                        color={"warning"}
                        onClick={() => {
                            navigate('/boardGames/' + params.params.row.id + '/edit')
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
    {field: 'name', headerName: 'Game Name', flex: 1},
    {field: 'minNumberOfPlayers', headerName: 'Min Players', width: 100, headerAlign: 'center', align: 'center'},
    {field: 'maxNumberOfPlayers', headerName: 'Max Players', width: 100, headerAlign: 'center', align: 'center'},
    {field: 'ageRestriction', headerName: 'Age Restriction', width: 115, headerAlign: 'center', align: 'center'},
    {field: 'producerName', headerName: 'Producer', flex: 1},
    {field: 'boardGameCategoryName', headerName: 'Category', flex: 1},
    {
        field: 'Options',
        sortable: false,
        renderCell: (props) => {return <ShowOptions params={props} />},
        width: 150,
        headerAlign: 'center',
        align: 'center'
    }
];

const BoardGameListTable = BoardGamesData => {
    const [boardGames, setBoardGames] = useState([]);
    const currentUser = authenticationService.currentUserValue;
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
        container
        alignSelf={"center"}
        alignItems={"center"}
        width={'90%'}
    >
        <DataGrid
            rows={boardGames}
            columns={columns}
            autoHeight {...boardGames}
            pageSize={10}
            rowsPerPageOptions={[10]}
        />
        {currentUser && currentUser.user.roles.map((role) => role.name).includes(Role.Admin) ? (
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
                    <Tooltip title={<h4>Add new Board Game</h4>}>
                        <IconButton
                            edge="end"
                            color={"success"}
                            onClick={() => {window.location = '/boardGames/add'}}
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

export default BoardGameListTable;
