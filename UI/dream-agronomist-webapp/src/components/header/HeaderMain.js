import {SlimHeader} from "./SlimHeader";
import {CenterHeader} from "./CenterHeader";
import {Headers} from "design-react-kit";
import React from "react";
import {useLocation} from "react-router-dom";
import {NavHeaderAgronomist} from "./NavHeaderAgronomist";

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
     * in login page
     */
    const centerHeader=()=>{
        if(location!=="/signin"){
            return(
                <CenterHeader theme="" />
            )
        }
    }
    /**
     * The nav part of the header. It is not shown
     * in login and in the landing page
     */
    const navHeader=()=>{
        if(location!=="/signin"&&location!=="/")
        return(
            <NavHeaderAgronomist theme="" />
        )
    }
    /**
     * Render part
     */
    return(
        <Headers>
            <SlimHeader token={props.token} agronomist={props.agronomist} setTokens={props.setTokens} theme="" />
            <div className="it-nav-wrapper">
                {centerHeader()}
                {navHeader()}
            </div>
        </Headers>
    )

}