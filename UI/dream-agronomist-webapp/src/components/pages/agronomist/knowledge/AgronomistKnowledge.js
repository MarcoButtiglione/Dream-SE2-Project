import React from "react";
import ApiClient from "../../../../commons/apiClient";

import {
    Button,
    Container,
    Icon,
    Input,
    Modal,
    ModalBody,
    ModalFooter,
    ModalHeader,
    notify,
    TextArea
} from "design-react-kit";
import {KnowledgeCard} from "./KnowledgeCard";
import {Fab} from "@mui/material";

export class AgronomistKnowledge extends React.Component{
    constructor(props) {
        super(props);
        this.state=this.getDefaultState()
        this.client = new ApiClient()
    }

    getDefaultState = () => {
        return {
            knowledge:null,
            search:"",
            openModalNewKnowledge:false,
            titleNewKnowledge:"",
            categoryNewKnowledge:"",
            descriptionNewKnowledge:"",
            articleNewKnowledge:"",
        }
    }

    componentDidMount() {
        this.getKnowledge()
    }

    getKnowledge = () => {
        this.client.GetAllKnowledgeByAgronomist()
            .then((response) => {
                if(response.data.code==200){
                    this.setState({
                        knowledge: response.data.results[0],
                    })
                }else if(response.data.code==404){
                    this.setState({
                        knowledge: null,
                    })
                }else if(response.data.code==400){
                    notify(
                        'Warning',
                        response.data.message,
                        {state:"warning"}
                    )
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
    getKnowledgeByWord = () => {
        this.client.GetAllKnowledgeByWordA(this.state.search)
            .then((response) => {
                if(response.data.code==200){
                    this.setState({
                        knowledge: response.data.results[0],
                    })
                }else if(response.data.code==404){
                    this.setState({
                        knowledge: null,
                    })
                }else if(response.data.code==400){
                    notify(
                        'Warning',
                        response.data.message,
                        {state:"warning"}
                    )
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
    postKnowledge=()=>{
        const knowledge ={
            title:this.state.titleNewKnowledge,
            description:this.state.descriptionNewKnowledge,
            article:this.state.articleNewKnowledge,
            category:this.state.categoryNewKnowledge
        }
        this.client.PostKnowledge(knowledge)
            .then((response) => {
                if(response.data.code==200){
                    notify(
                        'Success',
                        'The Knowledge was successfully created',
                        {state:"success"}
                    )
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
                this.handleToggleModalNew()
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

    knowledgeCard=()=>{
        if(this.state.knowledge){
            return this.state.knowledge.map((data) => <KnowledgeCard key={data.knowledgeId} data={data} getKnowledge={()=>this.getKnowledge()}/>)
        }
    }
    handleToggleModalNew=()=>{
        if(this.state.openModalNewKnowledge){
            this.setState({
                openModalNewKnowledge:false,
                titleNewKnowledge:"",
                categoryNewKnowledge:"",
                descriptionNewKnowledge:"",
                articleNewKnowledge:"",
            })
        }else {
            this.setState({
                openModalNewKnowledge:true,
            })
        }


    }
    handleChange = (name) => event => {
        this.setState({
            [name]: event.target.value,
        });
    };
    handleChangeSearch = event => {
        this.setState({
            search: event.target.value,
        },this.getKnowledgeByWord);
    };
    render () {
        return(
            <div>
                <Container style={{paddingTop:20,paddingBottom:20,minHeight:500}}>
                    <h4 style={{fontSize:35}}>My Knowledge</h4>
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
                                <button className='btn btn-primary' type='button' id='button-3' onClick={this.getKnowledgeByWord}>
                                    Search
                                </button>
                            </div>
                        </div>
                    </div>
                    {this.knowledgeCard()}
                    <Fab variant="extended" color="primary" aria-label="add"
                         onClick={() => this.setState({openModalNewKnowledge:!this.state.openModalNewKnowledge})}
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
                        Create a new knowledge
                    </Fab>
                    <Modal
                        isOpen={this.state.openModalNewKnowledge}
                        toggle={() => this.handleToggleModalNew()}
                        centered
                        labelledBy='esempio9'
                    >
                        <ModalHeader toggle={() => this.handleToggleModalNew()} id='essempio9'>
                            Create a new knowledge
                        </ModalHeader>
                        <ModalBody>
                            <div className='form-row'>
                                <Input
                                    type='text'
                                    label='Title'
                                    id='inputTitle'
                                    value={this.state.titleNewKnowledge}
                                    onChange={this.handleChange("titleNewKnowledge")}
                                    wrapperClass='col col-md-12'
                                />
                            </div>
                            <div className='form-row'>
                                <Input
                                    type='text'
                                    label='Category'
                                    id='inputTitle'
                                    value={this.state.categoryNewKnowledge}
                                    onChange={this.handleChange("categoryNewKnowledge")}

                                    wrapperClass='col col-md-12'
                                />
                            </div>
                            <div>
                                <TextArea
                                    label='Description'
                                    rows={3}
                                    style={{resize:"none"}}
                                    value={this.state.descriptionNewKnowledge}
                                    onChange={this.handleChange("descriptionNewKnowledge")}
                                />
                            </div>
                            <div>
                                <TextArea
                                    label='Article'
                                    rows={3}
                                    style={{resize:"none"}}
                                    value={this.state.articleNewKnowledge}
                                    onChange={this.handleChange("articleNewKnowledge")}
                                />
                            </div>
                        </ModalBody>
                        <ModalFooter>
                            <Button color='secondary' onClick={() => this.handleToggleModalNew()}>
                                Close
                            </Button>
                            <Button color='primary' onClick={() => this.postKnowledge()}>
                                OK
                            </Button>
                        </ModalFooter>
                    </Modal>
                </Container>
            </div>

        )
    }
}