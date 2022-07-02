import React, {useState} from 'react';
import {Button, Col, Container, Input, notify, Row} from "design-react-kit";
import {Link} from "react-router-dom";
import ApiClient from "../../../commons/apiClient";


export default function SignInPage(props){
    /**
     * Hooks
     * */
    const [email,setEmail]=useState("")
    const [password,setPassword]=useState("")
    const client=new ApiClient()

    /**
     * login function
     * It is use call to login the farmer when the "Sign in" button is pressed.
     * If the backend response, the token is set a notification is shown
     * */
    const login=()=>{
        let data = {
            email:email,
            password:password
        }
        client.LoginFarmer(data)
            .then((response) => {
                if(response.status==200){
                    props.setTokens(response.data.access_token,response.data.refresh_token)
                    notify(
                        'Success',
                        'The User was successfully logged',
                        {state:"success"}
                    )
                }
            })
            .catch((error) => {
                notify(
                    'Warning',
                    'Incorrect email or password',
                    {state:"warning"}
                )
            })
        ;

    }

    /**
      * Render part
     * */
    return(
        <div style={{minHeight:700}}>
                <Container style={{paddingTop:100}}>
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

                            <h2 className=' text-center'>Sign In</h2>
                            <div className='form-row'style={{marginTop:20}}>
                                <Input
                                    type='email'
                                    label='E-Mail'
                                    id='inputEmail'
                                    value={email}
                                    onChange={(event)=>setEmail(event.target.value)}
                                    wrapperClass='col col-md-12'
                                />
                            </div>
                            <div className='form-row'>
                                <Input
                                    type='password'
                                    id='inputPassword'
                                    label='Password'
                                    value={password}
                                    onChange={(event)=>setPassword(event.target.value)}
                                    wrapperClass='col col-md-12'
                                />
                            </div>
                            {/*<div className='form-row'>
                                <FormGroup check>
                                    <Input id='checkbox1' type='checkbox' />
                                    <Label for='checkbox1' check>
                                        Remember me
                                    </Label>
                                </FormGroup>
                            </div>*/}
                            <div className='form-row' style={{marginTop:20}}>
                                <Button
                                    className="btn btn-primary btn-block"
                                    onClick={()=>login()}
                                >
                                    Sign In
                                </Button>
                            </div>
                            <div className='text-right' style={{marginTop:20}}>
                                <Link className='font-weight-bold ' to={"/signup"}>
                                    Don't have an account? Sign Up
                                </Link>
                            </div>

                        </Col>

                    </Row>


                </Container>

            </div>

    )
}
