import React from "react";
import { Navigate, Route } from "react-router-dom";
import { authenticationService } from "../service/authenticateService";

export const Guard = ({component: children, roles}) => {
    var currentUser = authenticationService.currentUser;
    console.log(currentUser);
    if(!currentUser){
        return <Navigate to={{ pathname: '/login' }} />
    }

    if (roles && roles.indexOf(currentUser.role) === -1) {
        return <Navigate to={{pathname: '/'}} />
    }

    return children;
}
        

