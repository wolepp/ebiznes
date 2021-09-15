import { useContext, useEffect } from "react";
import { userContext } from "../userContext";
import { useHistory } from "react-router-dom";
import sendRequest from "../utils";

const SIGNOUT_ENDPOINT = process.env.REACT_APP_API_URL + '/signOut'

const Login = () => {
  const user = useContext(userContext);
  const history = useHistory();

  useEffect(async () => {
    if (user.state.email) {
      user.dispatch({type: 'clear-user'});
      await sendRequest(SIGNOUT_ENDPOINT)
    }
    history.push('/')
  }, []);

  return (
    <>
    </>
  );
};

export default Login;
