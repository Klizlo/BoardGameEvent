import {Alert, Button, Grid, InputLabel, MenuItem, Select, Snackbar, TextField} from "@mui/material";
import { Box } from "@mui/system";
import { useState } from "react";
import SaveIcon from '@mui/icons-material/Save';
import LoadingButton from '@mui/lab/LoadingButton';
import { Cancel } from "@mui/icons-material";
import Variables from "../Globals/Variables";
import { authHeader } from "../../helpers/auth-header";

export default function EditBoardGameForm(boardGame) {

    console.log(boardGame);
    console.log(boardGame.boardGamesCategories);
    console.log(boardGame.producers);

    const [data, setData] = useState({
        name: boardGame.boardGame.name,
        ageRestriction: boardGame.boardGame.ageRestriction,
        minNumberOfPlayers: boardGame.boardGame.minNumberOfPlayers,
        maxNumberOfPlayers: boardGame.boardGame.maxNumberOfPlayers,
        boardGameCategory: boardGame.boardGame.boardGameCategory,
        producer: boardGame.boardGame.producer,
    });

    console.log(data)

    const unauthorized = ['unauthorized', 'token_invalid', 'token_absent', 'token_expired', 'user_not_found'];

    const [loading, setLoading] = useState(false);
    const [openAlert, setOpenAlert] = useState(false);
    const [error, setError] = useState("");

    const handleSubmit = (e) => {
        e.preventDefault();
        setLoading(true);
        fetch(`${Variables.API}/boardGames/` + boardGame.boardGame.id,{
            method: "PUT",
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
                console.log(result);
                if(result.msg){
                    setOpenAlert(true);
                    setError(result.msg);
                    setLoading(false);
                } else if(unauthorized.includes(result.message)) {
                    window.location = '/';
                } else {
                    setData(result);
                    window.location = '/producers/' + boardGame.boardGame.id;
                }
            });
    };

    const handleAlert = (e) => {
        setOpenAlert(false);
    };

    const handleChange = (e) => {
        console.log(e.target.value);
        setData({...data, [e.target.name]: e.target.value});
    };

    return (
        <Box component='div'>
            <Box component="form" method="POST" onSubmit={handleSubmit} sx={{ mt: 1 }}>
                <Grid item xs={12} sm={12} md={6} >
                    <TextField
                        margin="normal"
                        id="name"
                        fullWidth
                        required
                        label="Board Game Name"
                        name="name"
                        onChange={handleChange}
                        value={data.name}
                    />
                </Grid>
                <Grid item xs={12} sm={12} md={6} >
                    <TextField
                        margin="normal"
                        id="minNumberOfPlayers"
                        fullWidth
                        required
                        label="Min Number of Players"
                        name="minNumberOfPlayers"
                        onChange={handleChange}
                        value= {data.minNumberOfPlayers}
                    />
                </Grid>
                <Grid item xs={12} sm={12} md={6} >
                    <TextField
                        margin="normal"
                        id="maxNumberOfPlayers"
                        fullWidth
                        required
                        label="Max Number of Players"
                        name="maxNumberOfPlayers"
                        onChange={handleChange}
                        value= {data.maxNumberOfPlayers}
                    />
                </Grid>
                <Grid item xs={12} sm={12} md={6} >
                    <InputLabel id="demo-simple-select-label">Age</InputLabel>
                    <Select
                        labelId="demo-simple-select-label"
                        id="chose"
                        name="producer"
                        value={data.producer.name}
                        label="Age"
                        onChange={handleChange}
                    >
                        <MenuItem value={10}>Ten</MenuItem>
                        <MenuItem value={20}>Twenty</MenuItem>
                        <MenuItem value={30}>Thirty</MenuItem>
                    </Select>
                    <TextField
                        margin="normal"
                        id="producerName"
                        fullWidth
                        required
                        label="Producer Name"
                        name="producerName"
                        onChange={handleChange}
                        value={data.producer.name}
                    />
                </Grid>
                <Grid item xs={12} sm={12} md={6} >
                    <TextField
                        margin="normal"
                        id="tag"
                        fullWidth
                        required
                        label="Tag"
                        name="tag"
                        onChange={handleChange}
                        value={data.boardGameCategory.name}
                    />
                </Grid>
                <Grid item xs={12} sm={12} md={6} >
                    <TextField
                        margin="normal"
                        id="age"
                        fullWidth
                        required
                        label="Age Restriction"
                        name="age"
                        onChange={handleChange}
                        value={data.ageRestriction}
                    />
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