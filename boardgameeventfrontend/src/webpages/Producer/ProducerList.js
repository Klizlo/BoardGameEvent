import { Box, Button, Grid, IconButton, Typography } from "@mui/material";
import { useEffect, useState } from "react";
import Variables from "../../components/Globals/Variables";
import ProducerTable from "../../components/Tables/ProducerListTable";
import { Role } from "../../helpers/role";
import { authenticationService } from "../../service/authenticateService";

const ProducerList = () => {

    const currentUser = authenticationService.currentUserValue;

    const [error, setError] = useState(null);
    const [isLoaded, setIsLoaded] = useState(false);
    const [producers, setProducers] = useState([]);
    const endpoint = Variables.API + '/producers';
    useEffect(() => {
        fetch(endpoint, {
            method: 'GET',
        })
            .then(res => res.json())
            .then(
                (data) => {
                    setIsLoaded(true);
                    setProducers(data);
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
                       Producers
                    </Typography>
                    { currentUser && currentUser.user.roles.map((role) => role.name).includes(Role.Admin) ? (
                        <Box>
                            <Button
                                onClick={() => {window.location = '/producers/add'}}    
                            >
                                Add Producer
                            </Button>
                        </Box>
                    ) : (
                        <></>
                    )}
                    <ProducerTable ProducerData={producers}/>
                </Grid>
            </Box>
        );
    }
}

export default ProducerList;