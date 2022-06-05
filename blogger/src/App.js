import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Auth from "./Components/Auth/Auth";
import Blog from "./Components/Blog";
import Home from "./Components/home/Home";
import Header from "./Components/Header";
import "./Assets/Styles/main.css"
import { useState } from "react";

function App() {

  let [user, updateUser] = useState(() => undefined);

  const updateCurrentUser = (newUser) => {
    updateUser(newUser);
  };

  return (
    <div className="App">
      <Header user={user} updateUser={updateCurrentUser} />
      <Router>
        <Routes>
          <Route path="/auth/*" exact
            element={<Auth updateUser={updateCurrentUser} />}
          />
          <Route path="/blog" exact
            element={<Blog />}
          />
          <Route path="/" exact
            element={<Home user={user} />}
          />
        </Routes>
      </Router>
    </div>
  );
}

export default App;
