import { Button, FloatingLabel, Form, FormGroup } from "react-bootstrap";
import { useForm } from "react-hook-form";
import * as yup from "yup";
import { yupResolver } from "@hookform/resolvers/yup";
import { useContext, useEffect } from "react";
import { userContext } from "../userContext";
import { useHistory } from "react-router-dom";
import { signUp } from "../Services/UserAPI";

const schema = yup.object().shape({
  email: yup.string()
    .required('Email is required')
    .email("Must be a valid email address"),
  password: yup.string()
    .required('Password is required')
    .min(8, "Password must be at least 8 characters"),
  name: yup.string()
    .required('Name is required'),
  address: yup.string()
    .required('Address is required'),
  city: yup.string()
    .required('Zip code is required'),
  agreement: yup.boolean()
    .oneOf([true], 'Accepting lorem ipsum is required')
})

const SignUp = () => {
  const user = useContext(userContext);

  const { register, handleSubmit, formState: { errors } } = useForm({
    resolver: yupResolver(schema)
  });

  const history = useHistory();

  useEffect(() => {
    if (user.state.email) {
      setTimeout(() => {
        history.push('/');
      }, 3000);
    }
  }, [user, user.state, user.state.email]);


  const onSubmit = data => {
    const { agreement, ...dataOmitAgreement } = data;
    signUp(dataOmitAgreement)
      .then(responseData => {
        user.dispatch({ type: 'set-user', payload: responseData })
        history.push('/')
      });
  }

  return (
    <>
      {
        user.state.email ?
          <h3
            className='text-center my-5'
          >You are already logged in, redirecting to main page...</h3>
          :
          <Form onSubmit={handleSubmit(onSubmit)}>

            {/* email */}
            <FormGroup controlId="formEmail" className="mb-3">
              <FloatingLabel label="Email address">
                <Form.Control
                  type="text"
                  placeholder="name@example.com"
                  isInvalid={errors.email}
                  {...register("email")} />
                <Form.Control.Feedback type="invalid">{errors.email?.message}</Form.Control.Feedback>
              </FloatingLabel>
            </FormGroup>

            {/* password */}
            <FormGroup controlId="formPassword" className="mb-3">
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
              {!errors.password &&
              <Form.Text id="passwordHelpText" muted>
                Your password must be at least 8 characters long
              </Form.Text>
              }
            </FormGroup>

            {/* name */}
            <FormGroup controlId="formName" className="my-3">
              <FloatingLabel label="Name">
                <Form.Control
                  type="text"
                  placeholder="Name"
                  isInvalid={errors.name}
                  {...register("name")} />
                <Form.Control.Feedback type="invalid">{errors.name?.message}</Form.Control.Feedback>
              </FloatingLabel>
            </FormGroup>

            {/* address */}
            <FormGroup controlId="formAddress" className="mb-3">
              <FloatingLabel label="Address">
                <Form.Control
                  type="text"
                  placeholder="Address"
                  isInvalid={errors.address}
                  {...register("address")} />
                <Form.Control.Feedback type="invalid">{errors.address?.message}</Form.Control.Feedback>
              </FloatingLabel>
            </FormGroup>

            {/* city */}
            <FormGroup controlId="formCity" className="mb-3">
              <FloatingLabel label="ZIP code">
                <Form.Control
                  type="text"
                  placeholder="Zip code"
                  isInvalid={errors.city}
                  {...register("city")} />
                <Form.Control.Feedback type="invalid">{errors.city?.message}</Form.Control.Feedback>
              </FloatingLabel>
            </FormGroup>

            {/* agreement */}
            <Form.Group className="mb-3" controlId="formAgreement">
              <Form.Check
                type="checkbox"
                label="I agree to lorem ipsum"
                isInvalid={errors.agreement}
                {...register("agreement")} />
            </Form.Group>

            <Button variant="primary" type="submit">
              Submit
            </Button>

          </Form>
      }
    </>
  );
};

export default SignUp;
