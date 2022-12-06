import { Box, Grid, Typography } from "@mui/material";
import { useEffect, useState } from "react";
import Variables from "../../components/Globals/Variables";
import ProducerTable from "../../components/Tables/ProducerListTable";

const ProducerList = () => {

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
                        Producers
                    </Typography>
                    <ProducerTable ProducerData={producers}/>
                </Grid>
            </Box>
        );
    }
}

export default ProducerList;