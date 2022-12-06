import AccountCircleIcon from "@mui/icons-material/AccountCircle";
import SaveIcon from '@mui/icons-material/Send';
import { LoadingButton } from "@mui/lab";
import { Alert, Avatar, Box, Container, Grid, Paper, TextField, Typography } from "@mui/material";
import { useState } from "react";
import { Link } from "react-router-dom";
import { authenticationService } from "../../service/authenticateService";

export default function RegisterPage() {

    const [data, setData] = useState({
        username: "",
        email: "",
        password: "",
        password_confirmation: ""
    });
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(false);

    const handleChange = (e) => {
        setData({...data, [e.target.name]: e.target.value});
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        setLoading(true);
        if(data.password !== data.password_confirmation){
            setError("Passwords have to be the same.");
            setLoading(false);
        } else {
            authenticationService.register(data.username, data.email, data.password)
            .then((response) => {
                window.location = '/boardGames';
            })
            .catch((reject) => {
                setError(reject.msg);
            });
            setLoading(false);
        }
    };

    return (
        <Container>
            <Grid container component="main" sx={{ height: '90vh', padding: '2% 0' }}>
                <Grid 
                item
                xs={false}
                sm={4}
                md={7}
                sx={{
                    backgroundImage: 'url(https://munchkin.game/site-munchkin/assets/files/3637/munchkins-and-mazes-cards.png)',
                    backgroundRepeat: 'no-repeat',
                    backgroundColor: (t) =>
                    t.palette.mode === 'light' ? t.palette.grey[50] : t.palette.grey[900],
                    backgroundSize: 'cover',
                    backgroundPosition: 'center',
                }}/>
                <Grid item xs={12} sm={8} md={5} component={Paper} elevation={6} square>
                    <Box
                        sx={{
                            my: 8,
                            mx: 4,
                            display: 'flex',
                            flexDirection: 'column',
                            alignItems: 'center',
                          }}>
                        <Avatar sx={{ m: 1, bgcolor: '#fe2800' }}>
                            <AccountCircleIcon />
                        </Avatar>
                        <Typography
                            variant="h5"
                            sx={{
                                fontFamily: 'kdam-thmor-pro',
                                fontWeight: 700,
                                letterSpacing: '.3rem',
                            }}
                        >
                            Sign up
                        </Typography>
                        <Box component="form" method="POST" onSubmit={handleSubmit} sx={{ mt: 1 }}>
                            <TextField
                                margin="normal"
                                inputProps={{
                                    maxLength: 255,
                                  }}
                                required
                                fullWidth
                                id="name"
                                onChange={handleChange}
                                label="Username"
                                name="username"
                                value={data.username}
                                autoFocus
                            />
                            <TextField
                                margin="normal"
                                required
                                fullWidth
                                id="email"
                                onChange={handleChange}
                                label="Email"
                                name="email"
                                value={data.email}
                                autoComplete="email"
                            />
                            <TextField
                                margin="normal"
                                required
                                fullWidth
                                name="password"
                                onChange={handleChange}
                                value={data.password}
                                label="password"
                                type="password"
                                id="password"
                                autoComplete="current-password"
                            />
                            <TextField
                                margin="normal"
                                required
                                fullWidth
                                name="password_confirmation"
                                onChange={handleChange}
                                value={data.password_confirmation}
                                label="Password confirmation"
                                type="password"
                                id="password_confirmation"
                                autoComplete="current-password"
                            />
                            {error && <Alert severity="error">{error}</Alert>}
                            <LoadingButton
                                type="submit"
                                fullWidth
                                loading={loading}
                                loadingPosition="start"
                                startIcon={<SaveIcon />}
                                variant="contained"
                                sx={{ 
                                    mt: 3,
                                    mb: 2,
                                    background: '#fe2800',
                                    '&:hover': {
                                        backgroundColor: 'black'
                                    }
                                 }}
                            >
                                Sign up
                            </LoadingButton>
                            <Grid container>
                                <Grid item>
                                <Link href="/logIn" variant="body2" color='#fe2800'>
                                    {"Sing in"}
                                </Link>
                                </Grid>
                            </Grid>
                        </Box>
                    </Box>
                </Grid>
            </Grid>
        </Container>
    );
}