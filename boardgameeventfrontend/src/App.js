import './App.css';
import {createTheme, ThemeProvider} from "@mui/material";
import WebPages from "./webpages/WebPages";

const darkTheme = createTheme({
  palette: {
    mode: "dark",
  },
});

function App() {
  return (
    <ThemeProvider theme={darkTheme}>
      <WebPages/>
    </ThemeProvider>
  );
}

export default App;
