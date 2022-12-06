import { Box, Grid, Typography } from "@mui/material";
import { useEffect, useState } from "react";
import Variables from "../../components/Globals/Variables";
import BoardGameCategoryTable from "../../components/Tables/BoardGameCategoryTable";


const BoardGameCategoryList = () => {

    const [error, setError] = useState(null);
    const [isLoaded, setIsLoaded] = useState(false);
    const [boardGameCategory, setBoardGameCategory] = useState([]);
    const endpoint = Variables.API + '/boardGamesCategories';
    useEffect(() => {
        fetch(endpoint, {
            method: 'GET',
        })
            .then(res => res.json())
            .then(
                (data) => {
                    setIsLoaded(true);
                    setBoardGameCategory(data);
                },
                (error) => {
                    setIsLoaded(true);
                    setError(error);
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
                        Board Game Categories
                    </Typography>
                    <BoardGameCategoryTable BoardGameCategoryData={boardGameCategory}/>
                </Grid>
            </Box>
        );
    }
}

export default BoardGameCategoryList;