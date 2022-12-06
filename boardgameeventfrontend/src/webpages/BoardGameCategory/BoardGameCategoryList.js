import { Box, Button, Grid, IconButton, Typography } from "@mui/material";
import { useEffect, useState } from "react";
import Variables from "../../components/Globals/Variables";
import BoardGameCategoryTable from "../../components/Tables/BoardGameCategoryTable";
import { Role } from "../../helpers/role";
import { authenticationService } from "../../service/authenticateService";

const BoardGameCategoryList = () => {

    const currentUser = authenticationService.currentUserValue;

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
                       Board Game Categories
                    </Typography>
                    { currentUser && currentUser.user.roles.map((role) => role.name).includes(Role.Admin) ? (
                        <Box>
                            <Button
                                onClick={() => {window.location = '/boardGamesCategories/add'}}    
                            >
                                Add Board Game Category
                            </Button>
                        </Box>
                    ) : (
                        <></>
                    )}
                    <BoardGameCategoryTable BoardGameCategoryData={boardGameCategory}/>
                </Grid>
            </Box>
        );
    }
}

export default BoardGameCategoryList;