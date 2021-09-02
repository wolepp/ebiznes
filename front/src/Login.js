import { Button, FloatingLabel, Form, FormGroup } from "react-bootstrap";
import { useForm } from "react-hook-form";
import * as yup from "yup";
import { yupResolver } from "@hookform/resolvers/yup";
import sendRequest from "./utilsMockup";

const schema = yup.object().shape({
  email: yup.string()
    .required('Email is required')
    .email("Must be a valid email address"),
  password: yup.string()
    .required('Password is required')
    .min(8, "Password must be at least 8 characters"),
})

const Login = () => {
  const { register, handleSubmit, formState: { errors } } = useForm({
    resolver: yupResolver(schema)
  });

  const onSubmit = data => {
    sendRequest('http://localhost:9000/auth/login', 'POST', data);
  }

  return (
    <Form onSubmit={handleSubmit(onSubmit)}>

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

      <Button variant="primary" type="submit">
        Log in
      </Button>

    </Form>
  );
};

export default Login;
