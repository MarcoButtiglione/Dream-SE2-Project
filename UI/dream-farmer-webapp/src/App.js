import 'bootstrap-italia/dist/css/bootstrap-italia.min.css';
import 'typeface-titillium-web';
import 'typeface-roboto-mono';
import 'typeface-lora';
import React, {useEffect, useRef, useState} from 'react';
import {FooterMain} from "./components/footer/FooterMain";
import {BrowserRouter} from "react-router-dom";
import {NotificationManager, notify} from "design-react-kit";
import RoutesFarmer from "./components/RoutesFarmer";
import {HeaderMain} from "./components/header/HeaderMain";
import useCookie from 'react-use-cookie';
import ApiClient from "./commons/apiClient";
import appGlobal from "./commons/appGlobal";

/**
 * The main React Function
 */
function App(){
    /**
     * Stases and some Hookw
     */
    const [userToken, setUserToken] = useCookie('api_token');
    const [refreshToken, setRefreshToken] = useCookie('refresh_token');
    const [farmer,setFarmer]=useState(null)
    const client = new ApiClient()

    /**
     * Run when userTaken changed
     */
    useEffect(()=>{
        if(userToken) {
            if(userToken!=="null"){
                getFarmer()
            }

        }
    }, [userToken])
    /**
     * API call
     */
    const getFarmer=()=>{
        if(appGlobal.apiToken()!=="mull"){
            client.GetFarmerF()
                .then((response) => {
                    if(response.data.code===200){
                        setFarmer(response.data.results[0])
                    }else if(response.data.code===404){
                        notify(
                            'Warning',
                            response.data.message,
                            {state:"warning"}
                        )
                    }else if(response.data.code===400){
                        notify(
                            'Warning',
                            response.data.message,
                            {state:"warning"}
                        )
                    }
                })
                .catch((error) => {
                    notify(
                        'Error',
                        'Something went wrong!',
                        {state:"error"}
                    )
                });
        }
    }
    /**
     * Set tokens function used when login
     */
    const setTokens=(aT,rT)=>{
        if(aT===null&&rT===null){
            setFarmer(null)
        }
        setUserToken(aT)
        setRefreshToken(rT)
    }
    /**
     * Render part
     */
    return (
        <div>
            <BrowserRouter>
                    <HeaderMain
                        token={userToken}
                        farmer={farmer}
                        setTokens={setTokens}
                    />
                    <RoutesFarmer
                        setTokens={setTokens}
                        token={userToken}
                        farmer={farmer}
                        getFarmer={getFarmer}
                    />
                    <FooterMain/>
                </BrowserRouter>
            <NotificationManager/>
        </div>
    )

}
export default App;
