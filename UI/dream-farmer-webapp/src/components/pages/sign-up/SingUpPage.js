import {Button, Col, Container, Input, notify, Row} from "design-react-kit";
import {Link} from "react-router-dom";
import React from 'react';
import ApiClient from "../../../commons/apiClient";
import {withRouter} from "../../../commons/withRouter";

/**
 * Sign Up compenent
 * */
class SignUpPage extends React.Component{
    /**
     * Constructor
     * */
    constructor(props) {
        super(props);
        this.state=this.getDefaultState()
        this.client = new ApiClient()
        this.toSignIn=this.toSignIn.bind(this)
    }
    getDefaultState = () => {
        return {
            firstName:"",
            lastName:"",
            aadhaar:"",
            email:"",
            password:"",
            telephone:"",
        }
    }
    /**
     * Redirect to Sign In page
     * */
    toSignIn() {
        this.props.navigate("/signin")
    }
    /**
     * Sign Up farmer function.
     * It is used to register a farmer in the system
     * */
    signUpFarmer=()=>{
            const farmer ={
                firstName:this.state.firstName ,
                lastName: this.state.lastName,
                aadhaar: this.state.aadhaar,
                email: this.state.email,
                password: this.state.password,
                telephone: this.state.telephone
            }
            this.client.SignUpFarmer(farmer)
                .then((response) => {
                    if(response.data.code==200){
                        notify(
                            'Success',
                            'The User was successfully registered',
                            {state:"success"}
                        )
                        this.toSignIn()
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
    /**
     * Function that is used to change the states when the user digits in the form
     * */
    handleChange = (name) => event => {
        this.setState({
            [name]: event.target.value,
        });
    };
    /**
     * Render part
     * */
    render() {
        return(
            <div style={{minHeight:900}}>
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

                            <h2 className=' text-center'>Sign Up</h2>
                            <div className='form-row' style={{marginTop:20}}>
                                <Input
                                    type='text'
                                    label='First Name'
                                    value={this.state.firstName}
                                    onChange={this.handleChange("firstName")}
                                    id='formFirstName'
                                    wrapperClass='col col-md-6'
                                />
                                <Input
                                    type='text'
                                    label='Last Name'
                                    value={this.state.lastName}
                                    onChange={this.handleChange("lastName")}
                                    id='formLastName'
                                    wrapperClass='col col-md-6'
                                />
                            </div>
                            <div className='form-row'>
                                <Input
                                    type='text'
                                    label='Aadhaar'
                                    value={this.state.aadhaar}
                                    onChange={this.handleChange("aadhaar")}
                                    id='formAadhaar'
                                    wrapperClass='col col-md-12'
                                />
                            </div>
                            <div className='form-row'>
                                <Input
                                    type='tel'
                                    label='Telephone number'
                                    id='formTelphone'
                                    wrapperClass='col col-md-12'
                                    value={this.state.telephone}
                                    onChange={this.handleChange("telephone")}
                                />
                            </div>
                            <div className='form-row'>
                                <Input
                                    type='email'
                                    value={this.state.email}
                                    onChange={this.handleChange("email")}

                                    label='E-Mail'
                                    id='inputEmail'
                                    wrapperClass='col col-md-12'
                                />
                            </div>

                            <div className='form-row'>
                                <Input
                                    type='password'
                                    id='inputPassword'
                                    value={this.state.password}
                                    onChange={this.handleChange("password")}
                                    label='Password'
                                    wrapperClass='col col-md-12'
                                />
                            </div>
                            <div className='form-row' style={{marginTop:20}}>
                                <Button
                                    className="btn btn-primary btn-block"
                                    onClick={()=>this.signUpFarmer()}
                                >
                                    Sign Up
                                </Button>
                            </div>
                            <div className='text-right' style={{marginTop:20}}>
                                <Link className='font-weight-bold ' to={"/signin"}>
                                    Already have an account? Sign In
                                </Link>
                            </div>
                        </Col>
                    </Row>
                </Container>
            </div>
        )
    }
}
export default withRouter(SignUpPage);