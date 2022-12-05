import { Box, Typography } from "@mui/material";
import AddProducerForm from "../../components/Add/AddProducerForm";

export default function AddProducer() {
    return (
        <Box
        sx={{
            margin: '2%'
        }}>
            <Typography
                variant="h5"
                sx={{
                    fontFamily: 'kdam-thmor-pro',
                    fontWeight: 700,
                    letterSpacing: '.3rem',
                    color: 'white'
                }}
            >
                Add Producer
            </Typography>
            <AddProducerForm />
        </Box>
    );
}