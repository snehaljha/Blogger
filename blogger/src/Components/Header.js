import "../Assets/Styles/header.css";


const Header = (props) => {
  const displayStyle = {
    display: props.user === undefined ? 'none' : 'block'
  };

  const justifyStyle = {
    justifyContent: props.user === undefined ? 'center' : 'space-between'
  };

  const logOut = () => {
    props.updateUser(undefined);
  };

  const createBlog = () => {
    console.log('Navigate to create blog link')
  };

  return(
    <div className="header" style={justifyStyle} onClick={createBlog}>
      <div className="spacer" style={displayStyle}>+</div>
      <div className='heading'>Blogger</div>
      <div className='logout' style={displayStyle} onClick={logOut}>LogOut</div>

    </div>
  )

};


export default Header;