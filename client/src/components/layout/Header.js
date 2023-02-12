import classes from "./Header.module.css";

const Header = () => {
  return (
    <div className={classes.header}>
      <div className={classes.brown}></div>
      <img src= {process.env.PUBLIC_URL + "/img/logo.png"} alt="logo" className={classes.img}></img>
      <div className={classes.brown}></div>
    </div>
  );
}

export default Header;