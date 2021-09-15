import { Button, FloatingLabel, Form, FormGroup } from "react-bootstrap";
import { useForm } from "react-hook-form";
import * as yup from "yup";
import { yupResolver } from "@hookform/resolvers/yup";
import { useContext, useEffect, useState } from "react";
import { userContext } from "../userContext";
import { useHistory } from "react-router-dom";
import { Facebook, Google } from "react-bootstrap-icons";
import { logIn } from "../Services/UserAPI";

const schema = yup.object().shape({
  email: yup.string()
    .required('Email is required')
    .email("Must be a valid email address"),
  password: yup.string()
    .required('Password is required')
    .min(8, "Password must be at least 8 characters"),
})

const Login = () => {
  const user = useContext(userContext);
  const [error, setError] = useState("");
  const [timeoutId, setTimeoutId] = useState(0);

  const { register, handleSubmit, formState: { errors } } = useForm({
    resolver: yupResolver(schema)
  });
  const history = useHistory();

  useEffect(() => {
    if (user.state.email) {
      const id = setTimeout(() => {
        history.push('/');
      }, 3000);
      setTimeoutId(id);
    }

    return () => {
      clearTimeout(timeoutId);
    }
  }, [user, user.state, user.state.email]);


  const onSubmit = data => {
    logIn(data)
      .then(responseData => {
        if (responseData.error) {
          setError(responseData.error);
        } else {
          user.dispatch({ type: 'set-user', payload: responseData })
        }
      })
      .catch(() => {
        setError("Server unavailable")
      });
  }

  const loginGoogle = () => {
    window.location.replace(process.env.REACT_APP_GOOGLE_AUTH);
  }

  const loginFacebook = () => {
    window.location.replace(process.env.REACT_APP_FACEBOOK_AUTH);
  }

  return (
    <>
      {
        user.state.email ?
          <h3
            className='text-center my-5'
          >You are already logged in, redirecting to main page...</h3>
          :

          <>
            <Form onSubmit={handleSubmit(onSubmit)} className='border-bottom border-3 pb-4'>

              <h3 className='mb-3'>Log in</h3>
              {/* email */}
              <FormGroup>
                <FloatingLabel
                  controlId="formEmail"
                  label="Email address"
                  className="mb-3"
                >
                  <Form.Control
                    type="text"
                    placeholder="name@example.com"
                    isInvalid={errors.email}
                    {...register("email")} />
                  <Form.Control.Feedback type="invalid">{errors.email?.message}</Form.Control.Feedback>
                </FloatingLabel>
              </FormGroup>

              {/* password */}
              <FormGroup className="mb-3" controlId="formPassword">
                <FloatingLabel label="Password">
                  <Form.Control
                    type="password"
                    placeholder="Password"
                    isInvalid={errors.password}
                    aria-describedby="passwordHelpText"
                    {...register("password")}
                  />
                  <Form.Control.Feedback type="invalid">{errors.password?.message}</Form.Control.Feedback>
                </FloatingLabel>
              </FormGroup>

              {error.length > 0 &&
                <h5 className='text-center my-3 p-3 border border-3 text-danger border-danger'>{error}</h5>
              }

              <Button variant="outline-primary" type="submit" className='w-100'>
                Log in
              </Button>

            </Form>

            <div className='my-4 d-grid gap-2'>
              <Button
                onClick={loginGoogle}
                variant='outline-dark'
              >
                <Google className='mx-2 mb-1' size={18} />
                Login via Google
              </Button>
              <Button
                onClick={loginFacebook}
                variant='outline-dark'
              >
                <Facebook className='mx-2 mb-1' size={18} />
                Login via Facebook
              </Button>
            </div>
          </>
      }
    </>
  )
    ;
};

export default Login;
