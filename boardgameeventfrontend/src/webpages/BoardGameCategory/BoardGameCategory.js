import { Alert, Snackbar, Typography } from "@mui/material";
import { Box } from "@mui/system";
import { useEffect, useState } from "react";
import { useParams } from "react-router";
import BoardGameCategoryDetails from "../../components/Details/BoardGameCategoriesDetails";
import ProducerDetails from "../../components/Details/ProducerDetails";
import Variables from "../../components/Globals/Variables";
import { authHeader } from "../../helpers/auth-header";

export default function BoardGameCategory() {
    const { id } = useParams();

    const [error, setError] = useState('');
    const [openAlert, setOpenAlert] = useState(false);

    const [data, setData] = useState(null);

    const handleAlert = (e) => {
        setOpenAlert(false);
    };

    useEffect(() => {
        fetch(`${Variables.API}/boardGamesCategories/` + id, {
            method: "GET",
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
                'Authorization': authHeader().Authorization
            },
        })
        .then((response) => response.json())
        .then((result) => {
            if(result.msg){
                setOpenAlert(true);
                setError(result.msg);
            } else if(result.status === 401) {
                window.location = '/login';
            } else {
                setData(result);
            }
        })
    }, []);

    return (
        <Box
        sx={{
            margin: '2%',
        }}>
            <Typography
                variant="h5"
                sx={{
                    fontFamily: 'kdam-thmor-pro',
                    fontWeight: 700,
                    letterSpacing: '.3rem',
                    color: "white"
                }}
            >
                Board Game Category Details: {data != null ? data.name : ''}
            </Typography>
            {data != null ? (<BoardGameCategoryDetails boardGamesCategory={data} />) : (<Typography>Fetching Data...</Typography>)}
            <Snackbar
                anchorOrigin={{ vertical: 'top', horizontal: 'center' }}
                open={openAlert}
                onClose={handleAlert}
                autoHideDuration={6000}
            >
                <Alert severity="error" sx={{ width: '100%' }}>
                    {error}
                </Alert>
            </Snackbar>
        </Box>
    );
}