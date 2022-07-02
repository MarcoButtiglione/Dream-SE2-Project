import {Route, Routes, Navigate} from "react-router-dom";
import {LandingPage} from "./pages/landing-page/LandingPage";
import SignInPage from "./pages/sign-in/SignInPage";
import React from "react";
import {AgronomistHome} from "./pages/agronomist/home/AgronomistHome";
import {AgronomistKnowledge} from "./pages/agronomist/knowledge/AgronomistKnowledge";
import {AgronomistHelp} from "./pages/agronomist/help/AgronomistHelp";
import {AgronomistMeeting} from "./pages/agronomist/meeting/AgronomistMeeting";
import AgronomistFarmers from "./pages/agronomist/farmers/AgronomistFarmers";

/**
 * Routing system
 * There are 2 different condition:
 * - when the agronomist in not logged
 * - when the farmer is logged
 */
function RoutesAgronomist (props){
        if(props.token && props.token!=="null" && props.agronomist){
            return(
                <Routes>
                    <Route path="/" element={<LandingPage/>}/>
                    <Route path="/signin" element={<Navigate replace to="/agronomist/home" />}/>
                    <Route path="/agronomist/home" element={<AgronomistHome/>}/>
                    <Route path="/agronomist/knowledge" element={<AgronomistKnowledge/>}/>
                    <Route path="/agronomist/help" element={<AgronomistHelp/>}/>
                    <Route path="/agronomist/meeting" element={<AgronomistMeeting/>}/>
                    <Route path="/agronomist/farmers" element={<AgronomistFarmers/>}/>
                    <Route path="*" element={<div>404</div>}/>
                </Routes>
            )
        }else {
            return(
                <Routes>
                    <Route path="/" element={<LandingPage/>}/>
                    <Route path="/signin" element={<SignInPage setTokens={props.setTokens}/>}/>
                    <Route path="/agronomist/*" element={<Navigate replace to="/signin" />}/>
                    <Route path="*" element={<div>404</div>}/>
                </Routes>
            )
        }


}
export default RoutesAgronomist;