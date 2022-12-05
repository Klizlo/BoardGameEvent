import { LockOutlined } from "@mui/icons-material";
import { Avatar, Box, Container, Grid, Paper,
     Typography, TextField} from "@mui/material";
import Link from '@mui/material/Link';
import LoadingButton from '@mui/lab/LoadingButton';
import { useState } from "react";
import SaveIcon from '@mui/icons-material/Send';
import { authenticationService } from "../../service/authenticateService";

export default function HomePage() {
    const [data, setData] = useState({username: "", password: ""});
    const [loading, setLoading] = useState(false);

    const handleChange = (e) => {
        setData({...data, [e.target.name]: e.target.value});
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        authenticationService.login(data.username, data.password)
        .then(response => window.location='/boardGames');
        setLoading(false);
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
                    backgroundImage: 'url(https://images82.fotosik.pl/823/06f08c7546399cb2.png)',
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
                            <LockOutlined />
                        </Avatar>
                        <Typography
                            variant="h5"
                            sx={{
                                fontFamily: 'kdam-thmor-pro',
                                fontWeight: 700,
                                letterSpacing: '.3rem',
                            }}
                        >
                            Sing In
                        </Typography>
                        <Box component="form" method="POST" onSubmit={handleSubmit} sx={{ mt: 1 }}>
                            <TextField
                                margin="normal"
                                required
                                fullWidth
                                id="username"
                                onChange={handleChange}
                                label="Username"
                                name="username"
                                value={data.username}
                                autoComplete="username"
                                autoFocus
                            />
                            <TextField
                                margin="normal"
                                required
                                fullWidth
                                name="password"
                                onChange={handleChange}
                                value={data.password}
                                label="Password"
                                type="password"
                                id="password"
                                autoComplete="current-password"
                            />
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
                               Sing in
                            </LoadingButton>
                            <Grid container>
                                <Grid item>
                                <Link href="/signup" variant="body2" color='#fe2800'>
                                    {"Sign Up"}
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