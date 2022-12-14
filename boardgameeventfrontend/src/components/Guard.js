import React from "react";
import { Navigate } from "react-router-dom";
import { authenticationService } from "../service/authenticateService";

export const Guard = ({component: children, roles}) => {
    let currentUser = authenticationService.currentUserValue;
    if(!currentUser){
        return <Navigate to={{pathname: "/login"}} />
    }

    if (roles && !checkRole(roles, currentUser)) {
        return <Navigate to={{pathname: '/'}} />
    }

    return children;
}
        
const checkRole = (roles, currentUser) => {
    if(currentUser === null){
        return false;
    }
    return roles.some((role) => currentUser.user.roles.map(role => role.name).includes(role)) === true;

}
