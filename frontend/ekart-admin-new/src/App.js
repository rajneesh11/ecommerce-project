import React, {lazy} from "react";
import {
    BrowserRouter as Router,
    Switch,
    Route,
    Redirect,
} from "react-router-dom";
import AccessibleNavigationAnnouncer from "./components/AccessibleNavigationAnnouncer";
import ProtectedRoute from "./components/ProtectedRoute";

const Layout = lazy(() => import("./containers/Layout"));
const Login = lazy(() => import("./pages/Login"));
const CreateAccount = lazy(() => import("./pages/CreateAccount"));
const ForgotPassword = lazy(() => import("./pages/ForgotPassword"));

function App() {
    return (
        <>
            <Router>
                <AccessibleNavigationAnnouncer/>
                <Switch>
                    <Route path="/login" component={Login}/>
                    <Route path="/create-account" component={CreateAccount}/>
                    <Route path="/forgot-password" component={ForgotPassword}/>

                    {/* Protected Route */}
                    <Route
                        path="/app"
                        render={() => (
                            <ProtectedRoute>
                                <Layout/>
                            </ProtectedRoute>
                        )}
                    />

                    {/* If you have an index page, you can remove this Redirect */}
                    <Redirect exact from="*" to="/login"/>
                </Switch>
            </Router>
        </>
    );
}

export default App;
