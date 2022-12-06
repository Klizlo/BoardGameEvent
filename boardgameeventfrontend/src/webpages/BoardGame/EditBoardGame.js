import { Alert, Box, Snackbar, Typography } from "@mui/material";
import { useEffect, useState } from "react";
import { useParams } from "react-router";
import Variables from "../../components/Globals/Variables";
import { authHeader } from "../../helpers/auth-header";
import EditBoardGameForm from "../../components/Edits/EditBoardGameForm";

export default function EditBoardGame() {

    const { id } = useParams();

    const [openAlert, setOpenAlert] = useState(false);
    const [error, setError] = useState("");
    const unauthorized = ['unauthorized', 'token_invalid', 'token_absent', 'token_expired', 'user_not_found'];

    const [data, setData] = useState(null);
    const [producersData, setProducersData] = useState(null);
    const [boardGamesCategoriesData, setBoardGamesCategoriesData] = useState(null);

    useEffect(() => {
        fetch(`${Variables.API}/boardGames/` + id, {
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
                } else if(unauthorized.includes(result.message)) {
                    window.location = '/';
                } else {
                    setData(result);
                }
            })
    }, []);

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
                } else if(unauthorized.includes(result.message)) {
                    window.location = '/';
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
                } else if(unauthorized.includes(result.message)) {
                    window.location = '/';
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
                Edit Board Game
            </Typography>
            {data != null && producersData != null && boardGamesCategoriesData != null ? <EditBoardGameForm boardGame={data} producers={producersData} boardGamesCategories={boardGamesCategoriesData}/> : <Typography >Fetching...</Typography>}
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