import {Button, Col, Container, Input, Row} from "design-react-kit";
import {Link} from "react-router-dom";
import React from "react";

export default function NotFound(){
    return(
        <div style={{minHeight:700}}>
            <Container style={{paddingTop:100}}>
                <Row>
                    <Col
                        md={{
                            offset: 2,
                            size: 8
                        }}
                        sm={{
                            offset: 1,
                            size: 10
                        }}
                    >

                        <h2 className=' text-center' style={{fontSize:90}}>404</h2>
                        <h3 className=' text-center'>Not Found</h3>

                    </Col>

                </Row>


            </Container>

        </div>
    )
}