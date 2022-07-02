import {SlimHeader} from "./SlimHeader";
import {CenterHeader} from "./CenterHeader";
import {NavHeaderFarmer} from "./NavHeaderFarmer";
import {Headers} from "design-react-kit";
import React from "react";
import {useLocation} from "react-router-dom";

/**
 * Header component
 */
export function HeaderMain (props) {
    /**
     * Hooks
     */
    const location =useLocation().pathname
    /**
     * The central part of the header. It is not shown
     * in login and registration page
     */
    const centerHeader=()=>{
        if(location!=="/signin"&&location!=="/signup"){
            return(
                <CenterHeader theme="" />
            )
        }
    }
    /**
     * The nav part of the header. It is not shown
     * in login, registration page and in the landing page
     */
    const navHeader=()=>{
        if(location!=="/signin"&&location!=="/signup"&&location!=="/")
        return(
            <NavHeaderFarmer theme="" />
        )
    }
    /**
     * Render part
     */
    return(
        <Headers>
            <SlimHeader token={props.token} farmer={props.farmer} setTokens={props.setTokens} theme="" />
            <div className="it-nav-wrapper">
                {centerHeader()}
                {navHeader()}
            </div>
        </Headers>
    )

}