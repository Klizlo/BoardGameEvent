import Box from '@mui/material/Box';
import {useNavigate} from "react-router-dom";
import React from 'react';
import {Button, ButtonGroup} from "@mui/material";

const NavBar = (sites) => {
    let token = true;
    const navigate = useNavigate();
    return (
        <Box sx={{
            width: '100%',
            bgcolor: 'background.paper',
        }}>
            <ButtonGroup
                variant="outlined"
                sx={{
                    p: 2,
                    position: 'center'
                }}

            >
                {sites.sites.map((site) => {
                    if (site.visible) {
                        return (
                            <Button variant={'contained'} color={"info"} key={site.name} onClick={() => {
                                navigate(site.link)
                            }
                            }>{site.name}</Button>
                        );
                    }
                })}
                {token ? (
                    <Button
                        variant={'contained'}
                        color={"warning"}
                        key={"logOut"}
                        onClick={() => {
                            token = false
                            navigate("/")
                        }}>
                        LogOut
                    </Button>
                ) : (
                    <Button
                        variant={'contained'}
                        color={"warning"}
                        key={"logIn"}
                        onClick={() => {
                            token = true
                            navigate("/")
                        }}>
                        LogIn
                    </Button>
                )}
            </ButtonGroup>
        </Box>
    );
};

export default NavBar