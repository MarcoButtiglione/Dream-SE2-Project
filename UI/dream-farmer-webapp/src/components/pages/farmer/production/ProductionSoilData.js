import {Container, Icon, notify, Select, Table} from "design-react-kit";
import React from "react";
import ApiClient from "../../../../commons/apiClient";
import {IconButton} from "@mui/material";

export class ProductionSoilData extends React.Component{
    constructor(props) {
        super(props);
        this.state=this.getDefaultState()
        this.client = new ApiClient()
    }
    getDefaultState = () => {
        return {
            soildata:null,
        }
    }
    componentDidMount() {
        this.getSoilData()
    }

    getSoilData = () => {
        this.client.GetSoilDataOfFarm()
            .then((response) => {
                if(response.data.code==200){
                    this.setState({
                        soildata: response.data.results[0],
                    })
                }else if(response.data.code==404){
                    this.setState({
                        soildata: null,
                    })
                }else if(response.data.code==400){
                    this.setState({
                        soildata: null,
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
    tableBody=()=>{
        if(this.state.soildata){
            const tableB=this.state.soildata.map((data)=>{
                return(
                    <tr key={data.soilDataId}>
                        <td>{data.date}</td>
                        <td style={{textAlign:"right"}}>{data.ph}</td>
                        <td style={{textAlign:"right"}}>{data.limestone}</td>
                        <td style={{textAlign:"right"}}>{data.nitrogen}</td>
                        <td style={{textAlign:"right"}}>{data.phosphorus}</td>
                        <td style={{textAlign:"right"}}>{data.organic_carbon}</td>
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
                    <td></td>
                    <td></td>
                    <td></td>

                </tr>
            )
        }
    }
    render(){
        return(
            <div>
                <Container style={{paddingTop:20,paddingBottom:20}}>
                    <h4 style={{fontSize:35}}>Soil Data</h4>
                    <Table>
                        <thead>
                        <tr>
                            <th scope='col' >Data</th>
                            <th scope='col' style={{textAlign:"right"}}>Ph</th>
                            <th scope='col' style={{textAlign:"right"}}>Limestone*</th>
                            <th scope='col' style={{textAlign:"right"}}>Nitrogen*</th>
                            <th scope='col' style={{textAlign:"right"}}>Phosphorus*</th>
                            <th scope='col' style={{textAlign:"right"}}>Organic carbon*</th>
                        </tr>
                        </thead>
                        <tbody>
                        {this.tableBody()}
                        </tbody>
                    </Table>
                    <span>*the unit of measurement is [mg/Kg]</span>
                </Container>
            </div>
        )
    }

}