import {
    Button,
    Container, Icon, Input, Modal, ModalBody, ModalFooter, ModalHeader, notify, Table, TextArea,
} from "design-react-kit";
import React, {useState} from 'react';
import ApiClient from "../../../../commons/apiClient";
import FullCalendar from '@fullcalendar/react' // must go before plugins
import dayGridPlugin from '@fullcalendar/daygrid' // a plugin!
import interactionPlugin from "@fullcalendar/interaction"
import {Fab, IconButton} from "@mui/material";
import EditOutlinedIcon from "@mui/icons-material/EditOutlined";
import DeleteOutlineOutlinedIcon from "@mui/icons-material/DeleteOutlineOutlined"; // needed for dayClick



function TableRow(props){
    const[newEvaluation,setNewEvaluation]=useState("")
    const[newNote,setNewNote]=useState("")
    const[openModalCloseMeeting,setOpenModalCloseMeeting]=useState(false)
    const client=new ApiClient()
    const closeMeeting=()=>{
        const meetingEv ={
            evaluation:newEvaluation,
            note:newNote,
        }
        client.CloseMeeting(meetingEv,props.meeting.meetingId)
            .then((response) => {
                if(response.data.code==200){
                    notify(
                        'Success',
                        'The Meeting was successfully closed',
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
                props.getMeeting()
                setOpenModalCloseMeeting(!openModalCloseMeeting)

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
    return(
        <tr>
            <td>{props.meeting.farmerName}</td>
            <td>{props.meeting.address}</td>
            <td>{props.meeting.date}</td>
            <td style={{textAlign:"center"}}>
                <IconButton style={{padding:0}} onClick={() =>setOpenModalCloseMeeting(!openModalCloseMeeting)}>
                    <Icon
                        className=""
                        color=""
                        icon="it-pencil"
                        size="sm"
                    />
                </IconButton>
            </td>
            <Modal
                isOpen={openModalCloseMeeting}
                toggle={() => setOpenModalCloseMeeting(!openModalCloseMeeting)}
                centered
                labelledBy='esempio9'
            >
                <ModalHeader toggle={() =>setOpenModalCloseMeeting(!openModalCloseMeeting)} id='essempio9'>
                    Evaluate the meeting
                </ModalHeader>
                <ModalBody>
                    <div className='form-row'>
                        <Input
                            type='number'
                            label='Evaluation'
                            id='inputEvaluation'
                            value={newEvaluation}
                            onChange={(event)=>setNewEvaluation(event.target.value)}
                            wrapperClass='col col-md-12'
                        />
                    </div>
                        <TextArea
                            label='Note'
                            rows={3}
                            style={{resize:"none"}}
                            value={newNote}
                            onChange={(event)=>setNewNote(event.target.value)}
                            wrapperClass='col col-md-12'
                        />



                </ModalBody>
                <ModalFooter>
                    <Button color='secondary' onClick={() =>setOpenModalCloseMeeting(!openModalCloseMeeting)}>
                        Close
                    </Button>
                    <Button color='primary' onClick={() => closeMeeting()}>
                        OK
                    </Button>
                </ModalFooter>
            </Modal>
        </tr>
    )

}


export class AgronomistMeeting extends React.Component{
    constructor(props) {
        super(props);

        this.state=this.getDefaultState()
        this.client = new ApiClient()

    }


    getDefaultState = () => {
        return {
            meetingType:"next",
            meetings:null,
            meetingsCalendar:null,
            openModalNewMeeting:false,
            openModalDetailMeeting:false,
            meetingDetail:null,
            openModalDelete:false,
            openModalEdit:false,
            editingDate:"",
            editingStartTime:"",
            editingEndTime:"",

        }
    }

    componentDidMount() {
        this.getMeeting()
    }
    getMeeting=()=>{
        if(this.state.meetingType==="next"){
            this.getAllMeeting()
        }else if(this.state.meetingType==="conclused"){
            this.getAllMeetingConclused()
        }
    }

    getAllMeeting = () => {
        this.client.GetAllMeetingsA()
            .then((response) => {
                if(response.data.code==200){
                    this.setState({
                        meetings: response.data.results[0],
                    })
                    const meetingsCalendar=response.data.results[0].map((meeting)=>{
                        let color
                        if(meeting.state==="pendant"){
                            color="yellow"
                        }else if(meeting.state==="rejected"){
                            color="red"
                        }else if(meeting.state==="confirmed"){
                            color="green"
                        }
                        let start = meeting.date +" "+ meeting.startTime
                        let end =
                            meeting.date +" "+ meeting.endTime

                        const meetingCal={
                            title:meeting.farmerName,
                            color:color,
                            start:start,
                            end:end,
                            extendedProps: {
                                meeting: meeting
                            },
                        }
                        return(meetingCal)

                    })
                    this.setState({meetingsCalendar:meetingsCalendar})
                }
                else if(response.data.code==404){
                    this.setState({
                        meetings: null,
                        meetingsCalendar:null

                    })
                }else if(response.data.code==400){
                    notify(
                        'Warning',
                        response.data.message,
                        {state:"warning"}
                    )
                    this.setState({
                        meetings: null,
                        meetingsCalendar:null

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
    getAllMeetingConclused = () => {
        this.client.GetAllMeetingsConclused()
            .then((response) => {
                if(response.data.code==200) {
                    this.setState({
                        meetings: response.data.results[0],
                    })
                }
                else if(response.data.code==404){
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
                )
            })
        ;
    };
    putMeeting=()=>{
        const meeting ={
            date:this.state.editingDate,
            startTime:this.state.editingStartTime,
            endTime:this.state.editingEndTime,
        }
        this.client.PutMeeting(meeting,this.state.meetingDetail.meetingId)
            .then((response) => {
                if(response.data.code==200){

                    this.getAllMeeting()
                    notify(
                        'Success',
                        'The Meeting was successfully edited',
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
                this.handleToggleModalEdit()
                this.handleEventClick()

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
    deleteMeeting=()=>{
        this.client.DeleteMeeting(this.state.meetingDetail.meetingId)
            .then((response) => {
                if(response.data.code==200){
                    this.getAllMeeting()
                    notify(
                        'Success',
                        'The Meeting was successfully deleted',
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
                this.handleEventClick()
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


    handleDateClick = (arg) => { // bind with an arrow function
    }
    handleEventClick = (arg) => {
        if(this.state.openModalDetailMeeting){
            this.setState({
                openModalDetailMeeting:false,
                meetingDetail:null,
            })
        }else {
            const meeting=arg.event.extendedProps.meeting
            this.setState({
                openModalDetailMeeting:true,
                meetingDetail:meeting,
            })
        }
    }
    handleToggleModalEdit=()=>{
        if(this.state.openModalEdit){
            this.setState({
                openModalEdit:false,
                editingDate:"",
                editingStartTime:"",
                editingEndTime:"",
            })
        }else {
            const meeting=this.state.meetingDetail
            this.setState({
                openModalEdit:true,
                editingDate:meeting.date,
                editingStartTime:meeting.startTime,
                editingEndTime:meeting.endTime
            })
        }
    }
    handleChange = (name) => event => {
        this.setState({
            [name]: event.target.value,
        });

    };
    handleChangeMeetingType=(type)=>{
        if(type==="next"){
            this.getAllMeeting()
            this.setState({meetingType:"next"})
        }else if(type==="conclused"){
            this.getAllMeetingConclused()
            this.setState({meetingType:"conclused"})
        }
    }
    calendar=()=>{
        if(this.state.meetingType==="next"){
            return(
                <FullCalendar
                    plugins={[ dayGridPlugin, interactionPlugin ]}
                    dateClick={this.handleDateClick}
                    eventClick={this.handleEventClick}
                    initialView="dayGridMonth"
                    weekends={true}
                    events={this.state.meetingsCalendar}
                />
            )
        }else {
            return (
                <Table>
                    <thead>
                    <tr>
                        <th scope='col' >Farmer Name</th>
                        <th scope='col'width={"30%"}>Farm address</th>
                        <th scope='col'>Meeting date</th>
                        <th scope='col' width={'5%'} style={{textAlign:"center"}}>Evaluate</th>
                    </tr>
                    </thead>
                    <tbody>
                    {this.tableBody()}
                    </tbody>
                </Table>
            )
        }
    }
    tableBody=()=>{
        if(this.state.meetings){
            const tableB=this.state.meetings.map((meeting)=> {
                    return (
                        <TableRow meeting={meeting} getMeeting={()=>this.getMeeting()}/>
                    )
                }
            )
            return tableB
        }else{
            return (
                <tr>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
            )
        }
    }


    render () {
        return(
            <div>
                <Container style={{paddingTop:20,paddingBottom:20, minHeight:500}} >
                    <div style={{marginBottom:20}}>
                        <Button color={this.state.meetingType==="next"?"primary":"secondary"}
                                size='sm'
                                onClick={()=>this.handleChangeMeetingType("next")}
                                style={{marginRight:10}}
                        >
                            Next meetings
                        </Button>
                        <Button color={this.state.meetingType==="conclused"?"primary":"secondary"}
                                size='sm'
                                onClick={()=>this.handleChangeMeetingType("conclused")}
                                style={{marginRight:10}}
                        >
                            Concluded
                        </Button>
                    </div>
                    {this.calendar()}

                    <Modal
                        isOpen={this.state.openModalDetailMeeting}
                        toggle={() => this.handleEventClick()}
                        centered
                        labelledBy='esempio9'
                    >
                        <ModalHeader toggle={() => this.handleEventClick()} id='essempio9'>
                            Marco Domenico Buttiglione
                        </ModalHeader>
                        <ModalBody>
                            <p>Date: {
                                this.state.meetingDetail?
                                    this.state.meetingDetail.date+" "+this.state.meetingDetail.startTime+"-"+this.state.meetingDetail.endTime:
                                    ""
                            }</p>
                            <p>{this.state.meetingDetail?this.state.meetingDetail.address:""}</p>
                        </ModalBody>
                        <ModalFooter>
                                <IconButton onClick={() => this.handleToggleModalEdit()}>
                                    <EditOutlinedIcon size="sm" />
                                </IconButton>
                                <IconButton onClick={() => this.setState({openModalDelete:!this.state.openModalDelete})}>
                                    <DeleteOutlineOutlinedIcon size="sm" />
                                </IconButton>
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
                            <Button color='primary' onClick={() => this.deleteMeeting()}>
                                OK
                            </Button>
                        </ModalFooter>
                    </Modal>
                    <Modal
                        isOpen={this.state.openModalEdit}
                        toggle={() => this.handleToggleModalEdit()}
                        centered
                        labelledBy='esempio9'
                    >
                        <ModalHeader toggle={() => this.handleToggleModalEdit()} id='essempio9'>
                            Edit the meeting
                        </ModalHeader>
                        <ModalBody>
                                <div className='input-group'>
                                    <label htmlFor='input-group-1'>Date</label>
                                    <input
                                        type='date'
                                        id='input-group-1'
                                        name='input-group-1'
                                        value={this.state.editingDate}
                                        onChange={this.handleChange("editingDate")}
                                    />
                                </div>
                                <div className='input-group'>
                                    <label htmlFor='input-group-1'>Start</label>
                                    <input
                                        type='time'
                                        id='input-group-1'
                                        name='input-group-1'
                                        value={this.state.editingStartTime}
                                        onChange={this.handleChange("editingStartTime")}
                                    />
                                </div>
                                <div className='input-group'>
                                    <label htmlFor='input-group-1'>End</label>
                                    <input
                                        type='time'
                                        id='input-group-1'
                                        name='input-group-1'
                                        value={this.state.editingEndTime}
                                        onChange={this.handleChange("editingEndTime")}
                                    />
                                </div>
                        </ModalBody>
                        <ModalFooter>
                            <Button color='secondary' onClick={() => this.handleToggleModalEdit()}>
                                Close
                            </Button>
                            <Button color='primary' onClick={() => this.putMeeting()}>
                                OK
                            </Button>
                        </ModalFooter>
                    </Modal>
                </Container>
            </div>

        )
    }
}