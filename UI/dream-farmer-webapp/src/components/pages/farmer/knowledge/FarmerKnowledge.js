import {
    Container, Icon, notify,
} from "design-react-kit";
import React from 'react';
import {KnowledgeCard} from "./KnowledgeCard";
import ApiClient from "../../../../commons/apiClient";


export class FarmerKnowledge extends React.Component{
    constructor(props) {
        super(props);
        this.state=this.getDefaultState()
        this.client = new ApiClient()
    }
    getDefaultState = () => {
        return {
            knowledge:null,
            search:"",
        }
    }
    componentDidMount() {
        this.getKnowledge()
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
        ;
    };
    getKnowledgeByWord = () => {
        this.client.GetAllKnowledgeByWordF(this.state.search)
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

    knowledgeCard=()=>{
        if(this.state.knowledge){
            return this.state.knowledge.map((data) => <KnowledgeCard key={data.knowledgeId} data={data} getKnowledge={()=>this.getKnowledge()}/>)
        }
    }
    handleChange = (event) => {
        this.setState({
            search:event.target.value
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
                <Container style={{paddingTop:20,paddingBottom:20, minHeight:500}}>
                    <h4 style={{fontSize:35}}>Knowledge</h4>

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
                </Container>
            </div>

        )
    }
}