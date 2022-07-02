import {
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
import React from 'react';
import {IconButton} from "@mui/material";
import FavoriteBorderIcon from '@mui/icons-material/FavoriteBorder';
import FavoriteIcon from '@mui/icons-material/Favorite';
import ApiClient from "../../../../commons/apiClient";
import appGlobal from "../../../../commons/appGlobal";
import EditOutlinedIcon from "@mui/icons-material/EditOutlined";
import DeleteOutlineOutlinedIcon from "@mui/icons-material/DeleteOutlineOutlined";

export class CommentCard extends React.Component{

    constructor(props) {
        super(props);
        this.state = {
            liked: this.props.data.liked,
            openModalDelete:false,
            openModalModify:false,
            modifyingComment:""
        };
        this.client = new ApiClient()
    }
    componentDidUpdate(prevProps) {
        if(prevProps.data.liked !== this.props.data.liked) {
            this.setState({liked: this.props.data.liked});

        }
    }

    likeComment=()=>{
        this.client.LikeComment(this.props.data.entity.commentId)
            .then((response) => {
                if(response.data.code==200){
                    this.props.getComment()
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
    }
    unLikeComment=()=>{
        this.client.UnLikeComment(this.props.data.entity.commentId)
            .then((response) => {
                if(response.data.code==200){
                    this.props.getComment()
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
    }
    putComment=()=>{
        const comment =this.state.modifyingComment
        this.client.PutComment(comment,this.props.data.entity.topic,this.props.data.entity.commentId)
            .then((response) => {
                if(response.data.code==200){
                    this.props.getComment()
                    this.props.getTopic()
                    notify(
                        'Success',
                        'The Comment was successfully edited',
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
    deleteComment=()=>{
        this.client.DeleteComment(this.props.data.entity.commentId)
            .then((response) => {
                if(response.data.code==200){
                    this.props.getComment()
                    this.props.getTopic()
                    notify(
                        'Success',
                        'The Comment was successfully deleted',
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

    isOwn=()=>{
        if(this.props.farmer.farmerId===this.props.data.entity.farmer){
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
    iconLiked=()=>{
        if(this.state.liked){
            return (
                <IconButton onClick={()=>this.unLikeComment()}>
                    {this.props.data.entity.likes}
                    <FavoriteIcon  size="sm" />
                </IconButton>
            )
        }else {
            return (
                <IconButton onClick={()=>this.likeComment()}>
                    {this.props.data.entity.likes}
                    <FavoriteBorderIcon size="sm" />
                </IconButton>

            )
        }
    }
    handleChange = (name) => event => {
        this.setState({
            [name]: event.target.value,
        });
    };
    handleToggleModalModify=()=> {
        if (this.state.openModalModify) {
            this.setState({
                openModalModify: false,
                modifyingComment: "",
            })
        } else {
            this.setState({
                openModalModify: true,
                modifyingComment: this.props.data.entity.comment,

            })
        }
    }
    render () {
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
                            <CardTagsHeader date={this.props.data.entity.date}
                                            //{this.props.data.date[2]+'/'+this.props.data.date[1]+'/'+this.props.data.date[0]}
                            >
                                <CardTag >{this.props.tag}</CardTag>
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
                                    <h6>{this.props.data.entity.firstName+' '+this.props.data.entity.lastName}</h6>
                                    <p>{this.props.data.entity.comment} </p>
                                </Col>
                            </Row>
                            <Row>
                                <Col md={{
                                    size: 2
                                }}>
                                    {this.isOwn()}
                                </Col>
                                <Col
                                    xl={{
                                        offset: 9,
                                        size: 1
                                    }}
                                    lg={{
                                        offset: 8,
                                        size: 1
                                    }}
                                    >
                                    {this.iconLiked()}
                                </Col>
                            </Row>
                        </CardBody>
                    </Card>
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
                            <Button color='primary' onClick={() => this.deleteComment()}>
                                OK
                            </Button>
                        </ModalFooter>
                    </Modal>
                    <Modal
                        isOpen={this.state.openModalModify}
                        toggle={() => this.handleToggleModalModify()}
                        centered
                        labelledBy='esempio9'
                    >
                        <ModalHeader toggle={() => this.handleToggleModalModify()} id='essempio9'>
                            Edit the comment
                        </ModalHeader>
                        <ModalBody>
                            <div className='form-row'style={{marginTop:20}}>
                                <Input
                                    type='text'
                                    label='Comment'
                                    id='inputTitle'
                                    value={this.state.modifyingComment}
                                    onChange={this.handleChange("modifyingComment")}

                                    wrapperClass='col col-md-12'
                                />
                            </div>
                        </ModalBody>
                        <ModalFooter>
                            <Button color='secondary' onClick={() => this.setState({openModalModify:!this.state.openModalModify})}>
                                Close
                            </Button>
                            <Button color='primary' onClick={() => this.putComment()}>
                                OK
                            </Button>
                        </ModalFooter>
                    </Modal>
                </Col>
            </Row>

        )
    }
}