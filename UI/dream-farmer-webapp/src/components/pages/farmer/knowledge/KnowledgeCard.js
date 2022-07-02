import {
    Button,
    Card,
    CardBody,
    CardReadMore,
    CardText,
    CardTitle,
    Col,
    Icon,
    Modal, ModalBody,
    ModalFooter, ModalHeader, notify,
    Row
} from "design-react-kit";
import React, {useState} from "react";
import {IconButton} from "@mui/material";
import ApiClient from "../../../../commons/apiClient";
import FavoriteIcon from "@mui/icons-material/Favorite";
import FavoriteBorderIcon from "@mui/icons-material/FavoriteBorder";

export class KnowledgeCard extends React.Component {
    constructor(props) {
        super(props);
        this.state=this.getDefaultState()
        this.client = new ApiClient()
    }
    getDefaultState = () => {
        return {
            liked: this.props.data.liked,
            isOpen:false,
        }
    }
    componentDidUpdate(prevProps) {
        if(prevProps.data.liked !== this.props.data.liked) {
            this.setState({liked: this.props.data.liked});

        }
    }
    likeKnowledge=()=>{
        this.client.LikeKnowledge(this.props.data.entity.knowledgeId)
            .then((response) => {
                if(response.data.code==200){
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
            })
            .catch((error) => {
                notify(
                    'Error',
                    'Something went wrong!',
                    {state:"error"}
                )            })
    }
    unLikeKnowledge=()=>{
        this.client.UnLikeKnowledge(this.props.data.entity.knowledgeId)
            .then((response) => {
                if(response.data.code==200){
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
            })
            .catch((error) => {
                notify(
                    'Error',
                    'Something went wrong!',
                    {state:"error"}
                )            })
    }


    iconLiked=()=>{
        if(this.state.liked){
            return (
                <IconButton onClick={()=>this.unLikeKnowledge()} style={{marginRight:30}}>
                    <FavoriteIcon  size="xs" />
                    <span>{this.props.data.entity.likes}</span>
                </IconButton>
            )
        }else {
            return (
                <IconButton onClick={()=>this.likeKnowledge()} style={{marginRight:30}}>
                    <FavoriteBorderIcon size="xs" />
                    <span>{this.props.data.entity.likes}</span>

                </IconButton>

            )
        }
    }
    render() {
        return(
            <div>
                <Row>
                    <Col xs='12' lg='12'>
                        <Card spacing className='card-bg card-big border-bottom-card'>
                            <div className='flag-icon'></div>
                            <div className='etichetta'>
                                {this.iconLiked()}
                                <Icon icon='it-settings' />
                                <span>{this.props.data.entity.category}</span>
                            </div>
                            <CardBody>
                                <Row>
                                    <Col xs={12} lg={4}>
                                        <CardTitle tag='h5'>
                                            {this.props.data.entity.title}
                                        </CardTitle>
                                    </Col>
                                    <Col xs={12}  lg={8}>
                                        <CardText>
                                            {this.props.data.entity.description}
                                        </CardText>
                                    </Col>
                                </Row>
                                <CardReadMore iconName='it-arrow-right' text='Read more' onClick={() => this.setState({isOpen:!this.state.isOpen})}/>
                            </CardBody>
                        </Card>
                    </Col>
                </Row>
                <Modal
                    isOpen={this.state.isOpen}
                    toggle={() => this.setState({isOpen:!this.state.isOpen})}
                    scrollable
                    centered
                    labelledBy='esempio8'
                    size={'lg'}
                >
                    <ModalHeader toggle={() => this.setState({isOpen:!this.state.isOpen})}  id='esempio8'>
                        <h4>
                            {this.props.data.entity.title}
                        </h4>
                        {this.props.data.entity.date}<br/>
                        <div className='etichetta'>
                            <Icon icon='it-settings' />
                            <span>{this.props.data.entity.category}</span>
                        </div>
                    </ModalHeader>
                    <ModalBody>
                        <p>
                            {this.props.data.entity.article}
                        </p>
                    </ModalBody>
                    <ModalFooter>
                        {this.iconLiked()}
                    </ModalFooter>
                </Modal>
            </div>

        )
    }



}