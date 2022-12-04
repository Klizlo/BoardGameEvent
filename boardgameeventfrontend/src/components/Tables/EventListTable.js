import {Fab, Grid} from "@mui/material";
import {DataGrid} from '@mui/x-data-grid';
import InfoIcon from '@mui/icons-material/Info';
import {useNavigate} from "react-router-dom";

const goToDetails = (params) => {
    // eslint-disable-next-line react-hooks/rules-of-hooks
    const navigate = useNavigate();
    return (
        <strong key={params.row.id}>
            <Fab
                color={"info"}
                size={"small"}
                onClick={() => {
                    navigate("/game/" + params.row.id)
                }}
            >
                <InfoIcon/>
            </Fab>
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
        renderCell: goToDetails,
        width: 200,
    }
];

const EventListTable = EventListData => {
    console.log(EventListData)
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
                rows={EventListData}
                columns={columns}
                pageSize={20}
                rowsPerPageOptions={[20]}
            />
        </Grid>
    );
}

export default EventListTable;
