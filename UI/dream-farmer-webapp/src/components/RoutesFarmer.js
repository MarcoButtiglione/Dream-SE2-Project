import {Route, Routes,Navigate } from "react-router-dom";
import {LandingPage} from "./pages/landing-page/LandingPage";
import SignInPage from "./pages/sign-in/SignInPage";
import SignUpPage from "./pages/sign-up/SingUpPage";
import {FarmerHome} from "./pages/farmer/home/FarmerHome";
import {FarmerForum} from "./pages/farmer/forum/FarmerForum";
import {FarmerForumMyTopics} from "./pages/farmer/forum/FarmerForumMyTopics";
import {FarmerKnowledge} from "./pages/farmer/knowledge/FarmerKnowledge";
import {FarmerHelp} from "./pages/farmer/help/FarmerHelp";
import {FarmerMeeting} from "./pages/farmer/meeting/FarmerMeeting";
import {FarmerProduction} from "./pages/farmer/production/FarmerProduction";
import {ProductionProducts} from "./pages/farmer/production/ProductionProducts";
import {ProductionSoilData} from "./pages/farmer/production/ProductionSoilData";
import React from "react";
import FarmRegistration from "./pages/farm/FarmRegistration";
import NotFound from "./pages/notfound/NotFound";

/**
 * Routing system
 * There are 3 different condition:
 * - when the farmer in not logged
 * - when the farmer is logged, but he does not have a farm
 * - when the farmer is logged and have a farm
 */
function RoutesFarmer (props){
        if(props.token && props.token!=="null" && props.farmer){
            if(props.farmer.farm){
                return (
                    <Routes>
                        <Route path="/" element={<LandingPage/>}/>
                        <Route path="/signin" element={<Navigate replace to="/farmer/home"/>}/>
                        <Route path="/signup" element={<Navigate replace to="/farmer/home"/>}/>
                        <Route path="/farm" element={<Navigate replace to="/farmer/home"/>}/>
                        <Route path="/farmer/home" element={<FarmerHome/>}/>
                        <Route path="/farmer/forum" element={<FarmerForum farmer={props.farmer}/>}/>
                        <Route path="/farmer/forum/mytopics" element={<FarmerForumMyTopics farmer={props.farmer}/>}/>
                        <Route path="/farmer/knowledge" element={<FarmerKnowledge/>}/>
                        <Route path="/farmer/help" element={<FarmerHelp/>}/>
                        <Route path="/farmer/meeting" element={<FarmerMeeting/>}/>
                        <Route path="/farmer/production" element={<FarmerProduction/>}/>
                        <Route path="/farmer/production/products" element={<ProductionProducts/>}/>
                        <Route path="/farmer/production/soildata" element={<ProductionSoilData/>}/>
                        <Route path="*" element={<NotFound/>}/>
                    </Routes>
                )
            }else {
                return (
                    <Routes>
                        <Route path="/" element={<LandingPage/>}/>
                        <Route path="/signin" element={<Navigate replace to="/farm"/>}/>
                        <Route path="/signup" element={<Navigate replace to="/farm"/>}/>
                        <Route path="/farmer/*" element={<Navigate replace to="/farm"/>}/>
                        <Route path="/farm" element={<FarmRegistration getFarmer={props.getFarmer}/>}/>
                        <Route path="*" element={<NotFound/>}/>
                    </Routes>
                )
            }
        }else {
            return(
                <Routes>
                    <Route path="/" element={<LandingPage/>}/>
                    <Route path="/signin" element={<SignInPage setTokens={props.setTokens}/>}/>
                    <Route path="/signup" element={<SignUpPage/>}/>
                    <Route path="/farmer/*" element={<Navigate replace to="/signin" />}/>
                    <Route path="*" element={<NotFound/>}/>
                </Routes>
            )
        }


}
export default RoutesFarmer;