import Box from '@mui/material/Box';
import {Link} from "react-router-dom";
import React from 'react';
import MenuIcon from '@mui/icons-material/Menu';
import {AppBar, Avatar, Button, Container, IconButton, Menu, MenuItem, Toolbar, Tooltip, Typography} from "@mui/material";
import { authenticationService } from '../../service/authenticateService';

const NavBar = (sites) => {

    let currentUser = authenticationService.currentUserValue;

    const [anchorElNav, setAnchorElNav] = React.useState(null);
    const [anchorElUser, setAnchorElUser] = React.useState(null);
  
    const handleOpenNavMenu = (event) => {
      setAnchorElNav(event.currentTarget);
    };
    const handleOpenUserMenu = (event) => {
      setAnchorElUser(event.currentTarget);
    };
  
    const handleCloseNavMenu = () => {
      setAnchorElNav(null);
    };
  
    const handleCloseUserMenu = () => {
      setAnchorElUser(null);
    };

    const logout = () => {
        authenticationService.logout();
        window.location = '/';
      };

    return (
        <AppBar position="static" sx={{
            background: "#000000",
            color: "white"
          }}>
            <Container maxWidth="xl">
              <Toolbar disableGutters>
                <Box component="img" src={process.env.PUBLIC_URL + "logo.png"} sx={{ width: "48px", display: { xs: 'none', md: 'flex' }, mr: 1 }} />
                <Typography
                  variant="h6"
                  noWrap
                  component={Link}
                  to="/"
                  sx={{
                    mr: 2,
                    display: { xs: 'none', md: 'flex' },
                    fontFamily: 'kdam-thmor-pro',
                    fontWeight: 700,
                    letterSpacing: '.3rem',
                    color: 'inherit',
                    textDecoration: 'none',
                  }}
                >
                  Board Game Event
                </Typography>
      
                <Box sx={{ flexGrow: 1, display: { xs: 'flex', md: 'none' } }}>
                  <IconButton
                    size="large"
                    aria-label="account of current user"
                    aria-controls="menu-appbar"
                    aria-haspopup="true"
                    onClick={handleOpenNavMenu}
                    color="inherit"
                  >
                    <MenuIcon />
                  </IconButton>
                  <Menu
                    id="menu-appbar"
                    anchorEl={anchorElNav}
                    anchorOrigin={{
                      vertical: 'bottom',
                      horizontal: 'left',
                    }}
                    keepMounted
                    transformOrigin={{
                      vertical: 'top',
                      horizontal: 'left',
                    }}
                    open={Boolean(anchorElNav)}
                    onClose={handleCloseNavMenu}
                    sx={{
                      display: { xs: 'block', md: 'none' },
                    }}
                  >
                    {sites.sites.filter((site) => site.role === "none" ).map((page) => (
                      <MenuItem element={Link} to={page.link} key={page.name} onClick={handleCloseNavMenu}>
                        <Typography textAlign="center">{page.name}</Typography>
                      </MenuItem>
                    ))}
                  </Menu>
                </Box>
                <Box component="img" src="logo.png" sx={{ width: "48px", display: { xs: 'flex', md: 'none' }, mr: 1 }} />
                <Typography
                  variant="h5"
                  noWrap
                  component={Link}
                  to="/"
                  sx={{
                    mr: 2,
                    display: { xs: 'flex', md: 'none' },
                    flexGrow: 1,
                    fontFamily: 'kdam-thmor-pro',
                    fontWeight: 700,
                    letterSpacing: '.1rem',
                    color: 'inherit',
                    textDecoration: 'none',
                  }}
                >
                  Board Game Event
                </Typography>
                <Box sx={{ flexGrow: 1, display: { xs: 'none', md: 'flex' } }}>
                  {sites.sites.filter((site) => !currentUser ? site.role === "none" : site.role === "none" || currentUser.user.roles.map(role => role.name).includes(site.role) )
                    .map((page) => (
                    <Button
                      component={Link} to={page.link}
                      key={page.name}
                      onClick={handleCloseNavMenu}
                      sx={{ my: 2, color: 'white', display: 'block' }}
                    >
                      {page.name}
                    </Button>
                  ))}
                </Box>
                { !currentUser ? (
                    <Box sx={{ flexGrow: 0 }}>
                        <Button
                      component={Link} to='/login'
                      key='login'
                      sx={{ my: 2, color: 'white', display: 'block' }}
                        >
                            Sign In
                        </Button>
                    </Box>
                ) : (
                    <Box sx={{ flexGrow: 0 }}>
                  <Tooltip title="Open settings">
                    <IconButton onClick={handleOpenUserMenu} sx={{ p: 0 }}>
                      <Avatar alt="Remy Sharp" src="/static/images/avatar/2.jpg" />
                    </IconButton>
                  </Tooltip>
                  <Menu
                    sx={{ mt: '45px' }}
                    id="menu-appbar"
                    anchorEl={anchorElUser}
                    anchorOrigin={{
                      vertical: 'top',
                      horizontal: 'right',
                    }}
                    keepMounted
                    transformOrigin={{
                      vertical: 'top',
                      horizontal: 'right',
                    }}
                    open={Boolean(anchorElUser)}
                    onClose={handleCloseUserMenu}
                  >
                    <MenuItem key='logout' onClick={logout}>
                      <Typography textAlign="center">Sign out</Typography>
                    </MenuItem>
                  </Menu>
                </Box>
                )}
              </Toolbar>
              
            </Container>
          </AppBar>
      );
};

export default NavBar