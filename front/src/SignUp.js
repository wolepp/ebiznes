import { Button, FloatingLabel, Form } from "react-bootstrap";
import { useForm } from "react-hook-form";
import * as yup from "yup";
import { yupResolver } from "@hookform/resolvers/yup";
import sendRequest from "./utilsMockup";

const schema = yup.object().shape({
  email: yup.string()
    .email("Must be a valid email address")
    .required('Email is required'),
  password: yup.string()
    .min(8, "Password must be at least 8 characters")
    .required('Password is required'),
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
  const { register, handleSubmit, formState: { errors } } = useForm({
    resolver: yupResolver(schema)
  });

  const onSubmit = data => {
    const { agreement, ...dataOmitAgreement } = data;
    sendRequest('http://localhost:9000/api/user', 'POST', dataOmitAgreement);
  }

  return (
    <Form onSubmit={handleSubmit(onSubmit)}>

      {/* email */}
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

      {/* password */}
      <FloatingLabel
        controlId="formPassword"
        label="Password"
      >
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

      {/* name */}
      <FloatingLabel
        controlId="formName"
        label="Name"
        className="my-3"
      >
        <Form.Control
          type="text"
          isInvalid={errors.name}
          {...register("name")} />
        <Form.Control.Feedback type="invalid">{errors.name?.message}</Form.Control.Feedback>
      </FloatingLabel>

      {/* address */}
      <FloatingLabel
        controlId="formAddress"
        label="Address"
        className="mb-3"
      >
        <Form.Control
          type="text"
          placeholder="Address"
          isInvalid={errors.address}
          {...register("address")} />
        <Form.Control.Feedback type="invalid">{errors.address?.message}</Form.Control.Feedback>
      </FloatingLabel>

      {/* city */}
      <FloatingLabel
        controlId="formCity"
        label="ZIP code"
        className="mb-3"
      >
        <Form.Control
          type="text"
          placeholder="Zip code"
          isInvalid={errors.city}
          {...register("city")} />
        <Form.Control.Feedback type="invalid">{errors.city?.message}</Form.Control.Feedback>
      </FloatingLabel>

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
  );
};

export default SignUp;
