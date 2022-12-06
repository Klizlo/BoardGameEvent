import { Alert, Button, Grid, Snackbar, TextField } from "@mui/material";
import { Box } from "@mui/system";
import { useState } from "react";
import SaveIcon from '@mui/icons-material/Save';
import LoadingButton from '@mui/lab/LoadingButton';
import { Cancel } from "@mui/icons-material";
import Variables from "../Globals/Variables";
import { authHeader } from "../../helpers/auth-header";

export default function AddProducerForm() {

    const [data, setData] = useState({
        name: ""
    });

    const [loading, setLoading] = useState(false);
    const [openAlert, setOpenAlert] = useState(false);
    const [error, setError] = useState("");

    const handleSubmit = (e) => {
        e.preventDefault();
        setLoading(true);
        fetch(`${Variables.API}/producers`,{
            method: "POST",
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
                'Authorization': authHeader().Authorization
            },
            body: JSON.stringify({
                name: data.name,
            })
        })
        .then((response) => response.json())
        .then((result) => {
            if(result.msg){
                setOpenAlert(true);
                setError(result.msg);
                setLoading(false);
            } else if(result.status === 401) {
                window.location = '/login';
            } else {
                setData(result);
                window.location = '/producers';
            }
        })
    };

    const handleChange = (e) => {
        setData({...data, [e.target.name]: e.target.value});
    };

    const handleAlert = (e) => {
        setOpenAlert(false);
    };

    return (
        <Box component='div'>
            <Box component="form" method="POST" onSubmit={handleSubmit} sx={{ mt: 1 }}>
                <Grid container spacing={{ xs: 2, md: 3 }}>
                    <Grid item xs={12} sm={12} md={6}>
                        <TextField
                            margin="normal"
                            id="name"
                            fullWidth
                            required
                            label="Producer name"
                            name="name"
                            onChange={handleChange}
                            value={data.name}
                        />
                    </Grid>
                </Grid>
                <LoadingButton
                    type="submit"
                    loading={loading}
                    loadingPosition="start"
                    startIcon={<SaveIcon />}
                    variant="contained"
                    sx={{ 
                        mt: 3,
                        mb: 2,
                        background: 'green',
                        '&:hover': {
                            backgroundColor: 'black'
                        }
                    }}
                >
                    Save
                </LoadingButton>
                <Button
                    startIcon={<Cancel />}
                    variant="contained"
                    sx={{ 
                        mt: 3,
                        mb: 2,
                        background: 'orange',
                        '&:hover': {
                            background: 'white',
                            color: 'black'
                        }
                    }}
                    onClick={()=>{
                        window.location = '/';
                    }}>
                        Cancel
                </Button>
            </Box>
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