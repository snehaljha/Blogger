import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Auth from "./Components/Auth/Auth";
import Blog from "./Components/Blog";
import Home from "./Components/Home";


function App() {
  return (
    <div className="App">
      App

      <Router>
        <Routes>
          <Route path="/auth" exact
            element={<Auth />}
          />
          <Route path="/blog" exact
            element={<Blog />}
          />
          <Route path="/" exact
            element={<Home />}
          />
        </Routes>
      </Router>
    </div>
  );
}

export default App;
