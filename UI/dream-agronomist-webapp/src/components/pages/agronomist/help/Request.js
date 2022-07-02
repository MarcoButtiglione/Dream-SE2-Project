import {
    Accordion, AccordionBody,
    AvatarIcon,
    Button,
    Card,
    CardBody, CardCategory, CardImg, CardReadMore, CardTag, CardTagsHeader,
    CardText,
    CardTitle,
    Col,
    Icon, Input, Modal, ModalBody, ModalFooter, ModalHeader, notify,
    Row, TextArea
} from "design-react-kit";
import React, {useState} from 'react';
import {IconButton} from "@mui/material";
import ApiClient from "../../../../commons/apiClient";
import SendIcon from '@mui/icons-material/Send';


export class Request extends React.Component{
    constructor(props) {
        super(props);
        this.state=this.getDefaultState()
        this.client = new ApiClient()
    }

    getDefaultState = () => {
        return {
            response:"",

            comments:null,
            collapseOpen1:false,
            openModalDelete:false,
            openModalModify:false,
            modifyingTitle:"",
            modifyingDescription:"",
            modifyingTag:"",
            newComment:"",

        }
    }
    componentDidMount() {
    }

    putResponse=()=>{
        this.client.PutResponse(this.state.response,this.props.data.requestId)
            .then((response) => {
                if(response.data.code==200){
                    this.props.getRequest()
                }else if(response.data.code==404){
                    notify(
                        'Warning',
                        response.data.message,
                        {state:"warning"}
                    )
                }else if(response.data.code==400){
                    notify(
                        'Warning',
                        response.data.message,
                        {state:"warning"}
                    )
                }
            })
            .catch((error) => {
                notify(
                    'Error',
                    'Something went wrong!',
                    {state:"error"}
                )
            })
        ;

    }

    handleChange = (name) => event => {
        this.setState({
            [name]: event.target.value,
        });
    };



    responseCard=()=>{
        if(this.props.data.status==="pendant"){
            return (
                <Row>
                    <Col
                        md={{
                            offset: 3,
                            size: 7
                        }}
                        xs={{
                            offset: 1,
                            size: 11
                        }}>
                        <Card spacing className='card-bg no-after'>
                            <CardBody style={{height:100}}>
                                <div className='form-group'>
                                    <div className='input-group'>
                                        <input
                                            type='text'
                                            className={'form-control'}
                                            id='input-group-3'
                                            name='input-group-3'
                                            placeholder='Leave a response'
                                            value={this.state.response}
                                            onChange={this.handleChange("response")}
                                        />
                                        <div className='input-group-append'>
                                            <IconButton onClick={() => this.putResponse()}>
                                                <SendIcon />
                                            </IconButton>
                                        </div>
                                    </div>
                                </div>
                            </CardBody>
                        </Card>
                    </Col>
                </Row>
            )

        }else if(this.props.data.status==="noFeed"){
            return(
                <Row>
                    <Col
                        md={{
                            offset: 3,
                            size: 7
                        }}
                        xs={{
                            offset: 1,
                            size: 11
                        }}>
                        <Card spacing className='card-bg no-after'>
                            <CardBody>
                                <Row>
                                    <Col lg={1}>
                                        <AvatarIcon size='lg'>
                                            <Icon icon='it-user' />
                                        </AvatarIcon>
                                    </Col>
                                    <Col lg={{
                                        size:10
                                    }}>
                                        <h6>Agronomist</h6>
                                        <p>{this.props.data.response} </p>
                                    </Col>
                                </Row>
                            </CardBody>
                        </Card>
                    </Col>
                </Row>
            )
        }else if(this.props.data.status==="closed"){
            return (
                <div>
                    <Row>
                        <Col
                            md={{
                                offset: 3,
                                size: 7
                            }}
                            xs={{
                                offset: 1,
                                size: 11
                            }}>
                            <Card spacing className='card-bg no-after'>
                                <CardBody>
                                    <Row>
                                        <Col lg={1}>
                                            <AvatarIcon size='lg'>
                                                <Icon icon='it-user' />
                                            </AvatarIcon>
                                        </Col>
                                        <Col lg={{
                                            size:10
                                        }}>
                                            <h6>Agronomist</h6>
                                            <p>{this.props.data.response} </p>
                                        </Col>
                                    </Row>
                                </CardBody>
                            </Card>
                        </Col>
                    </Row>
                    <Row>
                        <Col
                            md={{
                                offset: 3,
                                size: 7
                            }}
                            xs={{
                                offset: 1,
                                size: 11
                            }}>
                            <Card spacing className='card-bg no-after'>
                                <CardBody>
                                    <Row>
                                        <Col lg={1}>
                                            <AvatarIcon size='lg'>
                                                <Icon icon='it-user' />
                                            </AvatarIcon>
                                        </Col>
                                        <Col lg={{
                                            size:10
                                        }}>
                                            <h6>{this.props.data.farmerName} </h6>
                                            <p>{this.props.data.feedback} </p>
                                        </Col>
                                    </Row>
                                </CardBody>
                            </Card>
                        </Col>
                    </Row>
                </div>

            )
        }
    }

    render() {
        return(
            <div style={{marginBottom:12}}>
                <Row>
                    <Col
                        md={{
                            offset: 2,
                            size: 8
                        }}
                        sm={{
                            offset: 1,
                            size: 10
                        }}>
                        <Card spacing className='card-bg no-after' >
                            <CardBody>
                                <CardTagsHeader date={
                                    this.props.data.date
                                }>
                                    {/*<CardTag >{this.props.data.tag}</CardTag>*/}
                                </CardTagsHeader>
                                <Row>
                                    <Col lg={1}>
                                        <AvatarIcon size='lg'>
                                            <Icon icon='it-user' />
                                        </AvatarIcon>
                                    </Col>
                                    <Col lg={{
                                        size:10
                                    }}>
                                        <h6>{this.props.data.farmerName} </h6>
                                        <h5>{this.props.data.subject}</h5>
                                        <p>{this.props.data.request} </p>
                                    </Col>
                                </Row>
                                <Row>
                                    <Col md={{
                                        size: 2
                                    }}>
                                    </Col>
                                    <Col
                                        md={{
                                            offset: 9,
                                            size: 1
                                        }}
                                        xs={{
                                            offset: 0,
                                            size: 1
                                        }}
                                    >
                                        <IconButton onClick={() => this.setState({collapseOpen1:!this.state.collapseOpen1})}>
                                            <Icon icon='it-comment' size="sm" />
                                        </IconButton>
                                    </Col>
                                </Row>
                            </CardBody>
                        </Card>
                    </Col>
                </Row>
                <Accordion>
                    <AccordionBody active={this.state.collapseOpen1}>
                        {this.responseCard()}
                    </AccordionBody>

                </Accordion>

                {/*<Modal
                    isOpen={this.state.openModalModify}
                    toggle={() => this.handleToggleModalModify()}
                    centered
                    labelledBy='esempio9'
                >
                    <ModalHeader toggle={() => this.handleToggleModalModify()} id='essempio9'>
                        Edit the topic
                    </ModalHeader>
                    <ModalBody>
                        <div className='form-row'style={{marginTop:20}}>
                            <Input
                                type='text'
                                label='Title'
                                id='inputTitle'
                                value={this.state.modifyingTitle}
                                onChange={this.handleChange("modifyingTitle")}

                                wrapperClass='col col-md-12'
                            />
                        </div>
                        <div className='form-row'>
                            <Input
                                type='text'
                                label='Tag'
                                id='inputTitle'
                                value={this.state.modifyingTag}
                                onChange={this.handleChange("modifyingTag")}

                                wrapperClass='col col-md-12'
                            />
                        </div>
                        <div>
                            <TextArea
                                label='Body'
                                rows={3}
                                style={{resize:"none"}}
                                value={this.state.modifyingDescription}
                                onChange={this.handleChange("modifyingDescription")}
                            />
                        </div>
                    </ModalBody>
                    <ModalFooter>
                        <Button color='secondary' onClick={() => this.handleToggleModalModify()}>
                            Close
                        </Button>
                        <Button color='primary' onClick={() => this.putTopic()}>
                            OK
                        </Button>
                    </ModalFooter>
                </Modal>
                <Modal
                    isOpen={this.state.openModalDelete}
                    toggle={() => this.setState({openModalDelete:!this.state.openModalDelete})}
                    centered
                    labelledBy='esempio9'
                >
                    <ModalHeader  id='essempio9'>
                        Are you sure you want to delete?
                    </ModalHeader>
                    <ModalFooter>
                        <Button color='secondary' onClick={() => this.setState({openModalDelete:!this.state.openModalDelete})}>
                            Close
                        </Button>
                        <Button color='primary' onClick={() => this.deleteTopic()}>
                            OK
                        </Button>
                    </ModalFooter>
                </Modal>
*/}
            </div>


        )
    }




}