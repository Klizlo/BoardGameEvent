import { Box, Typography } from "@mui/material";
import AddBoardGameCategoryForm from "../../components/Add/AddBoardGameCategoryForm";

export default function AddBoardGameCategory() {
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
                Add Board Game Category
            </Typography>
            <AddBoardGameCategoryForm />
        </Box>
    );
}