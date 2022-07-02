import {
    Button,
    Container,
    Icon,
    Input,
    Modal, ModalBody, ModalFooter, ModalHeader, notify, TextArea
} from "design-react-kit";
import {useNavigate} from "react-router-dom";
import React from 'react';
import {Topic} from "./Topic";
import ApiClient from "../../../../commons/apiClient";
import {Fab} from "@mui/material";

function ButtonAllTopics() {
    let navigate = useNavigate()
    function toAllTopics(){
        navigate("/farmer/forum/")
    }
    return(
        <button className='btn btn-secondary' type='button' id='button-3' onClick={()=>toAllTopics()} style={{marginLeft:10}}>
            All topics
        </button>
    )
}

export class FarmerForumMyTopics extends React.Component{
    constructor(props) {
        super(props);
        this.state=this.getDefaultState()
        this.client = new ApiClient()
    }
    getDefaultState = () => {
        return {
            topics:null,
            search:"",
            openModalNewTopic:false,
            titleNewTopic:"",
            descriptionNewTopic:"",
            tagNewTopic:""
        }
    }
    componentDidMount() {
        this.getTopicByFarmer()
    }

    getTopicByFarmer = () => {
        this.client.GetAllTopicByFarmer()
            .then((response) => {
                if(response.data.code==200){
                    this.setState({
                        topics: response.data.results[0],
                    })
                }else if(response.data.code==404){
                    this.setState({
                        topics: null,
                    })
                }else if(response.data.code==400){
                    notify(
                        'Warning',
                        response.data.message,
                        {state:"warning"}
                    )
                    this.setState({
                        topics: null,
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
    getTopicByWord = () => {
        this.client.GetAllTopicByWordF(this.state.search)
            .then((response) => {
                if(response.data.code==200){
                    this.setState({
                        topics: response.data.results[0],
                    })
                }else if(response.data.code==404){
                    this.setState({
                        topics: null,
                    })
                }else if(response.data.code==400){
                    notify(
                        'Warning',
                        response.data.message,
                        {state:"warning"}
                    )
                    this.setState({
                        topics: null,
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
    postTopic=()=>{
        const topic ={
            topic:this.state.titleNewTopic,
            description:this.state.descriptionNewTopic,
            tag:this.state.tagNewTopic
        }
        this.client.PostTopic(topic)
            .then((response) => {
                if(response.data.code==200){
                    this.getTopicByFarmer()
                    notify(
                        'Success',
                        'The Topic was successfully created',
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
                this.handleToggleModalNewTopic()

            })
            .catch((error) => {
                notify(
                    'Error',
                    'Something went wrong!',
                    {state:"error"}
                )            })
        ;

    }

    topics=()=>{
        if(this.state.topics){
            return this.state.topics.map((data) => <Topic key={data.topicId} farmer={this.props.farmer} data={data} getTopic={()=>this.getTopicByFarmer()}/>)
        }
    }
    handleChangeSearch = event => {
        this.setState({
            search: event.target.value,
        },this.getTopicByWord);
    };
    handleChange = (name) => event => {
        this.setState({
            [name]: event.target.value,
        });
    };
    handleToggleModalNewTopic=()=>{
        if(this.state.openModalNewTopic){
            this.setState({
                openModalNewTopic:false,
                titleNewTopic:"",
                tagNewTopic:"",
                descriptionNewTopic:"",
            })
        }else {
            this.setState({
                openModalNewTopic:true,
            })
        }
    }


    render () {
        return(
            <div>
                <Container style={{paddingTop:20,paddingBottom:20, minHeight:500}} >
                    <h4 style={{fontSize:35}}>My Topics</h4>

                    <Fab variant="extended" color="primary" aria-label="add"
                         onClick={() => this.handleToggleModalNewTopic()}
                         style={{
                             margin: 0,
                             top: 'auto',
                             right: 20,
                             bottom: 20,
                             left: 'auto',
                             position: 'fixed',
                         }}
                    >
                        <Icon color='white' icon='it-plus' />
                        Create a new topic
                    </Fab>
                    <div className='form-group'>
                        <div className='input-group'>
                            <div className='input-group-prepend'>
                                <div className='input-group-text'>
                                    <Icon icon='it-search' color='primary' aria-hidden size='sm' />
                                </div>
                            </div>
                            <input
                                type='text'
                                className={'form-control'}
                                id='input-group-3'
                                name='input-group-3'
                                placeholder='Search'
                                onChange={(event)=>this.handleChangeSearch(event)}
                                value={this.state.search}
                            />
                            <div className='input-group-append'>
                                <button className='btn btn-primary' type='button' id='button-3' onClick={this.getTopicByWord}>
                                    Search
                                </button>
                                <ButtonAllTopics/>

                            </div>

                        </div>

                    </div>
                    {this.topics()}
                    <Modal
                        isOpen={this.state.openModalNewTopic}
                        toggle={() => this.handleToggleModalNewTopic()}
                        centered
                        labelledBy='esempio9'
                    >
                        <ModalHeader toggle={() => this.handleToggleModalNewTopic()} id='essempio9'>
                            Create a new topic
                        </ModalHeader>
                        <ModalBody>
                            <div className='form-row'style={{marginTop:20}}>
                                <Input
                                    type='text'
                                    label='Title'
                                    id='inputTitle'
                                    value={this.state.titleNewTopic}
                                    onChange={this.handleChange("titleNewTopic")}

                                    wrapperClass='col col-md-12'
                                />
                            </div>
                            <div className='form-row'>
                                <Input
                                    type='text'
                                    label='Tag'
                                    id='inputTitle'
                                    value={this.state.tagNewTopic}
                                    onChange={this.handleChange("tagNewTopic")}

                                    wrapperClass='col col-md-12'
                                />
                            </div>
                            <div>
                                <TextArea
                                    label='Body'
                                    rows={3}
                                    style={{resize:"none"}}
                                    value={this.state.descriptionNewTopic}
                                    onChange={this.handleChange("descriptionNewTopic")}
                                />
                            </div>
                        </ModalBody>
                        <ModalFooter>
                            <Button color='secondary' onClick={() => this.handleToggleModalNewTopic()}>
                                Close
                            </Button>
                            <Button color='primary' onClick={() => this.postTopic()}>
                                OK
                            </Button>
                        </ModalFooter>
                    </Modal>


                </Container>

            </div>

        )
    }
}