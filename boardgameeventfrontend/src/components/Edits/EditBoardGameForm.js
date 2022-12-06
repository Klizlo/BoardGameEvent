import {Alert, Button, Grid, InputLabel, MenuItem, Select, Snackbar, TextField} from "@mui/material";
import { Box } from "@mui/system";
import { useState } from "react";
import SaveIcon from '@mui/icons-material/Save';
import LoadingButton from '@mui/lab/LoadingButton';
import { Cancel } from "@mui/icons-material";
import Variables from "../Globals/Variables";
import { authHeader } from "../../helpers/auth-header";

const ageRestrictionList = [
    '+7',
    '+14',
    '+18'
]

export default function EditBoardGameForm(boardGame) {


    const [data, setData] = useState({
        name: boardGame.boardGame.name,
        ageRestriction: boardGame.boardGame.ageRestriction,
        minNumberOfPlayers: boardGame.boardGame.minNumberOfPlayers,
        maxNumberOfPlayers: boardGame.boardGame.maxNumberOfPlayers,
        boardGameCategory: boardGame.boardGame.boardGameCategory,
        producer: boardGame.boardGame.producer,
    });

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
                ageRestriction: data.ageRestriction,
                minNumberOfPlayers: data.minNumberOfPlayers,
                maxNumberOfPlayers: data.maxNumberOfPlayers,
                boardGameCategory: data.boardGameCategory,
                producer: data.producer,
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
                    window.location = '/boardGames/' + boardGame.boardGame.id;
                }
            });
    };

    const handleAlert = (e) => {
        setOpenAlert(false);
    };

    const handleProducerChange = (e) => {
      let id = '';
      boardGame.producers.map(producer => {
          if (producer.name === e.target.value){
              id = producer.id;
          }
      });
      setData({...data, producer : {name : e.target.value, id: id}});
    };
    const handleGameCategoryChange = (e) => {
        let id = '';
        boardGame.boardGamesCategories.map(category => {
            if (category.name === e.target.value){
                id = category.id;
            }
        });
        setData({...data, boardGameCategory : {name : e.target.value, id: id}});
    };
    const handleChange = (e) => {
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
                        type="number"
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
                        type="number"
                        name="maxNumberOfPlayers"
                        onChange={handleChange}
                        value= {data.maxNumberOfPlayers}
                    />
                </Grid>
                <Grid item xs={12} sm={12} md={6} >
                    <TextField
                        margin="normal"
                        id="producerName"
                        select
                        fullWidth
                        required
                        label="Producer Name"
                        name="producer"
                        onChange={handleProducerChange}
                        value={data.producer.name}
                    >
                        {boardGame.producers.map(producer => {
                            return(
                                <MenuItem key={producer.id} value={producer.name}>{producer.name}</MenuItem>
                            )
                        })}
                    </TextField>
                </Grid>
                <Grid item xs={12} sm={12} md={6} >
                    <TextField
                        margin="normal"
                        id="gameCategory"
                        fullWidth
                        select
                        required
                        label="Game Category"
                        name="boardGameCategory"
                        onChange={handleGameCategoryChange}
                        value={data.boardGameCategory.name}
                    >
                        {boardGame.boardGamesCategories.map(category => {
                            return(
                                <MenuItem key={category.id} value={category.name}>{category.name}</MenuItem>
                            )
                        })}
                    </TextField>
                </Grid>
                <Grid item xs={12} sm={12} md={6} >
                    <TextField
                        margin="normal"
                        id="age"
                        fullWidth
                        select
                        required
                        label="Age Restriction"
                        name="ageRestriction"
                        onChange={handleChange}
                        value={data.ageRestriction}
                    >
                        {ageRestrictionList.map(ageRestriction => {
                            return(
                                <MenuItem key={ageRestriction} value={ageRestriction}>{ageRestriction}</MenuItem>
                            )
                        })}
                    </TextField>
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