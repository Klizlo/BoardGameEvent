import {Alert, Box, Snackbar, Typography} from "@mui/material";
import AddBoardGameForm from "../../components/Add/AddBoardGameForm";
import {useEffect, useState} from "react";
import Variables from "../../components/Globals/Variables";
import {authHeader} from "../../helpers/auth-header";

export default function AddBoardGame() {

    const [openAlert, setOpenAlert] = useState(false);
    const [error, setError] = useState("");

    const [producersData, setProducersData] = useState(null);
    const [boardGamesCategoriesData, setBoardGamesCategoriesData] = useState(null);


    useEffect(() => {
        fetch(`${Variables.API}/producers/`, {
            method: 'GET',
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
                    setProducersData(result);
                }
            })
    }, []);

    useEffect(() => {
        fetch(`${Variables.API}/boardGamesCategories/`, {
            method: 'GET',
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
                    setBoardGamesCategoriesData(result);
                }
            })
    }, []);

    const handleAlert = (e) => {
        setOpenAlert(false);
    };

    return (
        <Box
            sx={{
                margin: '2%'
            }}>
            <Typography
                variant="h5"
                sx={{
                    fontFamily: 'kdam-thmor-pro',
                    fontWeight: 700,
                    letterSpacing: '.3rem',
                    color: 'white'
                }}
            >
                Add Producer
            </Typography>
            {producersData != null && boardGamesCategoriesData != null ? <AddBoardGameForm producers={producersData} boardGamesCategories={boardGamesCategoriesData}/> : <Typography >Fetching...</Typography>}
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