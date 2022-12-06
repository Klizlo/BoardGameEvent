import Variables from "../components/Globals/Variables"
import { authHeader } from "../helpers/auth-header"
import { handleResponse } from "../helpers/handleResponse"

export const eventService = {
    addPlayer,
}

function addPlayer (eventId, player) {
    return fetch(`${Variables.API}/events/${eventId}/players`,{
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'Authorization': authHeader().Authorization
        },
        body: JSON.stringify(player)
    })
    .then(handleResponse)
    .then(response => {
        return response;
    });
}