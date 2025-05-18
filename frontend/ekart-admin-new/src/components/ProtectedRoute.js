import React from 'react';
import {useEffect, useState} from 'react';
import {useHistory} from 'react-router-dom';

function ProtectedRoute({children}) {
    const history = useHistory();
    const [isAuthenticated, setIsAuthenticated] = useState(null);

    useEffect(() => {
        const jwtToken = localStorage.getItem('jwtToken');
        if (!jwtToken) {
            history.push('/login');
        } else {
            setIsAuthenticated(true);
        }
    }, [history]);

    if (isAuthenticated === null) return <div>Checking authentication...</div>;

    return children;
}

export default ProtectedRoute;
