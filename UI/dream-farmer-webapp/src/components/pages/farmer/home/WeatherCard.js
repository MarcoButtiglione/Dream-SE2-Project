import React from 'react'
import {Card, CardBody, CardCategory, CardReadMore, CardText, CardTitle, Col, Icon, Row} from "design-react-kit";


class WeatherCard extends React.Component{

    constructor(props){
        super(props)
    }

    spitOutCelcius = (kelvin) => {
        const celcius = Math.round(kelvin - 273.15);
        return celcius;
    }

    getWeatherIcon = (iconParameter) => {
        const icon = `https://openweathermap.org/img/wn/${iconParameter}@2x.png`
        return <img src = {icon} alt = "" />
    }

    render() {
        return(
            <Card
                spacing className="no-after rounded shadow"
            >
                    <CardBody className="pb-5 text-center h5" style={{fontSize:20}}>
                        <CardTitle className="h5" style={{fontSize:30}}>
                            {this.props.weatherResult.name}
                        </CardTitle>
                        <Row style={{marginBottom:30}}>
                            <Col style={{
                                display: "flex",
                                justifyContent: "center",
                                alignItems: "center"
                            }}>
                                <span style={{fontSize:50}}>{this.spitOutCelcius(this.props.weatherResult.main.temp)}&deg;C</span>
                            </Col>
                            <Col>
                                <div>
                                    <p >{this.props.weatherResult.weather[0].description}</p>
                                    <p>Max: {this.spitOutCelcius(this.props.weatherResult.main.temp_max) }&deg;C</p>
                                    <p>Min: {this.spitOutCelcius(this.props.weatherResult.main.temp_min)}&deg;C</p>
                                </div>
                            </Col>
                        </Row>
                        <Row style={{marginBottom:60}}>
                            <div className=" card shadow mx-auto"
                                 style={{
                                     borderRadius: "100%",
                                     width: 100,
                                     height: 100,
                                     background: "#202020",
                                 }}>
                                {this.getWeatherIcon(this.props.weatherResult.weather[0].icon)}
                            </div>
                        </Row>
                        <Row>
                            <Col>

                                    <p style={{fontSize:30}}>{this.spitOutCelcius(this.props.weatherResult.main.feels_like)}&deg;C</p>
                                    <span >Feels Like</span>
                            </Col>
                            <Col>

                                    <p style={{fontSize:30}}>{this.props.weatherResult.main.humidity}%</p>
                                    <span >Humidity</span>
                            </Col>

                        </Row>
                    </CardBody>
            </Card>
        )
    }
}

export default WeatherCard;