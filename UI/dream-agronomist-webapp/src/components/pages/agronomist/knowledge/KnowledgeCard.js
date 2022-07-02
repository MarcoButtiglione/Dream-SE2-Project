import React, {useState} from "react";
import {
    Button,
    Card,
    CardBody,
    CardReadMore,
    CardText,
    CardTitle,
    Col,
    Icon, Input,
    Modal,
    ModalBody, ModalFooter,
    ModalHeader, notify,
    Row, TextArea
} from "design-react-kit";
import {IconButton} from "@mui/material";
import EditOutlinedIcon from '@mui/icons-material/EditOutlined';
import DeleteOutlineOutlinedIcon from '@mui/icons-material/DeleteOutlineOutlined';
import ApiClient from "../../../../commons/apiClient";
import FavoriteBorderIcon from "@mui/icons-material/FavoriteBorder";


export class KnowledgeCard extends React.Component{
    constructor(props) {
        super(props);
        this.state=this.getDefaultState()
        this.client = new ApiClient()
    }

    getDefaultState = () => {
        return {
            openReadMore:false,

            openModalDelete:false,
            openModalModify:false,
            modifyingTitle:"",
            modifyingCategory:"",
            modifyingDescription:"",
            modifyingArticle:"",

        }
    }

    putKnowledge=()=>{
        const knowledge ={
            id:this.props.data.knowledgeId,
            title:this.state.modifyingTitle,
            description:this.state.modifyingDescription,
            article:this.state.modifyingArticle,
            category:this.state.modifyingCategory
        }
        this.client.PutKnowledge(knowledge)
            .then((response) => {
                if(response.data.code==200){
                    notify(
                        'Success',
                        'The Knowledge was successfully edited',
                        {state:"success"}
                    )
                    this.props.getKnowledge()

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
    deleteKnowledge=()=>{
        this.client.DeleteKnowledge(this.props.data.knowledgeId)
            .then((response) => {
                if(response.data.code==200){
                    this.props.getKnowledge()
                    notify(
                        'Success',
                        'The Knowledge was successfully deleted',
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

    handleToggleModalModify=()=>{
        if(this.state.openModalModify){
            this.setState({
                openModalModify:false,
                modifyingTitle:"",
                modifyingCategory:"",
                modifyingDescription:"",
                modifyingArticle:"",
            })
        }else {
            this.setState({
                openModalModify:true,
                modifyingTitle:this.props.data.title,
                modifyingCategory:this.props.data.category,
                modifyingDescription:this.props.data.description,
                modifyingArticle:this.props.data.article,
            })
        }


    }
    handleChange = (name) => event => {
        this.setState({
            [name]: event.target.value,
        });
    };
    render() {
        return(
            <div>
                <Row>
                    <Col xs='12' lg='12'>
                        <Card spacing className='card-bg card-big border-bottom-card'>
                            <div className='flag-icon'></div>
                            <div className='etichetta'>
                                <FavoriteBorderIcon size="xs" />
                                <span style={{marginRight:40}}>{this.props.data.likes}</span>

                                <Icon icon='it-settings' />
                                <span style={{marginRight:40}}>{this.props.data.category}</span>
                                <div>
                                    <IconButton onClick={() => this.handleToggleModalModify()}>
                                        <EditOutlinedIcon size="sm" />
                                    </IconButton>
                                    <IconButton onClick={() => this.setState({openModalDelete:!this.state.openModalDelete})}>
                                        <DeleteOutlineOutlinedIcon size="sm" />
                                    </IconButton>
                                </div>
                            </div>
                            <CardBody>
                                <Row>
                                    <Col xs={12} lg={4}>
                                        <CardTitle tag='h5'>
                                            {this.props.data.title}
                                        </CardTitle>
                                    </Col>
                                    <Col xs={12}  lg={8}>
                                        <CardText>
                                            {this.props.data.description}
                                        </CardText>
                                    </Col>
                                </Row>
                                <CardReadMore iconName='it-arrow-right' text='Read more' onClick={() =>this.setState({openReadMore:!this.state.openReadMore})}/>
                            </CardBody>
                        </Card>
                    </Col>
                </Row>
                <Modal
                    isOpen={this.state.openReadMore}
                    toggle={() => this.setState({openReadMore:!this.state.openReadMore})}
                    scrollable
                    centered
                    labelledBy='esempio8'
                    size={'lg'}
                >
                    <ModalHeader toggle={() => this.setState({openReadMore:!this.state.openReadMore})}  id='esempio8'>
                        <h4>
                            {this.props.data.title}
                        </h4>
                        {this.props.data.date}<br/>
                        <div className='etichetta'>
                            <Icon icon='it-settings' />
                            <span>{this.props.data.category}</span>
                        </div>
                    </ModalHeader>
                    <ModalBody>
                        <p>
                            {this.props.data.article}
                        </p>
                    </ModalBody>
                    <ModalFooter>
                        <IconButton onClick={() => this.handleToggleModalModify()}>
                            <EditOutlinedIcon size="sm" />
                        </IconButton>
                        <IconButton onClick={() => this.setState({openModalDelete:!this.state.openModalDelete})}>
                            <DeleteOutlineOutlinedIcon size="sm" />
                        </IconButton>

                    </ModalFooter>
                </Modal>
                <Modal
                    isOpen={this.state.openModalModify}
                    toggle={() => this.handleToggleModalModify()}
                    centered
                    labelledBy='esempio9'
                >
                    <ModalHeader toggle={() => this.handleToggleModalModify()} id='essempio9'>
                        Edit the Knowledge
                    </ModalHeader>
                    <ModalBody>
                        <div className='form-row'>
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
                                label='Category'
                                id='inputTitle'
                                value={this.state.modifyingCategory}
                                onChange={this.handleChange("modifyingCategory")}

                                wrapperClass='col col-md-12'
                            />
                        </div>
                        <div>
                            <TextArea
                                label='Description'
                                rows={3}
                                style={{resize:"none"}}
                                value={this.state.modifyingDescription}
                                onChange={this.handleChange("modifyingDescription")}
                            />
                        </div>
                        <div>
                            <TextArea
                                label='Article'
                                rows={3}
                                style={{resize:"none"}}
                                value={this.state.modifyingArticle}
                                onChange={this.handleChange("modifyingArticle")}
                            />
                        </div>
                    </ModalBody>
                    <ModalFooter>
                        <Button color='secondary' onClick={() => this.handleToggleModalModify()}>
                            Close
                        </Button>
                        <Button color='primary' onClick={() => this.putKnowledge()}>
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
                        <Button color='primary' onClick={() => this.deleteKnowledge()}>
                            OK
                        </Button>
                    </ModalFooter>
                </Modal>
            </div>

        )
    }



}