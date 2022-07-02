import {
    Accordion, AccordionBody,
    AvatarIcon, Button,
    Card,
    CardBody,
    CardTagsHeader,
    Col,
    Icon, Modal, ModalFooter, ModalHeader, notify,
    Row,
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
            feedback:"",

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
    putFeedback=()=>{
        this.client.PutFeedback(this.state.feedback,this.props.data.requestId)
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
                )            })
        ;
    }
    deleteRequest=()=>{
        this.client.DeleteRequest(this.props.data.requestId)
            .then((response) => {
                if(response.data.code==200){
                    this.props.getRequest()
                    notify(
                        'Success',
                        'The Help Request was successfully deleted',
                        {state:"success"}
                    )
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
                this.setState({openModalDelete:!this.state.openModalDelete})
            })
            .catch((error) => {
                notify(
                    'Error',
                    'Something went wrong!',
                    {state:"error"}
                )            })
        ;
    }

    handleChange = (name) => event => {
        this.setState({
            [name]: event.target.value,
        });
    };



    responseCard=()=>{
        if(this.props.data.status==="pendant"){
            return

        }else if(this.props.data.status==="noFeed"){
            return(
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
                                <CardBody style={{height:100}}>
                                    <div className='form-group'>
                                        <div className='input-group'>
                                            <input
                                                type='text'
                                                className={'form-control'}
                                                id='input-group-3'
                                                name='input-group-3'
                                                placeholder='Leave a feedback'
                                                value={this.state.feedback}
                                                onChange={this.handleChange("feedback")}
                                            />
                                            <div className='input-group-append'>
                                                <IconButton onClick={() => this.putFeedback()}>
                                                    <SendIcon />
                                                </IconButton>
                                            </div>
                                        </div>
                                    </div>
                                </CardBody>
                            </Card>
                        </Col>
                    </Row>
                </div>

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
    commentOrDelete=()=>{
        if(this.props.data.status==="pendant"){
            return(
                <IconButton onClick={() => this.setState({openModalDelete:!this.state.openModalDelete})}>
                    <Icon icon='it-delete' size="sm" />
                </IconButton>
            )

        }else {
            return (
                <IconButton onClick={() => this.setState({collapseOpen1:!this.state.collapseOpen1})}>
                    <Icon icon='it-comment' size="sm" />
                </IconButton>
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
                                <CardTagsHeader date={this.props.data.date}>
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
                                        {/*<h6>{this.props.data.firstName+' '+this.props.data.lastName} </h6>*/}
                                        <h6>{this.props.data.farmerName}  </h6>
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
                                        {this.commentOrDelete()}
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
                        <Button color='primary' onClick={() => this.deleteRequest()}>
                            OK
                        </Button>
                    </ModalFooter>
                </Modal>
            </div>


        )
    }




}