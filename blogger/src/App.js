import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Auth from "./Components/Auth/Auth";
import Home from "./Components/home/Home";
import Header from "./Components/Header";
import "./Assets/Styles/main.css"
import { useState } from "react";
import BlogEdit from "./Components/blogedit/BlogEdit";
import BlogView from "./Components/BlogView";

function App() {

  let [user, updateUser] = useState(() => undefined);
  const defaultHeader = 'Blogger';
  let [headerText, updateHeaderText] = useState(() => defaultHeader);

  const updateCurrentUser = (newUser) => {
    updateUser(newUser);
  };

  const resetHeader = () => {
      updateHeaderText(defaultHeader);

  }

  return (
    <div className="App">
      <Header user={user} updateUser={updateCurrentUser} headerText={headerText} />
      <Router>
        <Routes>
          <Route path="/auth/*" exact
            element={<Auth updateUser={updateCurrentUser} />}
          />
          <Route path="/" exact
            element={<Home user={user} />}
          />
          <Route path="/create" exact
            element={<BlogEdit user={user} blog={{title: '', description: '', user: '', content: ''}} />}
          />
          <Route path="/view/:blogId" exact
            element={<BlogView user={user} updateHeader={updateHeaderText} resetHeader={resetHeader} />}
          />
        </Routes>
      </Router>
    </div>
  );
}

export default App;
