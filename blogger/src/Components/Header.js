import "../Assets/Styles/header.css";


const Header = (props) => {
  // const display = {
  //   color : 'red',
  // }; 
    return(
      <div className="header">
        <div className='heading'>Blogger</div>  
        <a href="/auth" className = 'logout'>LogOut</a>

    </div>
    )

};


export default Header;