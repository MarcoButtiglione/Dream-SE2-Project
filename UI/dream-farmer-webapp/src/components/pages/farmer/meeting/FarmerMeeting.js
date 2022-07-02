import {Button, Container, Icon, notify, Select, Table} from "design-react-kit";
import React from "react";
import ApiClient from "../../../../commons/apiClient";
import {IconButton} from "@mui/material";

export class FarmerMeeting extends React.Component{
    constructor(props) {
        super(props);
        this.state=this.getDefaultState()
        this.client = new ApiClient()
    }
    getDefaultState = () => {
        return {
            meetings:null,
            meetingType:"next"
        }
    }
    componentDidMount() {
        this.getMeeting()
    }
    getMeeting=()=>{
        if(this.state.meetingType==="next"){
            this.getNextMeeting()
        }else if(this.state.meetingType==="closed"){
            this.getClosedMeeting()
        }
    }

    getNextMeeting = () => {
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
                }else if(response.data.code==400){
                    notify(
                        'Warning',
                        response.data.message,
                        {state:"warning"}
                    )
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
                )            })
        ;
    };
    getClosedMeeting = () => {
        this.client.GetAllMeetingsClosed()
            .then((response) => {
                console.log(response.data)
                if(response.data.code==200){
                    this.setState({
                        meetings: response.data.results[0],
                    })
                }else if(response.data.code==404){
                    this.setState({
                        meetings: null,
                    })
                }else if(response.data.code==400){
                    notify(
                        'Warning',
                        response.data.message,
                        {state:"warning"}
                    )
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
                )            })
        ;
    };
    changeStatusMeeting=(id,state)=>{
        this.client.ChangeStatusMeeting(id,state)
            .then((response) => {
                if(response.data.code==200){
                    notify(
                        'Success',
                        'The Meeting was successfully changed the status',
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
                this.getMeeting()
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

    tableBodyNext=()=>{
        if(this.state.meetings){
            const tableB=this.state.meetings.map((meeting)=>{
                if(meeting.state==="pendant"){
                    return(
                        <tr key={meeting.meetingId}>
                            <td>{meeting.date+" "+meeting.startTime+"-"+meeting.endTime}</td>
                            <td>{meeting.state}</td>
                            <td> <Button color='success' onClick={()=>this.changeStatusMeeting(meeting.meetingId,"confirmed")}>Confirm</Button>{' '}</td>
                            <td><Button color='danger' onClick={()=>this.changeStatusMeeting(meeting.meetingId,"rejected")}>Reject</Button>{' '}</td>
                        </tr>
                    )
                }else if(meeting.state==="confirmed"){
                    return(
                        <tr key={meeting.meetingId}>
                            <td>{meeting.date+" "+meeting.startTime+"-"+meeting.endTime}</td>
                            <td>{meeting.state}</td>
                            <td></td>
                            <td><Button color='danger' onClick={()=>this.changeStatusMeeting(meeting.meetingId,"rejected")}>Reject</Button>{' '}</td>
                        </tr>
                    )
                }else if(meeting.state==="rejected"){
                    return(
                        <tr key={meeting.meetingId}>
                            <td>{meeting.date+" "+meeting.startTime+"-"+meeting.endTime}</td>
                            <td>{meeting.state}</td>
                            <td></td>
                            <td></td>
                        </tr>
                    )
                }

            })
            return tableB
        }else{
            return (
                <tr>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td>
                    </td>

                </tr>
            )
        }
    }
    tableBodyClosed=()=>{
        if(this.state.meetings){
            const tableB=this.state.meetings.map((meeting)=>{
                    return(
                        <tr key={meeting.meetingId}>
                            <td>{meeting.date+" "+meeting.startTime+"-"+meeting.endTime}</td>
                            <td>{meeting.evaluation}</td>
                            <td>{meeting.note}</td>
                        </tr>
                    )
            })
            return tableB
        }else{
            return (
                <tr>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
            )
        }
    }

    handleChangeMeetingType=(type)=>{
        if(type==="next"){
            this.getNextMeeting()
            this.setState({meetingType:"next"})
        }else if(type==="closed"){
            this.getClosedMeeting()
            this.setState({meetingType:"closed"})
        }
    }
    table=()=>{
        if(this.state.meetingType==="next"){
            return(
                <Table>
                    <thead>
                    <tr>
                        <th scope='col' width={'30%'}>Date</th>
                        <th scope='col'>State</th>
                        <th scope='col' width={'10%'}></th>
                        <th scope='col' width={'10%'}></th>
                    </tr>
                    </thead>
                    <tbody>
                    {this.tableBodyNext()}
                    </tbody>
                </Table>

            )
        }else if(this.state.meetingType==="closed"){
            return(
                <Table>
                    <thead>
                    <tr>
                        <th scope='col' >Date</th>
                        <th scope='col'>Evaluation</th>
                        <th scope='col' width={'50%'}>Note</th>
                    </tr>
                    </thead>
                    <tbody>
                    {this.tableBodyClosed()}
                    </tbody>
                </Table>
            )

        }
    }

    render(){
        return(
            <div>
                <Container style={{paddingTop:20,paddingBottom:20}}>
                    <h4 style={{fontSize:35}}>Meetings</h4>
                    <div style={{marginBottom:20}}>
                        <Button color={this.state.meetingType==="next"?"primary":"secondary"}
                                size='sm'
                                onClick={()=>this.handleChangeMeetingType("next")}
                                style={{marginRight:10}}
                        >
                            Next meetings
                        </Button>
                        <Button color={this.state.meetingType==="closed"?"primary":"secondary"}
                                size='sm'
                                onClick={()=>this.handleChangeMeetingType("closed")}
                                style={{marginRight:10}}
                        >
                            Closed
                        </Button>
                        {this.table()}
                    </div>

                </Container>
            </div>
        )
    }

}