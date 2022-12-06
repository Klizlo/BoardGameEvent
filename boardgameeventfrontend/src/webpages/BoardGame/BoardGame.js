import { Alert, Snackbar, Typography } from "@mui/material";
import { Box } from "@mui/system";
import { useEffect, useState } from "react";
import { useParams } from "react-router";
import BoardGameDetails from "../../components/Details/BoardGameDetails";
import Variables from "../../components/Globals/Variables";
import { authHeader } from "../../helpers/auth-header";

export default function BoardGame() {
    const { id } = useParams();

    const [error, setError] = useState('');
    const [openAlert, setOpenAlert] = useState(false);

    const [data, setData] = useState(null);

    const handleAlert = (e) => {
        setOpenAlert(false);
    };

    useEffect(() => {
        fetch(`${Variables.API}/boardGames/` + id, {
            method: "GET",
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
                'Authorization': authHeader()
            },
        })
            .then((response) => response.json())
            .then((result) => {
                console.log(result);
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
                Board Game Details: {data != null ? data.name : ''}
            </Typography>
            {data != null ? (<BoardGameDetails boardGame={data} />) : (<Typography>Fetching Data...</Typography>)}
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