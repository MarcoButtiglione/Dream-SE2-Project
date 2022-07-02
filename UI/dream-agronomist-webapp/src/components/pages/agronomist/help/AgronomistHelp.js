import {
    Button,
    Container, notify,
} from "design-react-kit";
import React from 'react';
import {Request} from "./Request";
import ApiClient from "../../../../commons/apiClient";



export class AgronomistHelp extends React.Component{
    constructor(props) {
        super(props);
        this.state=this.getDefaultState()
        this.client = new ApiClient()
    }


    getDefaultState = () => {
        return {
            requests:null,
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
        this.client.GetRequestByAgronomistPendant()
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
        this.client.GetRequestByAgronomistNoFeed()
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
                )
            })
        ;
    };
    getRequestClosed = () => {
        this.client.GetRequestByAgronomistClosed()
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
                )
            })
        ;
    };

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
                </Container>
            </div>

        )
    }
}