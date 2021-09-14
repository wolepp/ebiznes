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
      await sendRequest(SIGNOUT_ENDPOINT, 'get')
      user.dispatch({type: 'clear-user'});
    }
    history.push('/')
  }, []);

  return (
    <>
    </>
  );
};

export default Login;
