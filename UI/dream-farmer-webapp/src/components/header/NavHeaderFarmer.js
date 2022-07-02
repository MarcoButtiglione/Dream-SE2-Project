import {
    Collapse,
    Header,
    HeaderContent,
    HeaderToggler,
    Icon,
    Nav,
    NavItem,
    NavLink
} from "design-react-kit";
import { useNavigate,useLocation } from 'react-router-dom';
import {useState} from "react";
/**
 * Navigation header
 * It is used to change the different tabs and it could be also
 * collapsed in the mobile view
 */
export function NavHeaderFarmer () {
    /**
     * Hooks
     */
    const [collapse, setCollapse] = useState(false);
    const toggle = () => {
        setCollapse(!collapse);
    };
    const location =useLocation().pathname
    const navigate = useNavigate();
    /**
     * Handle click function.
     * It is use to change view in the web app when a button in
     * the nav bar is pressed
     * @param path string used to navigate
     * first it is used the navigate method to change Route.
     * then set collapse false if the navbar is in the mobile form
     * @return null
     * */
    function handleClick(path) {
        navigate('/farmer/'+path);
        setCollapse(false)

    }
    /**
     * isActive function
     * It is use set active the selected tab
     * @param link string used to identify the tabs
     * if the link is equal to the path in the browser return true,
     * else false
     * @return boolean
     * */
    function isActive(link){
        const root=location.substr(0,link.length)
        if(link==root) return true
        else return false
    }

    /**
     * Render part
     * */

    return (
        <Header
            theme=""
            type="navbar"
        >
            <HeaderContent
                expand="lg"
                megamenu
            >
                <HeaderToggler
                    aria-controls="nav1"
                    aria-expanded="false"
                    aria-label="Toggle navigation"
                    onClick={toggle}
                >
                    <Icon icon="it-burger"/>
                </HeaderToggler>
                <Collapse
                    header
                    navbar
                    onOverlayClick={toggle}
                    isOpen={collapse}

                >
                    <div className="menu-wrapper">
                        <Nav navbar>
                            <NavItem active>
                                <NavLink
                                    active={isActive("/farmer/home")}
                                    onClick={() => handleClick("home")}
                                >
                                    <span>Home{' '}</span>
                                    <span className="sr-only">current</span>
                                </NavLink>
                            </NavItem>
                            <NavItem>
                                <NavLink active={isActive("/farmer/forum")} onClick={() => handleClick("forum")}>Forum</NavLink>
                            </NavItem>
                            <NavItem>
                                <NavLink active={isActive("/farmer/knowledge")} onClick={() => handleClick("knowledge")}>Knowledge</NavLink>
                            </NavItem>
                            <NavItem>
                                <NavLink active={isActive("/farmer/help")} onClick={() => handleClick("help")}>Help</NavLink>
                            </NavItem>
                            <NavItem>
                                <NavLink active={isActive("/farmer/meeting")} onClick={() => handleClick("meeting")}>Meeting</NavLink>
                            </NavItem>
                            <NavItem>
                                <NavLink active={isActive("/farmer/production")} onClick={() => handleClick("production")}>Production</NavLink>
                            </NavItem>
                        </Nav>
                    </div>
                </Collapse>
            </HeaderContent>
        </Header>
    )
}