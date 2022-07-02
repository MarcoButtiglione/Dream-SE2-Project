import {Button, Col, Container, Input, notify, Row, Select} from "design-react-kit";
import {useNavigate} from "react-router-dom";
import React, {useEffect, useState} from "react";
import ApiClient from "../../../commons/apiClient";

function FarmRegistration(props){
    const navigate = useNavigate();

    const [locations,setLocations]=new useState(null)
    const [locationsForSelect,setLocationsForSelect]=new useState(null)
    const [option,setOption]=new useState(null)
    const [address,setAddress]=new useState("")
    const [sqrKm,setSqrKm]=new useState("")

    const client=new ApiClient()
    useEffect(()=>{
        getLocation()
    }, [])

    const getLocation=()=>{
        client.GetAllLocation()
            .then((response) => {
                if(response.data.code==200){
                    setLocations(response.data.results[0])
                    const locationsForSelect=response.data.results[0].map((location)=>{
                        return { value:location.locationId,label:location.location}})
                    setLocationsForSelect(locationsForSelect)
                }else if(response.data.code==404){
                    notify(
                        'Warning',
                        response.data.message,
                        {state:"warning"}
                    )
                    setLocations(null)
                    setLocationsForSelect(null)
                }else if(response.data.code==400){
                    notify(
                        'Warning',
                        response.data.message,
                        {state:"warning"}
                    )
                    setLocations(null)
                    setLocationsForSelect(null)
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
    }
    const farmReg=()=>{
        let data = {
            squareKm:sqrKm,
            address:address
        }
        client.PostFarm(data,option.value)
            .then((response) => {
                if(response.data.code==200){
                    props.getFarmer()
                    notify(
                        'Success',
                        'The Farm was successfully registered',
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
        <div style={{minHeight:700}}>
            <Container style={{paddingTop:70}}>
                <Row>
                    <Col
                        md={{
                            offset: 2,
                            size: 8
                        }}
                        sm={{
                            offset: 1,
                            size: 10
                        }}
                    >

                        <h2 className=' text-center'>Sign Up your farm</h2>
                        <div className='bootstrap-select-wrapper' style={{marginTop:20}}>

                            <Select
                                id='selectExampleReset'
                                value={option}
                                onChange={setOption}
                                options={locationsForSelect}
                                placeholder='Choose an area'
                                aria-label='Choose an area'
                                wrapperClass='col col-md-12'
                            />
                        </div>

                        <div className='form-row'style={{marginTop:50}}>
                            <Input
                                type='text'
                                label='Address'
                                id='inputAddress'
                                value={address}
                                onChange={(event)=>setAddress(event.target.value)}
                                wrapperClass='col col-md-12'
                            />
                        </div>
                        <div className='form-row'>
                            <Input
                                type='number'
                                label='Square kilometers'
                                id='inputTitle'
                                value={sqrKm}
                                onChange={(event)=>setSqrKm(event.target.value)}
                                wrapperClass='col col-md-12'
                            />
                        </div>



                        <div className='form-row' style={{marginTop:20}}>
                            <Button
                                className="btn btn-primary btn-block"
                                onClick={()=>farmReg()}
                            >
                                Sign Up
                            </Button>
                        </div>
                    </Col>

                </Row>


            </Container>

        </div>
    )
}
export default FarmRegistration