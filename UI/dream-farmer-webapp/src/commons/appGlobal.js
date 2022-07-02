import Cookies from 'js-cookie';


/**
 * A set of function used to acquire api_token and refresh_token
 * Import:
 * js-cookie, a lightweight JavaScript API for handling cookies
 * */
export default {
    apiToken: function (){
        return Cookies.get('api_token')
    },
    refreshToken: function (){
        return Cookies.get('refresh_token')
    },

}