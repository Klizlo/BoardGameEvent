import { Alert, Button, Grid, MenuItem, Snackbar, TextField } from "@mui/material";
import { Box } from "@mui/system";
import { useEffect, useState } from "react";
import SaveIcon from '@mui/icons-material/Save';
import LoadingButton from '@mui/lab/LoadingButton';
import { Cancel } from "@mui/icons-material";
import Variables from "../Globals/Variables";
import { authHeader } from "../../helpers/auth-header";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import dayjs from "dayjs";
import { DateTimePicker, LocalizationProvider } from "@mui/x-date-pickers";

export default function EditEventForm(event) {

    const [boardGames, setBoardGames] = useState([]);

    const [selectedDate, setDate] = useState(dayjs(event.event.date));

    const [boardGame, setBoardGame] = useState(event.event.boardGame.name);

    const [data, setData] = useState(event.event);

    const [loading, setLoading] = useState(false);
    const [openAlert, setOpenAlert] = useState(false);
    const [error, setError] = useState("");

    const handleSubmit = (e) => {
        e.preventDefault();
        setLoading(true);
        fetch(`${Variables.API}/events/` + event.event.id,{
            method: "PUT",
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
                'Authorization': authHeader().Authorization
            },
            body: JSON.stringify(data)
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
                window.location = '/events/' + event.event.id;
            }
        });
    };

    const handleAlert = (e) => {
        setOpenAlert(false);
    };
    
    const handleChange = (e) => {
        setData({...data, [e.target.name]: e.target.value});
    };

    useEffect(() => {
        fetch(`${Variables.API}/boardGames`, {
            method: "GET"
        })
        .then((response) => response.json())
        .then((result) => {
            setBoardGames(result);
        });
    }, []);

    return (
        <Box component='div'>
            <Box component="form" method="POST" onSubmit={handleSubmit} sx={{ mt: 1 }}>
                <Grid container spacing={{ xs: 2, md: 3 }}>
                <Grid item xs={12} sm={12} md={6} >
                            <TextField
                                margin="normal"
                                required
                                id="name"
                                fullWidth
                                label="Event name"
                                name="name"
                                onChange={handleChange}
                                value={data.name}
                            />
                        </Grid>
                        <Grid item xs={12} sm={12} md={6} >
                            <TextField
                                margin="normal"
                                multiline
                                rows={4}
                                id="description"
                                fullWidth
                                label="Description"
                                name="description"
                                onChange={handleChange}
                                value={data.description}
                            />
                        </Grid>
                        <Grid item xs={12} sm={12} md={6} >
                            <TextField
                                required
                                margin="normal"
                                InputProps={{
                                    inputProps: {
                                        min: 1,
                                        step: 1
                                    }
                                }}
                                type="number"
                                id="numberOfPlayers"
                                fullWidth
                                label="Number of wanted players"
                                name="numberOfPlayers"
                                onChange={handleChange}
                                value={data.numberOfPlayers}
                            />
                        </Grid>
                        <Grid item xs={12} sm={12} md={6} >
                            <LocalizationProvider dateAdapter={AdapterDayjs}>
                                <DateTimePicker
                                    id="date"
                                    required
                                    fullWidth
                                    minDateTime={dayjs()}
                                    label="Date"
                                    name="date"
                                    onChange={(e) => {
                                        setDate(e);
                                        setData({...data, date : e.format("YYYY-MM-DDTHH:mm")});
                                    }}
                                    value={selectedDate}
                                    inputFormat="DD-MM-YYYY HH:mm"
                                    renderInput={(params) => <TextField {...params} />}
                                />
                            </LocalizationProvider>
                        </Grid>
                        <Grid item xs={12} sm={12} md={6} >
                            <TextField
                                margin="normal"
                                select
                                required
                                id="boardGame"
                                fullWidth
                                label="Board Game"
                                name="boardGame"
                                onChange={(e) => {
                                    setData({...data, [e.target.name]: boardGames.filter((boardGame) => boardGame.name === e.target.value)[0]});
                                    setBoardGame(e.target.value);
                                }}
                                value={boardGame}
                            >
                            {boardGames.sort((boardGame1, boardGame2) => sort(boardGame1, boardGame2)).map((boardGame) => (
                                <MenuItem key={boardGame.id} value={boardGame.name}>
                                    {boardGame.name}
                                </MenuItem>
                            ))}
                        </TextField>
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
                        window.location = '/events/' + event.event.id;
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

    function sort(boardGame1, boardGame2) {
        const name1 = boardGame1.name.toUpperCase();
        const name2 = boardGame2.name.toUpperCase();

        if(name1 < name2){
            return -1;
        }
        if(name1 > name2){
            return 1;
        }
        return 0;
    }
}