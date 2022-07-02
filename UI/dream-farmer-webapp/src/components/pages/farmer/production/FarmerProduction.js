import {
    Card,
    CardBody,
    CardReadMore,
    CardTitle,
    Col,
    Container,
    notify,
    Row, Select, Table
} from "design-react-kit";
import React from 'react';
import { LineChart, Line, CartesianGrid, XAxis, YAxis, Tooltip } from 'recharts';
import { useNavigate } from 'react-router-dom';
import ApiClient from "../../../../commons/apiClient";



function CardReadMoreLinkTo(to) {
    let navigate = useNavigate()
    function handleClick(to){
        navigate("/farmer/production/"+to.to)
    }
    return(
        <CardReadMore iconName='it-arrow-right' text='See more'  onClick={() => handleClick(to)}/>
    )
}



export class FarmerProduction extends React.Component{
    constructor(props) {
        super(props);
        this.state=this.getDefaultState()
        this.client = new ApiClient()
    }


    getDefaultState = () => {
        return {
            selectedOption:null,
            products:null,
            productsForSelect:{
                value:"",
                label:""
            },
            productionsForGraph:[],
            productions:null,
            soildata:null,
            temperature:null,
            humidity:null,
            waterConsumption:null
        }
    }
    componentDidMount() {
        this.getProduct()
        this.getSoilData()
        this.getTemperature()
        this.getHumidity()
        this.getWaterConsumption()
    }
    getProduct = () => {
        this.client.GetProductsByFarm()
            .then((response) => {
                if(response.data.code==200){
                    this.setState({
                        products: response.data.results[0],
                    }, function() {
                        const productsForSelect=this.state.products.map((product)=>{
                            return { value:product.productId,label:product.product}})
                        this.setState({
                            productsForSelect:productsForSelect
                        })
                    })
                }else if(response.data.code==404){
                    this.setState({
                        products: null,
                    })
                }else if(response.data.code==400){
                    this.setState({
                        products: null,
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
    getSoilData = () => {
        this.client.GetSoilDataOfFarm()
            .then((response) => {
                if(response.data.code==200){
                    this.setState({
                        soildata: response.data.results[0][0],
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
    getTemperature = () => {
        this.client.GetTemperature()
            .then((response) => {
                this.setState({temperature:response.data})
            })
            .catch((error) => {
                notify(
                    'Error',
                    'Something went wrong!',
                    {state:"error"}
                )            })
        ;
    };
    getHumidity = () => {
        this.client.GetHumidity()
            .then((response) => {
                this.setState({humidity:response.data})
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
    getWaterConsumption = () => {
        this.client.GetWaterConsumption()
            .then((response) => {
                this.setState({waterConsumption:response.data})
            })
            .catch((error) => {
                notify(
                    'Error',
                    'Something went wrong!',
                    {state:"error"}
                )            })
        ;
    };
    getProductionsByProduct = () => {
        this.client.GetProductionsByFarmAndProduct(this.state.selectedOption.value)
            .then((response) => {
                if(response.data.code==200){
                    this.setState({
                        productions: response.data.results[0],
                    }, function() {
                        const productionsForGraph=this.state.productions.slice(0).reverse().map((production)=>{
                            return { date:production.date,amount:production.qta}})
                        this.setState({
                            productionsForGraph:productionsForGraph
                        })
                    })
                }else if(response.data.code==404){
                    this.setState({
                        productions: null,
                    })
                }else if(response.data.code==400){
                    this.setState({
                        productions: null,
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

    handleChange = (selectedOption) =>{
        const self=this
        this.setState({selectedOption:selectedOption}, function (){
            self.getProductionsByProduct()
        });
    }


    render() {
        return(
            <div>
                <Container style={{paddingTop:20,paddingBottom:20}}>
                    <Row>
                        <Col
                            lg='12'
                            xl='6'
                        >
                            <Card  className="no-after rounded shadow"
                            >
                                <CardBody className="pb-5">
                                    <CardTitle className="h5">
                                        Production
                                    </CardTitle>
                                    <div className='bootstrap-select-wrapper' style={{marginTop:30}} >

                                        <Select
                                            id='selectExampleReset'
                                            value={this.state.selectedOption}
                                            onChange={this.handleChange}
                                            options={this.state.productsForSelect}
                                            placeholder='Choose a product'
                                            aria-label='Choose a product'
                                        />
                                    </div>
                                    <div style={{marginTop:30}}>
                                        <LineChart width={500} height={300} data={this.state.productionsForGraph}>
                                            <Line type="monotone" dataKey="amount" stroke="#8884d8" />
                                            <CartesianGrid stroke="#ccc" strokeDasharray="5 5" />
                                            <XAxis dataKey="date" />
                                            <YAxis />
                                            <Tooltip />
                                        </LineChart>
                                    </div>
                                    <CardReadMoreLinkTo to={"products"}/>
                                </CardBody>
                            </Card>
                        </Col>
                        <Col
                            lg='12'
                            xl='3'
                        >
                            <Card
                                className="no-after rounded shadow"


                            >
                                <CardBody className="pb-5">

                                    <CardTitle className="h5">
                                        Soil Data
                                    </CardTitle>
                                    <Table>
                                        <thead>
                                        <tr>
                                            <th scope='col'>Type</th>
                                            <th scope='col' style={{textAlign:"right"}}>Value</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <tr>
                                            <td>Ph</td>
                                            <td style={{textAlign:"right"}}>{this.state.soildata?this.state.soildata.ph:""}</td>
                                        </tr>
                                        <tr>
                                            <td>Limestone*</td>
                                            <td style={{textAlign:"right"}}>{this.state.soildata?this.state.soildata.limestone:""}</td>
                                        </tr>
                                        <tr>
                                            <td>Nitrogen*</td>
                                            <td style={{textAlign:"right"}}>{this.state.soildata?this.state.soildata.nitrogen:""}</td>
                                        </tr>
                                        <tr>
                                            <td>Phosphorus*</td>
                                            <td style={{textAlign:"right"}}>{this.state.soildata?this.state.soildata.phosphorus:""}</td>
                                        </tr>
                                        <tr>
                                            <td>Organic carbon*</td>
                                            <td style={{textAlign:"right"}}>{this.state.soildata?this.state.soildata.organic_carbon:""}</td>
                                        </tr>
                                        </tbody>
                                    </Table>
                                    <span style={{fontSize:14}}>*the unit of measurement is [mg/Kg]</span>

                                    <CardReadMoreLinkTo to={"soildata"}/>
                                </CardBody>

                            </Card>
                        </Col>
                        <Col
                            lg='12'
                            xl='3'

                        >
                            <Card
                                className="no-after rounded shadow"
                            >
                                <CardBody className="pb-5">
                                    <CardTitle className="h5">
                                        Instant data
                                    </CardTitle>
                                    <Table>
                                        <thead>
                                        <tr>
                                            <th scope='col'>Type</th>
                                            <th scope='col' style={{textAlign:"right"}}>Value</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <tr>
                                            <td>Daily water consumption</td>
                                            <td style={{textAlign:"right"}}>{this.state.waterConsumption?this.state.waterConsumption:""}m<sup>3</sup></td>
                                        </tr>
                                        <tr>
                                            <td>Soil moisture</td>
                                            <td style={{textAlign:"right"}}>{this.state.humidity?this.state.humidity:""}%</td>
                                        </tr>
                                        <tr>
                                            <td>Temperature</td>
                                            <td style={{textAlign:"right"}}>{this.state.temperature?this.state.temperature:""}°C</td>
                                        </tr>
                                        </tbody>
                                    </Table>
                                </CardBody>

                            </Card>
                        </Col>




                    </Row>

                </Container>

            </div>

        )
    }



}