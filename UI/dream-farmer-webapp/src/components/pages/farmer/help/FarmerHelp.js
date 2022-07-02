import {
    Button,
    Container, Icon, Input, Modal, ModalBody, ModalFooter, ModalHeader, notify, TextArea,
} from "design-react-kit";
import React from 'react';
import {Request} from "./Request";
import ApiClient from "../../../../commons/apiClient";
import {Fab} from "@mui/material";



export class FarmerHelp extends React.Component{
    constructor(props) {
        super(props);
        this.state=this.getDefaultState()
        this.client = new ApiClient()
    }


    getDefaultState = () => {
        return {
            requests:null,
            subjectNewRequest:"",
            bodyNewRequest:"",
            openModalNewRequest:false,
            requestType:"pendant",
        }
    }

    componentDidMount() {
        this.getRequest()
    }

    getRequest=()=>{
        if(this.state.requestType==="pendant"){
            this.getRequestPendant()
        }else if(this.state.requestType==="nofeed"){
            this.getRequestNoFeed()
        }else if(this.state.requestType==="closed"){
            this.getRequestClosed()
        }
    }
    getRequestPendant = () => {
        this.client.GetRequestByFarmerPendant()
            .then((response) => {
                if(response.data.code==200){
                    this.setState({
                        requests: response.data.results[0],
                    })
                }else if(response.data.code==404){
                    this.setState({
                        requests: null,
                    })
                }else if(response.data.code==400){
                    this.setState({
                        requests: null,
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
    getRequestNoFeed = () => {
        this.client.GetRequestByFarmerNoFeed()
            .then((response) => {
                if(response.data.code==200){
                    this.setState({
                        requests: response.data.results[0],
                    })
                }else if(response.data.code==404){
                    this.setState({
                        requests: null,
                    })
                }else if(response.data.code==400){
                    this.setState({
                        requests: null,
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
    getRequestClosed = () => {
        this.client.GetRequestByFarmerClosed()
            .then((response) => {
                if(response.data.code==200){
                    this.setState({
                        requests: response.data.results[0],
                    })
                }else if(response.data.code==404){
                    this.setState({
                        requests: null,
                    })
                }else if(response.data.code==400){
                    this.setState({
                        requests: null,
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
    postRequest=()=>{
        const request ={
            subject:this.state.subjectNewRequest,
            body:this.state.bodyNewRequest,
        }
        this.client.PostRequest(request)
            .then((response) => {
                if(response.data.code==200){
                    this.getRequest()
                    notify(
                        'Success',
                        'The Help Request was successfully created',
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
                this.handleToggleModalNewRequest()

            })
            .catch((error) => {
                notify(
                    'Error',
                    'Something went wrong!',
                    {state:"error"}
                )            })
        ;

    }

    requests=()=>{
        if(this.state.requests){
            return this.state.requests.map((data) => <Request key={data.requestId} data={data} getRequest={()=>this.getRequest()}/>)
        }
    }
    handleChangeRequestType=(type)=>{
        if(type==="pendant"){
            this.getRequestPendant()
            this.setState({requestType:"pendant"})
        }else if(type==="nofeed"){
            this.getRequestNoFeed()
            this.setState({requestType:"nofeed"})
        }else if(type==="closed"){
            this.getRequestClosed()
            this.setState({requestType:"closed"})
        }
    }
    handleToggleModalNewRequest=()=>{
        if(this.state.openModalNewRequest){
            this.setState({
                openModalNewRequest:false,
                subjectNewRequest:"",
                bodyNewRequest:"",
            })
        }else {
            this.setState({
                openModalNewRequest:true,
            })
        }
    }
    handleChange = (name) => event => {
        this.setState({
            [name]: event.target.value,
        });
    };

    render () {
        return(
            <div>
                <Container style={{paddingTop:20,paddingBottom:20, minHeight:500}} >
                    <h4 style={{fontSize:35}}>Help Requests</h4>
                    <div style={{marginBottom:20}}>
                        <Button color={this.state.requestType==="pendant"?"primary":"secondary"}
                                size='sm'
                                onClick={()=>this.handleChangeRequestType("pendant")}
                                style={{marginRight:10}}
                        >
                            Pendant
                        </Button>
                        <Button color={this.state.requestType==="nofeed"?"primary":"secondary"}
                                size='sm'
                                onClick={()=>this.handleChangeRequestType("nofeed")}
                                style={{marginRight:10}}
                        >
                            No feed
                        </Button>
                        <Button color={this.state.requestType==="closed"?"primary":"secondary"}
                                size='sm'
                                onClick={()=>this.handleChangeRequestType("closed")}
                        >
                            Closed
                        </Button>
                    </div>
                    {this.requests()}
                    <Fab variant="extended" color="primary" aria-label="add"
                         onClick={() => this.handleToggleModalNewRequest()}
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
                        Create a new help request
                    </Fab>
                    <Modal
                        isOpen={this.state.openModalNewRequest}
                        toggle={() => this.handleToggleModalNewRequest()}
                        centered
                        labelledBy='esempio9'
                    >
                        <ModalHeader toggle={() => this.handleToggleModalNewRequest()} id='essempio9'>
                            Create a new help request
                        </ModalHeader>
                        <ModalBody>
                            <div className='form-row'style={{marginTop:20}}>
                                <Input
                                    type='text'
                                    label='Subject'
                                    id='inputTitle'
                                    value={this.state.subjectNewRequest}
                                    onChange={this.handleChange("subjectNewRequest")}

                                    wrapperClass='col col-md-12'
                                />
                            </div>
                            <div>
                                <TextArea
                                    label='Body'
                                    rows={3}
                                    style={{resize:"none"}}
                                    value={this.state.bodyNewRequest}
                                    onChange={this.handleChange("bodyNewRequest")}
                                />
                            </div>
                        </ModalBody>
                        <ModalFooter>
                            <Button color='secondary' onClick={() => this.handleToggleModalNewRequest()}>
                                Close
                            </Button>
                            <Button color='primary' onClick={() => this.postRequest()}>
                                OK
                            </Button>
                        </ModalFooter>
                    </Modal>

                </Container>
            </div>

        )
    }
}