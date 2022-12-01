import {useEffect, useState} from "react";
import Variables from "../components/Globals/Variables";
import Box from "@mui/material/Box";
import {Grid, Typography} from "@mui/material";
import BoardGamesTable from "../components/Tables/BoardGamesTable";

const BoardGames = () => {

    const [error, setError] = useState(null);
    const [isLoaded, setIsLoaded] = useState(false);
    const [boardGames, setBoardGames] = useState([]);
    const endpoint = Variables.API + '/boardGames';
    useEffect(() => {
        fetch(endpoint, {
            method: 'GET',
        })
            .then(res => res.json())
            .then(
                (data) => {
                    setIsLoaded(true);
                    setBoardGames(data);
                    console.log(data);
                },
                (error) => {
                    setIsLoaded(true);
                    setError(error);
                    console.log(error);
                }
            )
    }, []);

    if (error){
        return <div>ERROR: {error.message}</div>;
    } else if (!isLoaded){
        return <div>Fetching Data...</div>;
    } else {
        return (
            <Box
                sx={{
                    width: 1,
                    bgcolor: "background.default",
                }}
            >
                <Grid
                    marginLeft={"auto"}
                    marginRight={"auto"}
                    p={2}
                    border={2}
                    borderColor={"dimgrey"}
                    borderRadius={"12px"}
                    container
                    direction={"column"}
                    justifyContent={"space-between"}
                    alignSelf={"center"}
                    alignItems={"center"}
                    bgcolor={'action.hover'}
                    width={'100%'}
                >
                    <Typography sx={{fontSize: 35, fontWeight: 'bold'}} color={"text.secondary"} gutterBottom>
                        Board Games
                    </Typography>
                    <BoardGamesTable BoardGamesData={boardGames}/>
                </Grid>
            </Box>
        );
    }
}

export default BoardGames;