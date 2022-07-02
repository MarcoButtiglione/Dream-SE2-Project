import {
    Col, Collapse,
    Button,
    DropdownMenu,
    DropdownToggle, Header, HeaderBrand, HeaderContent, HeaderLinkZone, HeaderRightZone, HeaderToggler,
    Icon,
    LinkList,
    LinkListItem,
    Row,
    UncontrolledDropdown, Dropdown
} from "design-react-kit";
import {Link, NavLink, useLocation, useNavigate} from "react-router-dom";
import {useState} from "react";

export function SlimHeader(props) {
    const [open, toggle] = useState(false);
    const location = useLocation().pathname
    const navigate = useNavigate();
    /**
     * Button to the personal area.
     * There are 3 different condition:
     * - if the user is not logged, it is show a sign in button
     * - if the user is logged and it is in the landing page, it is shown a button
     * tu the home page
     * -if the user is logged and it is in the personal area, it is shown a
     * button with his name that could be pressed to logout
     */
    const personalArea = () => {
        if (props.token && props.token !== "null" && props.agronomist) {
            if (location === "/") {
                return (
                    <Button
                        className="btn-icon"
                        color="primary"
                        size="full"
                        onClick={() => navigate("/agronomist/home")}
                    >
                        <span className="rounded-icon">
                              <Icon
                                  color="primary"
                                  icon="it-user"
                              />
                        </span>
                        <span className="d-none d-lg-block">
                            Personal Area
                        </span>
                    </Button>
                )
            } else {
                return (
                    <Dropdown className='mr-3' isOpen={open} toggle={() => toggle(!open)}>
                        <DropdownToggle color='primary' caret className={"btn-icon btn btn-primary btn-full"}>
                        <span className="rounded-icon">
                              <Icon
                                  color="primary"
                                  icon="it-user"
                              />
                        </span>
                            <span className="d-none d-lg-block">
                            {props.agronomist ? props.agronomist.firstName + " " + props.agronomist.lastName : ""}
                        </span>
                        </DropdownToggle>
                        <DropdownMenu className='dark'>
                            <LinkList>
                                <LinkListItem divider/>
                                <LinkListItem onClick={() => props.setTokens(null, null)}>Logout</LinkListItem>
                            </LinkList>
                        </DropdownMenu>
                    </Dropdown>
                )
            }
        } else {
            return (
                <div className="it-access-top-wrapper">
                    <Link
                        className="btn btn-primary btn-sm"
                        role="button"
                        to="/signin"
                        style={{textDecoration: 'none'}}
                    >
                        Sign In
                    </Link>
                </div>
            )
        }
    }
    /**
     *Render part
     */
    return (
        <Header
            theme=""
            type="slim"
        >
            <HeaderContent>
                <HeaderBrand
                >
                    <Link to="/">SE2-Project-POLIMI</Link>
                </HeaderBrand>
                <HeaderLinkZone>
                    <HeaderToggler
                        //onClick={function noRefCheck(){}}
                    >
        <span
        >
            <Link to="/">SE2-Project-POLIMI</Link>


        </span>
                    </HeaderToggler>

                </HeaderLinkZone>
                <HeaderRightZone>
                    <UncontrolledDropdown
                        nav
                        tag="div"
                    >
                        <DropdownToggle
                            caret
                            nav
                        >
                            ENG
                            <Icon
                                className="d-none d-lg-block"
                                color="icon-white"
                                icon="it-expand"
                            />
                        </DropdownToggle>
                        <DropdownMenu>
                            <Row>
                                <Col size="12">
                                    <LinkList>
                                        <LinkListItem
                                            href="#"
                                            //tag={function noRefCheck(){}}
                                        >
                  <span>
                    HIN
                  </span>
                                        </LinkListItem>
                                        <LinkListItem
                                            href="#"
                                            //tag={function noRefCheck(){}}
                                        >
                  <span>
                    ENG
                  </span>
                                        </LinkListItem>
                                    </LinkList>
                                </Col>
                            </Row>
                        </DropdownMenu>
                    </UncontrolledDropdown>
                    {personalArea()}

                </HeaderRightZone>
            </HeaderContent>
        </Header>
    )
}