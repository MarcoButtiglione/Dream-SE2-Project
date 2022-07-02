import {
    Card,
    CardBody,
    CardReadMore,
    CardText,
    CardTitle,
    Col,
    Container, Icon, LinkList, LinkListItem, Modal, ModalBody, ModalFooter, ModalHeader, notify,
    Row
} from "design-react-kit";
import WeatherCard from "./WeatherCard";
import React from 'react';
import axios from 'axios'
import ApiClient from "../../../../commons/apiClient";
import {useNavigate} from "react-router-dom";
import './Style_home.css'
import {IconButton} from "@mui/material";
import FavoriteIcon from "@mui/icons-material/Favorite";
import FavoriteBorderIcon from "@mui/icons-material/FavoriteBorder";

/**
 * Buttons ReadMore
 * */
function CardReadMoreLinkTo(to) {
    let navigate = useNavigate()
    function handleClick(to){
        navigate(to.to)
    }
    return(
        <CardReadMore iconName='it-arrow-right' text='Read more'  onClick={() => handleClick(to)}/>
    )
}
function NotificationLinkTo(props) {
    let navigate = useNavigate()
    function handleClick(to,id){
        props.delete(id)
        props.refresh()
        navigate(to)
    }
    return(
        <LinkListItem className="icon-left" key={props.key} onClick={() => handleClick(props.to,props.notificationId)}>
            {props.icon}
            <span style={{marginLeft:10,lineHeight:"150%"}}>{props.description}</span>
        </LinkListItem>
    )
}
function MeetingLinkTo(props) {
    let navigate = useNavigate()
    function handleClick(){
        navigate("/farmer/meeting")
    }
    return(

        <LinkListItem className="icon-left" onClick={()=>handleClick()}>
            <Icon color="primary" icon="it-calendar"/>
            <span style={{marginLeft:10,lineHeight:"150%"}}>{props.text}</span>
        </LinkListItem>
    )
}

/**
 * Farmer Home component
 * */
export class FarmerHome extends React.Component{
    /**
     * Constructor and default state
     * */
    constructor(props) {
        super(props);
        this.state=this.getDefaultState()
        this.client = new ApiClient()
    }
    getDefaultState = () => {
        return {
            weatherResult: null,
            knowledge:null,
            meetings: null,
            notifications:null,
            openModalKnowledge:false,
            selectedKnowledge:null,
        }
    }

    /**
     * The main functions that must be executed when the component is rendered
     * */
    componentDidMount() {
        this.getKnowledge()
        this.getMeeting()
        this.getNotification()
        this.onSearchSubmit("Hyderabad")

    }
    getKnowledge = () => {
        this.client.GetAllKnowledge()
            .then((response) => {
                if(response.data.code==200){
                    this.setState({
                        knowledge: response.data.results[0],
                    })
                }else if(response.data.code==404){
                    this.setState({
                        knowledge: null,
                    })
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
    };
    getMeeting = () => {
        this.client.GetAllMeetingsF()
            .then((response) => {
                if(response.data.code==200){
                    this.setState({
                        meetings: response.data.results[0],
                    })
                }else if(response.data.code==404){
                    this.setState({
                        meetings: null,
                    })
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
    };
    getNotification = () => {
        this.client.GetAllNotificationF()
            .then((response) => {
                if(response.data.code==200){
                    this.setState({
                        notifications: response.data.results[0],
                    })
                }else if(response.data.code==404){
                    this.setState({
                        notifications: null,
                    })
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
    deleteNotification = (id) => {
        this.client.DeleteNotificationF(id)
            .then((response) => {
                if(response.data.code==404){
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
                this.getNotification()
            })
            .catch((error) => {
                notify(
                    'Error',
                    'Something went wrong!',
                    {state:"error"}
                )            })
        ;
    };
    likeKnowledge=()=>{
        this.client.LikeKnowledge(this.state.selectedKnowledge.entity.knowledgeId)
            .then((response) => {
                if(response.data.code==200){
                    this.handleToggleModalKnowledge()
                    this.getKnowledge()
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
    }
    unLikeKnowledge=()=>{
        this.client.UnLikeKnowledge(this.state.selectedKnowledge.entity.knowledgeId)
            .then((response) => {
                if(response.data.code==200){
                    this.handleToggleModalKnowledge()
                    this.getKnowledge()
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

    /**
     * The mock up function used to acquire weather forecasting data
     **/
    onSearchSubmit = async (searchInputValue) => {
        const response = await axios.get(`https://api.openweathermap.org/data/2.5/weather?q=${searchInputValue}&appid=ca261c971d5638db9d4d6cbccc1f093d`)
        this.setState({weatherResult: response.data})
    }

    /**
     * Some internal component of the Home
     **/
    knowledgeItem=()=>{
        if(this.state.knowledge){
            const cards=this.state.knowledge.map((knowledge)=>{
                return(
                    <LinkListItem className="icon-left" onClick={()=>this.handleToggleModalKnowledge(knowledge)}>
                        <Icon color="primary" icon="it-files"/>
                        <span style={{marginLeft:10}}>{knowledge.entity.title}</span>
                    </LinkListItem>
                )
            })
            return(cards)
        }
    }
    handleToggleModalKnowledge=(knowledge)=>{
        if(this.state.openModalKnowledge){
            this.setState({
                openModalKnowledge:false,
                selectedKnowledge:null,
            })
        }else {
            this.setState({
                openModalKnowledge:true,
                selectedKnowledge:knowledge,
            })
        }
    }
    meetingItem=()=>{
        if(this.state.meetings){
            const cards=this.state.meetings.map((meeting)=>{
                return(
                    <MeetingLinkTo text={
                        "Routine visit: "+meeting.date+" "+meeting.startTime+" "+
                        "Status: "+meeting.state
                    }
                    />
                )
            })
            return(cards)
        }
    }
    notificationItem=()=>{
        if(this.state.notifications){
            const cards=this.state.notifications.map((notification)=>{
                let icon = <Icon color="primary" icon="it-mail"/>
                let to="#"
                if(notification.type==="NEW_MEETING"){
                    to="/farmer/meeting"
                    icon = <Icon color="primary" icon="it-calendar"/>
                }else if (notification.type==="NEW_COMMENT"){
                    to="/farmer/forum"
                    icon = <Icon color="primary" icon="it-comment"/>
                }else if (notification.type==="LIKE_COMMENT"){
                    to="/farmer/forum"
                    icon = <Icon color="primary" icon="it-star-outline"/>
                }else if (notification.type==="NEW_SOILDATA"){
                    to="/farmer/production/soildata"
                    icon = <Icon color="primary" icon="it-inbox"/>
                }
                return(
                    <NotificationLinkTo notificationId={notification.notificationId} icon={icon} to={to} description={notification.description} delete={this.deleteNotification} refresh={this.getNotification}/>
                )
            })
            return(cards)
        }
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
    iconLiked=()=>{
        if(this.state.selectedKnowledge){
            if(this.state.selectedKnowledge.liked){
                return (
                    <IconButton onClick={()=>this.unLikeKnowledge()} style={{marginRight:30}}>
                        <FavoriteIcon  size="xs" />
                        <span>{this.state.selectedKnowledge.entity.likes}</span>
                    </IconButton>
                )
            }else {
                return (
                    <IconButton onClick={()=>this.likeKnowledge()} style={{marginRight:30}}>
                        <FavoriteBorderIcon size="xs" />
                        <span>{this.state.selectedKnowledge.entity.likes}</span>

                    </IconButton>

                )
            }
        }

    }


    render () {
        return(
            <div>
                <Container  style={{paddingTop:20,paddingBottom:20}}>
                    <Row>
                        <Col
                            lg='12'
                            xl='4'
                        >
                            {this.state.weatherResult ?  <WeatherCard weatherResult = {this.state.weatherResult} /> : <div></div>}
                        </Col>
                        <Col
                            lg='12'
                            xl='4'
                        >
                            <Card
                                spacing className="no-after rounded shadow"
                            >
                                <CardBody className="pb-5">

                                    <CardTitle className="h5">
                                        Knowledge
                                    </CardTitle>
                                    <div
                                        className="overflow-auto"
                                        style={{height:300}}
                                    >
                                        <LinkList >
                                            {this.knowledgeItem()}
                                        </LinkList>
                                    </div>
                                    <CardReadMoreLinkTo to={"/farmer/knowledge"}/>

                                </CardBody>
                            </Card>
                            <Modal
                                isOpen={this.state.openModalKnowledge}
                                toggle={() => this.handleToggleModalKnowledge()}
                                scrollable
                                centered
                                labelledBy='esempio8'
                                size={'lg'}
                            >
                                <ModalHeader toggle={() => this.handleToggleModalKnowledge()}  id='esempio8'>
                                    <h4>
                                        {this.state.selectedKnowledge?this.state.selectedKnowledge.entity.title:""}
                                    </h4>
                                    {this.state.selectedKnowledge?this.state.selectedKnowledge.entity.date:""}
                                    <br/>
                                    <div className='etichetta'>
                                        <Icon icon='it-settings' />
                                        <span>{this.state.selectedKnowledge?this.state.selectedKnowledge.entity.category:""}</span>
                                    </div>
                                </ModalHeader>
                                <ModalBody>
                                    <p>
                                        {this.state.selectedKnowledge?this.state.selectedKnowledge.entity.article:""}
                                    </p>
                                </ModalBody>
                                <ModalFooter>
                                    {this.iconLiked()}
                                </ModalFooter>
                            </Modal>
                        </Col>
                        <Col
                            lg='12'
                            xl='4'
                        >
                            <Card
                                spacing className="no-after "
                            >
                                <Card spacing className='card-bg rounded'>
                                    <CardBody>
                                        <CardTitle className="h5">
                                            New Meetings
                                        </CardTitle>
                                        <div
                                            className="overflow-auto"
                                            style={{height:150}}
                                        >
                                            <LinkList >
                                                {this.meetingItem()}
                                            </LinkList>
                                        </div>
                                        <CardReadMoreLinkTo to={"/farmer/meeting"}/>
                                    </CardBody>
                                </Card>
                                <Card  className='card-bg rounded no-after'>
                                    <CardBody>
                                        <CardTitle className='h5'>
                                            Notifications
                                        </CardTitle>
                                        <div
                                            className="overflow-auto"
                                            style={{height:200}}
                                        >
                                            <LinkList >
                                                {this.notificationItem()}
                                            </LinkList>
                                        </div>

                                    </CardBody>
                                </Card>
                            </Card>
                        </Col>
                    </Row>

                </Container>

            </div>

        )
    }
}