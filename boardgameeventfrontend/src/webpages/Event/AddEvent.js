import { Box, Typography } from "@mui/material";
import AddEventForm from "../../components/Add/AddEventForm";
export default function AddEvent() {

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
                Add Event
            </Typography>
            <AddEventForm />
        </Box>
    );
}