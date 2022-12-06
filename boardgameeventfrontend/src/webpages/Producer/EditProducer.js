import { Alert, Box, Snackbar, Typography } from "@mui/material";
import { useEffect, useState } from "react";
import { useParams } from "react-router";
import EditProducerForm from "../../components/Edits/EditProducerForm";
import Variables from "../../components/Globals/Variables";
import { authHeader } from "../../helpers/auth-header";

export default function EditProducer() {

    const { id } = useParams();

    const [openAlert, setOpenAlert] = useState(false);
    const [error, setError] = useState("");

    const [data, setData] = useState(null);

    useEffect(() => {
        fetch(`${Variables.API}/producers/` + id, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
                'Authorization': authHeader().Authorization
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
                Edit Producer
            </Typography>
            {data != null  ? <EditProducerForm producer={data} /> : <Typography >Fetching...</Typography>}
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