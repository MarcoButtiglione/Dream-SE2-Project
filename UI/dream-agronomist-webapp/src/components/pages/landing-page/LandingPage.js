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
                                                Observe farmer productivity
                                            </CardTitle>
                                            <CardText className="card-text" style={{marginTop:10}}>
                                                Visualize data concerning your area of expertise – for instance, weather forecasts, the best performing farmers in the
                                                area, their production and their performance

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
                                            Helping
                                        </CardCategory>
                                        <CardTitle className="font-weight-semibold">
                                            Assist the farmer in you zone
                                        </CardTitle>
                                        <CardText style={{marginTop:10}}>
                                            Share your experiences to all the farmers on thee platform. Answer their requests for help and arrange meetings with the farmers who need them most.
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
                                            View and plan periodic visits with the farmers in your area. In these visits you can also evaluate their progress and how they responded to your requests for help.
                                        </CardText>
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