import React from "react";
import { Link } from "react-router-dom";

import ImageLight from "../assets/img/ekart-logo.png";
import ImageDark from "../assets/img/ekart-logo1.png";
import { GithubIcon, TwitterIcon } from "../icons";
import { Label, Input, Button } from "@windmill/react-ui";
import axios from "axios";
import { useHistory } from "react-router-dom";

const handleLogin = async (creds, history) => {
  try {
    const response = await axios.post(
      "http://localhost:8080/api/auth/login",
      creds
    );
    if (response.data && response.data.jwtToken) {
      localStorage.setItem("jwtToken", response.data.jwtToken);
      history.push("/app");
    } else {
      alert("Invalid credentials");
    }
  } catch (error) {
    console.error("Login failed:", error);
    alert("An error occurred. Please try again.");
  }
};

const Login = () => {
  const history = useHistory();
  return (
    <div className="flex items-center min-h-screen p-6 bg-gray-50 dark:bg-gray-900">
      <div className="flex-1 h-full max-w-4xl mx-auto overflow-hidden bg-white rounded-lg shadow-xl dark:bg-gray-800">
        <div className="flex flex-col overflow-y-auto md:flex-row">
          <div className="h-32 md:h-auto md:w-1/2">
            <img
              aria-hidden="true"
              className="object-cover w-full h-full dark:hidden"
              src={ImageLight}
              alt="e-kart"
            />
            <img
              aria-hidden="true"
              className="hidden object-cover w-auto h-auto dark:block"
              src={ImageDark}
              alt="e-kart"
            />
          </div>
          <main className="flex items-center justify-center p-6 sm:p-12 md:w-1/2">
            <div className="w-full">
              <h1 className="mb-4 text-xl font-semibold text-gray-700 dark:text-gray-200">
                Admin Login
              </h1>
              <form
                onSubmit={(event) => {
                  event.preventDefault();
                  const formElements = event.currentTarget.elements;
                  const data = {
                    email: formElements.email.value,
                    password: formElements.password.value,
                  };
                  handleLogin(data, history);
                }}
              >
                <Label>
                  <span>Email</span>
                  <Input className="mt-1" type="email" name="email" required />
                </Label>

                <Label className="mt-4">
                  <span>Password</span>
                  <Input
                    className="mt-1"
                    type="password"
                    name="password"
                    required
                  />
                </Label>

                <Button
                  type="submit"
                  className="mt-4"
                  block
                  /* tag={Link}
                  to="/app" */
                >
                  Log in
                </Button>
              </form>

              <p className="mt-4">
                <Link
                  className="text-sm font-medium text-purple-600 dark:text-purple-400 hover:underline"
                  to="/forgot-password"
                >
                  Forgot your password?
                </Link>
              </p>
              {/* <p className="mt-1">
                <Link
                  className="text-sm font-medium text-purple-600 dark:text-purple-400 hover:underline"
                  to="/create-account"
                >
                  Create account
                </Link>
              </p> */}
            </div>
          </main>
        </div>
      </div>
    </div>
  );
};
export default Login;
