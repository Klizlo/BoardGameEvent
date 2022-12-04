import { authenticationService } from "../service/authenticateService";

export function authHeader() {
    // return authorization header with jwt token
    const currentUser = authenticationService.currentUser;
    if (currentUser && currentUser.token) {
        return { Authorization: `Bearer ${currentUser.token}` };
    } else {
        return {};
    }
}