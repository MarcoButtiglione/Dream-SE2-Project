import {
    Button,
    Card,
    CardBody,
    CardCategory, CardImg,
    CardReadMore,
    CardText,
    CardTitle,
    Col,
    Container,
    Icon, LinkList, LinkListItem,
    Row
} from "design-react-kit";

export function LandingPage(){
    return(
        <>
            <section id="head-section">
                <Container>
                    <Row>
                        <Col
                            lg={{
                                offset: 1,
                                order: 2,
                                size: 6
                            }}
                        >
                            <img
                                alt="imagealt"
                                className="img-fluid"
                                src="https://firebasestorage.googleapis.com/v0/b/dreamfarmerse2.appspot.com/o/home1.png?alt=media&token=14143640-3a43-4cc1-a65d-8e950fd6c1ad"
                                title="img title"
                            />
                        </Col>
                        <Col
                            lg={{
                                order: 1,
                                size: 5
                            }}
                        >
                            <Card className={"no-after"}>
                                <CardBody className="pb-5" style={{paddingTop:30}}>
                                    <CardTitle tag="h4">
                                        Dream, the Telangana platform for monitoring agricultural production.
                                    </CardTitle>
                                    <CardText>
                                        The goal of Telengana’s government is to design, develop and demonstrate anticipatory governance models for food systems using digital public goods and community-centric approaches to strengthen data-driven policy making in the state.
                                    </CardText>

                                </CardBody>
                            </Card>
                        </Col>
                    </Row>
                </Container>
            </section>
            <section id="calendario">
                <div className="section section-muted pb-5 pt-0">
                    <Container>
                        <Row>
                            <div className="card-wrapper card-teaser-wrapper card-overlapping card-teaser-wrapper-equal card-teaser-block-3">
                                <Card
                                    className="card-teaser-image card-flex no-after rounded shadow"
                                    noWrapper
                                    teaser
                                >
                                    <div className="card-image-wrapper with-read-more pb-5">
                                        <CardBody className="p-4">
                                            <CardCategory>
                                                <Icon icon="it-chart-line" />
                                                Monitoring
                                            </CardCategory>
                                            <CardTitle className="font-weight-semibold">
                                                Improve production
                                            </CardTitle>
                                            <CardText className="card-text" style={{marginTop:10}}>
                                                Visualize data relevant to you – for instance, weather forecasts, the data coming from the sensors
                                                and the information obtained by the governmental agronomists who periodically will visit your farms.
                                                It is also possible to insert data concerning your production and your products.

                                            </CardText>
                                        </CardBody>
                                    </div>
                                </Card>
                                <Card
                                    className="no-after rounded shadow"
                                    noWrapper
                                    teaser
                                >
                                    <CardBody className="pb-5">
                                        <CardCategory>
                                            <Icon icon="it-hearing" />
                                            Learning
                                        </CardCategory>
                                        <CardTitle className="font-weight-semibold">
                                            Share your problem
                                        </CardTitle>
                                        <CardText style={{marginTop:10}}>
                                            On this platform, a group of experts will respond to yours request for help and and they will suggest you how to improve the productivity of your farm.
                                            Furthermore, it is also possible to create discussion forums with the other farmers and sharing their experiences.
                                        </CardText>
                                    </CardBody>

                                </Card>
                                <Card
                                    className="no-after rounded shadow"
                                    noWrapper
                                    teaser
                                >
                                    <CardBody className="pb-5">
                                        <CardCategory>
                                            <Icon icon="it-calendar" />
                                            Planning
                                        </CardCategory>
                                        <CardTitle className="font-weight-semibold">
                                            Organize periodic meetings
                                        </CardTitle >
                                        <CardText style={{marginTop:10}}>
                                            View and plan periodic visits organized by the agronomist in your area who will record the data relating to the soil and evaluate your work.                                        </CardText>
                                    </CardBody>
                                </Card>
                            </div>
                        </Row>

                    </Container>
                </div>
            </section>

        </>
    )
}