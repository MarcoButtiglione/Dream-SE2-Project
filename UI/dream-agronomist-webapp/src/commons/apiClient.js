import axios from "axios";
import appGlobal from "./appGlobal";
import qs from 'qs';

/**
 * Functional class used by all React components with all the farmer REST API calls
 * Import :
 * axios, a Javascript library that allows you to connect with the backend API and manage requests made via the HTTP protocol.
 * qs, a querystring parsing and stringifying library with some added security.
 * appGlobal, a set of function used to acquire api_token and refresh_token
 * */
export default class ApiClient {
    /**
     * Constructor of the class, declaring the base URL of all REST API
     * */
    constructor() {
        this._baseUrl = "http://localhost:8080/api/"
        this._urlAgronomist = this._baseUrl+"agronomist/"
    }

    /**
     * GETTER METHOD of the HEADER of the REST API calls
     * */
    getHeaders(){
        return {
            'Authorization': 'Bearer ' + appGlobal.apiToken(),
        }
    }
    getHeadersRefresh(){
        return {
            'Authorization': 'Bearer ' + appGlobal.refreshToken(),
        }
    }


    /**
     * Login call
     * @param agronomist class login
     * first it is extrapolated all the data from the agronomist class creating a new the data class correctly formatted.
     * then it is used the async axios function to send the request to the backend.
     * @return response
     * @exception general error
     * */
    LoginAgronomist=function (agronomist){
        let self=this
        return new Promise(function (resolve, reject){
            let data = {
                email:agronomist.email,
                password:agronomist.password
            }
            const options = {
                method: 'POST',
                headers: { 'content-type': 'application/x-www-form-urlencoded' },
                data: qs.stringify(data),
                url:self._baseUrl+'login',
            };
            axios(options)
                .then((response)=>{
                    resolve(response)
                })
                .catch((error)=>{
                    reject(error)
                })
        })
    }


    /**
     * AGRONOMIST CALLS
     * */
    /**
     * GET Agronomist call
     * it is used the async axios function to send the request to the backend.
     * @return response
     * @exception general error
     * */
    GetAgronomist= function () {
        let self = this
        return new Promise(function (resolve, reject) {
            let headers = self.getHeaders()
            axios.get(self._urlAgronomist + 'GetAgronomist/',{headers:headers})
                .then((response) => {
                    resolve(response)
                })
                .catch((error) => {
                    reject(error)
                })
        })
    }


    /**
     * KNOWLEDGE CALLS
     * */
    /**
     * GET All Knowledge call
     * it is used the async axios function to send the request to the backend.
     * @return response
     * @exception general error
     * */
    GetAllKnowledgeByAgronomist= function () {
        let self = this
        return new Promise(function (resolve, reject) {
            let headers = self.getHeaders()
            axios.get(self._urlAgronomist + 'getAllKnowledgeByAgronomist/',{headers:headers})
                .then((response) => {
                    resolve(response)
                })
                .catch((error) => {
                    reject(error)
                })
        })
    }
    /**
     * GET All Knowledge By Word call
     * @param search word to search
     * it is used the async axios function to send the request to the backend.
     * @return response
     * @exception general error
     * */
    GetAllKnowledgeByWordA=function (search) {
        let self = this
        return new Promise(function (resolve, reject) {
            let headers = self.getHeaders()
            axios.get(self._urlAgronomist + 'getAllKnowledgeByWordA/?word='+search,{headers:headers})
                .then((response) => {
                    resolve(response)
                })
                .catch((error) => {
                    reject(error)
                })
        })
    }
    /**
     * POST Knowledge call
     * @param knowledge class to add
     * first it is extrapolated all the data from the knowledge class creating a new the data class correctly formatted.
     * then it is used the async axios function to send the request to the backend.
     * @return response
     * @exception general error
     * */
    PostKnowledge=function (knowledge){
        let self=this
        return new Promise(function (resolve, reject){
            let headers = self.getHeaders()
            let data = {
                title:knowledge.title,
                description:knowledge.description,
                article:knowledge.article,
                category:knowledge.category
            }
            axios.post(self._urlAgronomist+'PostKnowledge/', data,{headers:headers})
                .then((response)=>{
                    resolve(response)
                })
                .catch((error)=>{
                    reject(error)
                })
        })
    }
    /**
     * PUT Knoledge call
     * @param knowledge class to edit
     * first it is extrapolated all the data from the knowledge class creating a new the data class correctly formatted.
     * then it is used the async axios function to send the request to the backend.
     * @return response
     * @exception general error
     * */
    PutKnowledge=function (knowledge){
        let self=this
        return new Promise(function (resolve, reject){
            let headers = self.getHeaders()

            let data = {
                title:knowledge.title,
                description:knowledge.description,
                article:knowledge.article,
                category:knowledge.category
            }

            axios.post(self._urlAgronomist+'PutKnowledge/'+knowledge.id, data,{headers:headers})
                .then((response)=>{
                    resolve(response)
                })
                .catch((error)=>{
                    reject(error)
                })
        })
    }
    /**
     * DELETE Knowledge call
     * @param id of knowledge to delete
     * it is used the async axios function to send the request to the backend.
     * @return response
     * @exception general error
     * */
    DeleteKnowledge=function (id){
        let self=this
        return new Promise(function (resolve, reject){
            let headers = self.getHeaders()
            axios.get(self._urlAgronomist+'DeleteKnowledge/'+id,{headers:headers})
                .then((response)=>{
                    resolve(response)
                })
                .catch((error)=>{
                    reject(error)
                })
        })
    }


    /**
     * REQUEST CALLS
     * */
    /**
     * GET Request Pendant call
     * it is used the async axios function to send the request to the backend.
     * @return response
     * @exception general error
     * */
    GetRequestByAgronomistPendant= function () {
        let self = this
        return new Promise(function (resolve, reject) {
            let headers = self.getHeaders()
            axios.get(self._urlAgronomist + 'GetRequestByAgronomistPendant/',{headers:headers})
                .then((response) => {
                    resolve(response)
                })
                .catch((error) => {
                    reject(error)
                })
        })
    }
    /**
     * GET Request No Feed call
     * it is used the async axios function to send the request to the backend.
     * @return response
     * @exception general error
     * */
    GetRequestByAgronomistNoFeed= function () {
        let self = this
        return new Promise(function (resolve, reject) {
            let headers = self.getHeaders()
            axios.get(self._urlAgronomist + 'GetRequestByAgronomistNoFeed/',{headers:headers})
                .then((response) => {
                    resolve(response)
                })
                .catch((error) => {
                    reject(error)
                })
        })
    }
    /**
     * GET Request No Closed call
     * it is used the async axios function to send the request to the backend.
     * @return response
     * @exception general error
     * */
    GetRequestByAgronomistClosed= function () {
        let self = this
        return new Promise(function (resolve, reject) {
            let headers = self.getHeaders()
            axios.get(self._urlAgronomist + 'GetRequestByAgronomistClosed/',{headers:headers})
                .then((response) => {
                    resolve(response)
                })
                .catch((error) => {
                    reject(error)
                })
        })
    }
    /**
     * PUT Response call
     * @param response request to add
     * @param requestId of the request where the response will be added
     * first it is extrapolated all the data from the request class creating a new the data class correctly formatted.
     * then it is used the async axios function to send the request to the backend.
     * @return response
     * @exception general error
     * */
    PutResponse=function (response,requestId){
        let self=this
        return new Promise(function (resolve, reject){
            let headers = self.getHeaders()
            let data = {
                response:response
            }

            axios.post(self._urlAgronomist+'PutResponse/'+requestId, data,{headers:headers})
                .then((response)=>{
                    resolve(response)
                })
                .catch((error)=>{
                    reject(error)
                })
        })
    }


    /**
     * MEETING CALLS
     * */
    /**
     * GET Daily Meeting call
     * it is used the async axios function to send the request to the backend.
     * @return response
     * @exception general error
     * */
    GetTodaysMeetingsA=function () {
        let self = this
        return new Promise(function (resolve, reject) {
            let headers = self.getHeaders()
            axios.get(self._urlAgronomist + 'GetTodaysMeetingsA/',{headers:headers})
                .then((response) => {
                    resolve(response)
                })
                .catch((error) => {
                    reject(error)
                })
        })
    }
    /**
     * GET All Meeting call
     * it is used the async axios function to send the request to the backend.
     * @return response
     * @exception general error
     * */
    GetAllMeetingsA=function () {
        let self = this
        return new Promise(function (resolve, reject) {
            let headers = self.getHeaders()
            axios.get(self._urlAgronomist + 'GetAllMeetingsA/',{headers:headers})
                .then((response) => {
                    resolve(response)
                })
                .catch((error) => {
                    reject(error)
                })
        })
    }
    /**
     * GET All Meeting Concluded call
     * it is used the async axios function to send the request to the backend.
     * @return response
     * @exception general error
     * */
    GetAllMeetingsConclused=function () {
        let self = this
        return new Promise(function (resolve, reject) {
            let headers = self.getHeaders()
            axios.get(self._urlAgronomist + 'GetAllMeetingsConclused/',{headers:headers})
                .then((response) => {
                    resolve(response)
                })
                .catch((error) => {
                    reject(error)
                })
        })
    }
    /**
     * POST Meeting call
     * @param farmerId of the farmer where the meeting will be created
     * @param meeting class to add
     * first it is extrapolated all the data from the meeting class creating a new the data class correctly formatted.
     * then it is used the async axios function to send the request to the backend.
     * @return response
     * @exception general error
     * */
    PostMeeting=function (meeting,farmerId){
        let self=this
        return new Promise(function (resolve, reject){
            let headers = self.getHeaders()
            let data = {
                date:meeting.date,
                startTime:meeting.startTime,
                endTime:meeting.endTime
            }

            axios.post(self._urlAgronomist+'PostMeeting/'+farmerId, data,{headers:headers})
                .then((response)=>{
                    resolve(response)
                })
                .catch((error)=>{
                    reject(error)
                })
        })
    }
    /**
     * PUT Meeting call
     * @param meetingId of the meeting to edit
     * @param meeting class to add
     * first it is extrapolated all the data from the meeting class creating a new the data class correctly formatted.
     * then it is used the async axios function to send the request to the backend.
     * @return response
     * @exception general error
     * */
    PutMeeting=function (meeting,meetingId){
        let self=this
        return new Promise(function (resolve, reject){
            let headers = self.getHeaders()
            let data = {
                date:meeting.date,
                startTime:meeting.startTime,
                endTime:meeting.endTime
            }

            axios.post(self._urlAgronomist+'PutMeeting/'+meetingId, data,{headers:headers})
                .then((response)=>{
                    resolve(response)
                })
                .catch((error)=>{
                    reject(error)
                })
        })
    }
    /**
     * DELETE Meeting call
     * @param id of meeting to delete
     * it is used the async axios function to send the request to the backend.
     * @return response
     * @exception general error
     * */
    DeleteMeeting=function (id){
        let self=this
        return new Promise(function (resolve, reject){
            let headers = self.getHeaders()
            axios.get(self._urlAgronomist+'DeleteMeeting/'+id,{headers:headers})
                .then((response)=>{
                    resolve(response)
                })
                .catch((error)=>{
                    reject(error)
                })
        })
    }
    /**
     * Evaluate(Close) the Meeting call
     * @param meetingId of the meeting to evaluate
     * @param meeting class to evaluate
     * first it is extrapolated all the data from the meeting class creating a new the data class correctly formatted.
     * then it is used the async axios function to send the request to the backend.
     * @return response
     * @exception general error
     * */
    CloseMeeting=function (meeting,meetingId){
        let self=this
        return new Promise(function (resolve, reject){
            let headers = self.getHeaders()
            let data = {
                evaluation:meeting.evaluation,
                note:meeting.note
            }

            axios.post(self._urlAgronomist+'CloseMeeting/'+meetingId, data,{headers:headers})
                .then((response)=>{
                    resolve(response)
                })
                .catch((error)=>{
                    reject(error)
                })
        })
    }


    /**
     * FARMER CALLS
     * */
    /**
     * GET All Farmer of an Agronomist call
     * it is used the async axios function to send the request to the backend.
     * @return response
     * @exception general error
     * */
    GetFarmerByAgronomist=function () {
        let self = this
        return new Promise(function (resolve, reject) {
            let headers = self.getHeaders()
            axios.get(self._urlAgronomist + 'GetFarmerByAgronomist/',{headers:headers})
                .then((response) => {
                    resolve(response)
                })
                .catch((error) => {
                    reject(error)
                })
        })
    }
    /**
     * GET Farmer By Aadhaar call
     * @param search Aadhaar to search
     * it is used the async axios function to send the request to the backend.
     * @return response
     * @exception general error
     * */
    GetFarmerByAadhaar=function (search) {
        let self = this
        return new Promise(function (resolve, reject) {
            let headers = self.getHeaders()
            axios.get(self._urlAgronomist + 'GetFarmerByAadhaar/'+search,{headers:headers})
                .then((response) => {
                    resolve(response)
                })
                .catch((error) => {
                    reject(error)
                })
        })
    }


    /**
     * PRODUCT CALLS
     * */
    /**
     * GET All the Products of a Farmer call
     * @param farmerId of the farmer to search
     * it is used the async axios function to send the request to the backend.
     * @return response
     * @exception general error
     * */
    GetProductsByFarmer=function (farmerId) {
        let self = this
        return new Promise(function (resolve, reject) {
            let headers = self.getHeaders()
            axios.get(self._urlAgronomist + 'GetProductsByFarm/'+farmerId,{headers:headers})
                .then((response) => {
                    resolve(response)
                })
                .catch((error) => {
                    reject(error)
                })
        })
    }


    /**
     * PRODUCT CALLS
     * */
    /**
     * GET Production by Farm and Product call
     * @param farmerId of the farmer to search
     * @param productId of the product to search
     * it is used the async axios function to send the request to the backend.
     * @return response
     * @exception general error
     * */
    GetProductionsByFarmAndProduct=function (farmerId,productId) {
        let self = this
        return new Promise(function (resolve, reject) {
            let headers = self.getHeaders()
            axios.get(self._urlAgronomist + 'GetProductionsByFarmAndProduct/'+farmerId+"?productId="+productId,{headers:headers})
                .then((response) => {
                    resolve(response)
                })
                .catch((error) => {
                    reject(error)
                })
        })
    }


    /**
     * SOIL DATA CALLS
     * */
    /**
     * POST Soil Data call
     * @param farmerId of the farmer where the soil data will be added
     * @param soilData class to add
     * first it is extrapolated all the data from the soil data class creating a new the data class correctly formatted.
     * then it is used the async axios function to send the request to the backend.
     * @return response
     * @exception general error
     * */
    PostSoilData=function (soilData,farmerId){
        let self=this
        return new Promise(function (resolve, reject){
            let headers = self.getHeaders()
            let data = {
                ph: soilData.ph,
                nitrogen:soilData.nitrogen,
                phosphorus:soilData.phosphorus,
                organic_carbon:soilData.organic_carbon,
                limestone:soilData.limestone
            }

            axios.post(self._urlAgronomist+'PostSoilData/'+farmerId, data,{headers:headers})
                .then((response)=>{
                    resolve(response)
                })
                .catch((error)=>{
                    reject(error)
                })
        })
    }
    /**
     * PUT Soil Data call
     * @param soilData class to edit
     * @param farmerId of the farmer where the soil data will be edited
     * @param soilDataId of the soil data that will be edited
     * first it is extrapolated all the data from the soil data class creating a new the data class correctly formatted.
     * then it is used the async axios function to send the request to the backend.
     * @return response
     * @exception general error
     * */
    PutSoilData=function (soilData,farmerId,soilDataId){
        let self=this
        return new Promise(function (resolve, reject){
            let headers = self.getHeaders()
            let data = {
                ph: soilData.ph,
                nitrogen:soilData.nitrogen,
                phosphorus:soilData.phosphorus,
                organic_carbon:soilData.organic_carbon,
                limestone:soilData.limestone
            }

            axios.post(self._urlAgronomist+'PutSoilData/'+farmerId+"?soilDataId="+soilDataId, data,{headers:headers})
                .then((response)=>{
                    resolve(response)
                })
                .catch((error)=>{
                    reject(error)
                })
        })
    }
    /**
     * DELETE Soil Data call
     * @param id of soil data to delete
     * it is used the async axios function to send the request to the backend.
     * @return response
     * @exception general error
     * */
    DeleteSoilData=function (id){
        let self=this
        return new Promise(function (resolve, reject){
            let headers = self.getHeaders()
            axios.get(self._urlAgronomist+'DeleteSoilData/'+id,{headers:headers})
                .then((response)=>{
                    resolve(response)
                })
                .catch((error)=>{
                    reject(error)
                })
        })
    }


    /**
     * NOTIFICATION CALLS
     * */
    /**
     * GET All Notification call
     * it is used the async axios function to send the request to the backend.
     * @return response
     * @exception general error
     * */
    GetAllNotificationA=function () {
        let self = this
        return new Promise(function (resolve, reject) {
            let headers = self.getHeaders()
            axios.get(self._urlAgronomist + 'GetAllNotificationA/',{headers:headers})
                .then((response) => {
                    resolve(response)
                })
                .catch((error) => {
                    reject(error)
                })
        })
    }
    /**
     * DELETE Notification call
     * @param id of notification to delete
     * it is used the async axios function to send the request to the backend.
     * @return response
     * @exception general error
     * */
    DeleteNotificationA=function (id){
        let self=this
        return new Promise(function (resolve, reject){
            let headers = self.getHeaders()
            axios.get(self._urlAgronomist+'DeleteNotificationA/'+id,{headers:headers})
                .then((response)=>{
                    resolve(response)
                })
                .catch((error)=>{
                    reject(error)
                })
        })
    }

}

