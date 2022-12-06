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
import CancelIcon from '@mui/icons-material/Cancel';
import {useNavigate} from "react-router-dom";
import {useState} from "react";
import { Edit } from "@mui/icons-material";
import { authenticationService } from "../../service/authenticateService";
import { Role } from "../../helpers/role";
import Variables from "../Globals/Variables";
import { authHeader } from "../../helpers/auth-header";
import Box from "@mui/material/Box";
import AddIcon from "@mui/icons-material/Add";

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
        fetch(`${Variables.API}/boardGamesCategories/` + params.params.row.id, {
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
            window.location = '/boardGamesCategories';
        }
    }

    return (
        <strong key={params.params.row.id}>
            <IconButton
                color={"info"}
                size={"small"}
                onClick={() => {
                    navigate("/boardGamesCategories/" + params.params.row.id)
                }}
            >
                <InfoIcon/>
            </IconButton>
            { currentUser && currentUser.user.roles.map((role) => role.name).includes(Role.Admin) ? (
            <strong>
                <IconButton
                color={"warning"}
                size={"small"}
                onClick={() => {
                    navigate('/boardGamesCategories/' + params.params.row.id + '/edit')
                }}
                >
                    <Edit />
                </IconButton>
                <IconButton
                    color={"error"}
                    size={"small"}
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
                    Do you want remove board game category from list?
                </DialogTitle>
                <DialogActions>
                <Button startIcon={<CancelIcon />} autoFocus onClick={() => {setOpen(false)}}>
                    Cancel
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
    {field: 'name', headerName: 'Board Game Category Name', flex: 1},
    {
        field: 'Options',
        sortable: false,
        renderCell: (props) => {return <GoToDetails params={props} />},
        width: 150,
        headerAlign: 'center',
        align: 'center'
    }
];

const BoardGameCategoryTable = BoardGameCategoryData => {

    const boardGameCategory = BoardGameCategoryData.BoardGameCategoryData;
    const currentUser = authenticationService.currentUserValue;
    
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
            rows={boardGameCategory}
            columns={columns}
            autoHeight {...boardGameCategory}
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
                    <Tooltip title={<h4>Add new Board Game Category</h4>}>
                        <IconButton
                            edge="end"
                            color={"success"}
                            onClick={() => {window.location = '/boardGamesCategories/add'}}
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

export default BoardGameCategoryTable;
