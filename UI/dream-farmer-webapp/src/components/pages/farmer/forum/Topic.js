import {
    Accordion, AccordionBody,
    AvatarIcon,
    Button,
    Card,
    CardBody, CardTag, CardTagsHeader,
    Col,
    Icon, Input, Modal, ModalBody, ModalFooter, ModalHeader, notify,
    Row, TextArea
} from "design-react-kit";
import React, {useState} from 'react';
import {IconButton} from "@mui/material";
import {CommentCard} from "./CommentCard";
import ApiClient from "../../../../commons/apiClient";
import SendIcon from '@mui/icons-material/Send';
import EditOutlinedIcon from '@mui/icons-material/EditOutlined';
import DeleteOutlineOutlinedIcon from '@mui/icons-material/DeleteOutlineOutlined';

export class Topic extends React.Component{
    constructor(props) {
        super(props);
        this.state=this.getDefaultState()
        this.client = new ApiClient()
    }

    getDefaultState = () => {
        return {
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
        this.getComment()
    }

    putTopic=()=>{
        const topic ={
            id:this.props.data.topicId,
            topic:this.state.modifyingTitle,
            description:this.state.modifyingDescription,
            tag:this.state.modifyingTag
        }
        this.client.PutTopic(topic)
            .then((response) => {
                if(response.data.code==200){
                    notify(
                        'Success',
                        'The Topic was successfully edited',
                        {state:"success"}
                    )
                    this.props.getTopic()
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
                this.setState({openModalModify:!this.state.openModalModify})

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
    deleteTopic=()=>{
        this.client.DeleteTopic(this.props.data.topicId)
            .then((response) => {
                if(response.data.code==200){
                    this.props.getTopic()
                    notify(
                        'Success',
                        'The Topic was successfully deleted',
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
                this.setState({openModalDelete:false})

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
    getComment = () => {
        this.client.GetAllComment(this.props.data.topicId)
            .then((response) => {
                if(response.data.code==200){
                    this.setState({
                        comments: response.data.results[0],
                    })

                }else if(response.data.code==404){
                    this.setState({
                        comments: null,
                    })
                }else if(response.data.code==400){
                    this.setState({
                        comments: null,
                    })
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
    };
    postComment=()=>{
        this.client.PostComment(this.props.data.topicId,this.state.newComment)
            .then((response) => {
                if(response.data.code==200){
                    this.setState({newComment:""})
                    this.getComment()
                    this.props.getTopic()
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


    handleChange = (name) => event => {
        this.setState({
            [name]: event.target.value,
        });
    };
    comments=()=>{
        if(this.state.comments){
            return this.state.comments.map((data) =>
                <CommentCard
                    farmer={this.props.farmer}
                    key={data.entity.commentId}
                    data={data}
                    tag={this.props.data.tag}
                    getComment={()=>this.getComment()}
                    getTopic={()=>this.props.getTopic()}
                />)
        }
    }
    handleToggleModalModify=()=>{
        if(this.state.openModalModify){
            this.setState({
                openModalModify:false,
                modifyingTitle:"",
                modifyingDescription:"",
                modifyingTag:"",
            })
        }else {
            this.setState({
                openModalModify:true,
                modifyingTitle:this.props.data.topic,
                modifyingDescription:this.props.data.description,
                modifyingTag:this.props.data.tag,

            })
        }


    }

    isOwn=()=>{
        if(this.props.farmer.farmerId===this.props.data.farmer){
            return(
                <div>
                    <IconButton onClick={() => this.handleToggleModalModify()}>
                        <EditOutlinedIcon size="sm" />
                    </IconButton>
                    <IconButton onClick={() => this.setState({openModalDelete:!this.state.openModalDelete})}>
                        <DeleteOutlineOutlinedIcon size="sm" />
                    </IconButton>
                </div>
            )
        }
    }
    handleChange = (name) => event => {
        this.setState({
            [name]: event.target.value,
        });
    };
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
                                <CardTagsHeader
                                    date={this.props.data.date}
                                >
                                    <CardTag >{this.props.data.tag}</CardTag>
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
                                        <h6>{this.props.data.firstName+' '+this.props.data.lastName} </h6>
                                        <h5>{this.props.data.topic}</h5>
                                        <p>{this.props.data.description} </p>
                                    </Col>
                                </Row>
                                <Row>
                                    <Col md={{
                                        size: 2
                                    }}>
                                        {this.isOwn()}
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
                                            {this.props.data.comments.length}
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
                        {this.comments()}
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
                                                    placeholder='Leave a comment'
                                                    value={this.state.newComment}
                                                    onChange={this.handleChange("newComment")}


                                                />
                                                <div className='input-group-append'>
                                                    <IconButton onClick={() => this.postComment()}>
                                                        <SendIcon />
                                                    </IconButton>
                                                </div>
                                            </div>
                                        </div>
                                    </CardBody>
                                </Card>
                            </Col>
                        </Row>
                    </AccordionBody>

                </Accordion>

                <Modal
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

            </div>


        )
    }




}