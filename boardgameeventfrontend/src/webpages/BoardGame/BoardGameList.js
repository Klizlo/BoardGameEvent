import {useEffect, useState} from "react";
import Variables from "../../components/Globals/Variables";
import Box from "@mui/material/Box";
import {Grid, Typography} from "@mui/material";
import BoardGameListTable from "../../components/Tables/BoardGameListTable";

const BoardGameList = () => {

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
                },
                (error) => {
                    setIsLoaded(true);
                    setError(error);
                }
            )
    }, []);

    if (error) {
        return <div>ERROR: {error.message}</div>;
    } else if (!isLoaded) {
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
                    container
                    direction={"column"}
                    justifyContent={"space-between"}
                    alignSelf={"center"}
                    alignItems={"center"}
                    width={'100%'}
                >
                    <Typography
                        variant="h4"
                        sx={{
                            fontFamily: 'kdam-thmor-pro',
                            fontWeight: 700,
                            letterSpacing: '.3rem',
                            color: "white"
                        }}
                    >
                        Board Games
                    </Typography>
                    <BoardGameListTable BoardGamesData={boardGames}/>
                </Grid>
            </Box>
        );
    }
}

export default BoardGameList;