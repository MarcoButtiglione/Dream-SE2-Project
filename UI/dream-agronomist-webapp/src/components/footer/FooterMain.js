import {Button, Col, Container, Form, Icon, Input, Label, LinkList, LinkListItem, Row} from "design-react-kit";

/**
 * Footer
 * */
export function FooterMain(){
    return(
        <footer className="it-footer">
            <div className="it-footer-main">
                <Container>
                    <section>
                        <Row className="clearfix">
                            <Col sm={12}>
                                <div className="it-brand-wrapper">
                                    <a
                                        className=""
                                        href="#"
                                    >
                                        <Icon icon="it-pa" />
                                        <div className="it-brand-text">
                                            <h2>
                                                Dream
                                            </h2>
                                            <h3 className="d-none d-md-block">
                                                Telegana farm monitoring platform
                                            </h3>
                                        </div>
                                    </a>
                                </div>
                            </Col>
                        </Row>
                    </section>
                </Container>
            </div>
            <div className="it-footer-small-prints clearfix">
                <Container>
                    <h3 className="sr-only">
                        Useful Links Section
                    </h3>
                    <ul className="it-footer-small-prints-list list-inline mb-0 d-flex flex-column flex-md-row">
                        <li className="list-inline-item">
                            <a
                                href="#"
                                title="Note Legali"
                            >
                                Media policy
                            </a>
                        </li>
                        <li className="list-inline-item">
                            <a
                                href="#"
                                title="Note Legali"
                            >
                                Legal notices
                            </a>
                        </li>
                        <li className="list-inline-item">
                            <a
                                href="#"
                                title="Privacy-Cookies"
                            >
                                Privacy policy
                            </a>
                        </li>
                       {/* <li className="list-inline-item">
                            <a
                                href="#"
                                title="Mappa del sito"
                            >
                                Mappa del sito
                            </a>
                        </li>*/}
                    </ul>
                </Container>
            </div>
        </footer>
    )
}