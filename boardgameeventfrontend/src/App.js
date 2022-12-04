import './App.css';
import {createTheme, ThemeProvider} from "@mui/material";
import WebPages from "./webpages/WebPages";
import React from 'react';
import { authenticationService } from './service/authenticateService';
import { Role } from './helpers/role';

const darkTheme = createTheme({
  palette: {
    mode: "dark",
  },
});

class App extends React.Component {

  constructor(props) {
    super(props);

    this.state = {
        currentUser: null,
        isAdmin: false
    };
  }

  componentDidMount() {
      authenticationService.currentUser.subscribe(x => this.setState({
          currentUser: x,
          isAdmin: x && x.role === Role.Admin
      }));
  }

  logout() {
      authenticationService.logout();
  }

  render() {
    const { currentUser, isAdmin } = this.state;
    return (
      <ThemeProvider theme={darkTheme}>
        <WebPages/>
      </ThemeProvider>
    );
  }
}

export default App;
