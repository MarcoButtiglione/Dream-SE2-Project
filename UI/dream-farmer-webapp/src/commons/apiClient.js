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
        this._urlFarmer = this._baseUrl+"farmer/"
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
     * SignUp call
     * @param farmer class to sign up
     * first it is extrapolated all the data from the farmer class creating a new the data class correctly formatted.
     * then it is used the async axios function to send the request to the backend.
     * @return response
     * @exception general error
     * */
    SignUpFarmer=function (farmer){
        let self=this
        return new Promise(function (resolve, reject){
            let data = {
                firstName:farmer.firstName ,
                lastName: farmer.lastName,
                aadhaar: farmer.aadhaar,
                email: farmer.email,
                password: farmer.password,
                telephone: farmer.telephone
            }
            axios.post(self._baseUrl+'signUpFarmer', data)
                .then((response)=>{
                    resolve(response)
                })
                .catch((error)=>{
                    reject(error)
                })
        })
    }
    /**
     * Login call
     * @param farmer class login
     * first it is extrapolated all the data from the farmer class creating a new the data class correctly formatted.
     * then it is used the async axios function to send the request to the backend.
     * @return response
     * @exception general error
     * */
    LoginFarmer=function (farmer){
        let self=this
        return new Promise(function (resolve, reject){
            let data = {
                email:farmer.email,
                password:farmer.password
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
     * FARMER CALLS
     * */
    /**
     * GET farmer call
     * it is used the async axios function to send the request to the backend.
     * @return response
     * @exception general error
     * */
    GetFarmerF= function () {
        let self = this
        return new Promise(function (resolve, reject) {
            let headers = self.getHeaders()
            axios.get(self._urlFarmer + 'GetFarmerF/',{headers:headers})
                .then((response) => {
                    resolve(response)
                })
                .catch((error) => {
                    reject(error)
                })
        })
    }


    /**
     * LOCATION CALLS
     * */
    /**
     * GET location call
     * it is used the async axios function to send the request to the backend.
     * @return response
     * @exception general error
     * */
    GetAllLocation = function () {
        let self = this
        return new Promise(function (resolve, reject) {
            let headers = self.getHeaders()
            axios.get(self._urlFarmer + 'GetAllLocation/',{headers:headers})
                .then((response) => {
                    resolve(response)
                })
                .catch((error) => {
                    reject(error)
                })
        })
    }


    /**
     * FARM CALLS
     * */
    /**
     * POST Farm call
     * @param farm class to register
     * @param locationId where the farm is located
     * first it is extrapolated all the data from the farm class creating a new the data class correctly formatted.
     * then it is used the async axios function to send the request to the backend.
     * @return response
     * @exception general error
     * */
    PostFarm=function (farm,locationId){
        let self=this
        return new Promise(function (resolve, reject){
            let data = {
                squareKm:farm.squareKm,
                address:farm.address
            }
            let headers = self.getHeaders()
            axios.post(self._urlFarmer+'PostFarm/'+locationId, data,{headers:headers})
                .then((response)=>{
                    resolve(response)
                })
                .catch((error)=>{
                    reject(error)
                })
        })
    }


    /**
     * TOPIC CALLS
     * */
    /**
     * GET All Topics call
     * it is used the async axios function to send the request to the backend.
     * @return response
     * @exception general error
     * */
    GetAllTopic = function () {
        let self = this
        return new Promise(function (resolve, reject) {
            let headers = self.getHeaders()
            axios.get(self._urlFarmer + 'getAllTopic/',{headers:headers})
                .then((response) => {
                    resolve(response)
                })
                .catch((error) => {
                    reject(error)
                })
        })
    }
    /**
     * GET All Topics by Farmer call
     * it is used the async axios function to send the request to the backend.
     * @return response
     * @exception general error
     * */
    GetAllTopicByFarmer=function () {
        let self = this
        return new Promise(function (resolve, reject) {
            let headers = self.getHeaders()
            axios.get(self._urlFarmer + 'getAllTopicByFarmer/',{headers:headers})
                .then((response) => {
                    resolve(response)
                })
                .catch((error) => {
                    reject(error)
                })
        })
    }
    /**
     * GET All Topics by word call
     * @param search word to search
     * it is used the async axios function to send the request to the backend.
     * @return response
     * @exception general error
     * */
    GetAllTopicByWord = function (search) {
        let self = this
        return new Promise(function (resolve, reject) {
            let headers = self.getHeaders()
            axios.get(self._urlFarmer + 'getAllTopicByWord/?word=' + search,{headers:headers})
                .then((response) => {
                    resolve(response)
                })
                .catch((error) => {
                    reject(error)
                })
        })
    }
    /**
     * GET All Topics by word and farmer call
     * @param search word to search
     * it is used the async axios function to send the request to the backend.
     * @return response
     * @exception general error
     * */
    GetAllTopicByWordF = function (search) {
        let self = this
        return new Promise(function (resolve, reject) {
            let headers = self.getHeaders()
            axios.get(self._urlFarmer + 'getAllTopicByWordF/?word=' + search,{headers:headers})
                .then((response) => {
                    resolve(response)
                })
                .catch((error) => {
                    reject(error)
                })
        })
    }
    /**
     * POST Topic call
     * @param topic class to add
     * first it is extrapolated all the data from the topic class creating a new the data class correctly formatted.
     * then it is used the async axios function to send the request to the backend.
     * @return response
     * @exception general error
     * */
    PostTopic=function (topic){
        let self=this
        return new Promise(function (resolve, reject){
            let data = {
                topic:topic.topic,
                description:topic.description ,
                tag:topic.tag
            }
            let headers = self.getHeaders()
            axios.post(self._urlFarmer+'PostTopic/', data,{headers:headers})
                .then((response)=>{
                    resolve(response)
                })
                .catch((error)=>{
                    reject(error)
                })
        })
    }
    /**
     * PUT Topic call
     * @param topic class to edit
     * first it is extrapolated all the data from the topic class creating a new the data class correctly formatted.
     * then it is used the async axios function to send the request to the backend.
     * @return response
     * @exception general error
     * */
    PutTopic=function (topic){
        let self=this
        return new Promise(function (resolve, reject){
            let data = {
                topic:topic.topic,
                description:topic.description ,
                tag:topic.tag
            }
            let headers = self.getHeaders()
            axios.post(self._urlFarmer+'PutTopic/'+topic.id, data,{headers:headers})
                .then((response)=>{
                    resolve(response)
                })
                .catch((error)=>{
                    reject(error)
                })
        })
    }
    /**
     * DELETE Topic call
     * @param id of topic to delete
     * it is used the async axios function to send the request to the backend.
     * @return response
     * @exception general error
     * */
    DeleteTopic=function (id){
        let self=this
        return new Promise(function (resolve, reject){
            let headers = self.getHeaders()
            axios.get(self._urlFarmer+'DeleteTopic/'+id,{headers:headers})
                .then((response)=>{
                    resolve(response)
                })
                .catch((error)=>{
                    reject(error)
                })
        })
    }


    /**
     * COMMENT CALLS
     * */
    /**
     * GET All Comment of a Topic call
     * @param topicId of topic of the comments
     * it is used the async axios function to send the request to the backend.
     * @return response
     * @exception general error
     * */
    GetAllComment = function (topicId) {
        let self = this
        return new Promise(function (resolve, reject) {
            let headers = self.getHeaders()
            axios.get(self._urlFarmer + 'getAllComment/'+topicId,{headers:headers})
                .then((response) => {
                    resolve(response)
                })
                .catch((error) => {
                    reject(error)
                })
        })
    }
    /**
     * Like Comment call
     * @param commentId of comment to put the like
     * it is used the async axios function to send the request to the backend.
     * @return response
     * @exception general error
     * */
    LikeComment=function (commentId){
        let self=this
        return new Promise(function (resolve, reject){
            let headers = self.getHeaders()
            axios.get(self._urlFarmer+'LikeComment/'+commentId,{headers:headers})
                .then((response)=>{
                    resolve(response)
                })
                .catch((error)=>{
                    reject(error)
                })
        })
    }
    /**
     * UnLike Comment call
     * @param commentId of comment to unlike
     * it is used the async axios function to send the request to the backend.
     * @return response
     * @exception general error
     * */
    UnLikeComment=function (commentId){
        let self=this
        return new Promise(function (resolve, reject){
            let headers = self.getHeaders()
            axios.get(self._urlFarmer+'UnLikeComment/'+commentId,{headers:headers})
                .then((response)=>{
                    resolve(response)
                })
                .catch((error)=>{
                    reject(error)
                })
        })
    }
    /**
     * POST Comment call
     * @param topicId of the topic where the comment will be added
     * @param comment class to add
     * first it is extrapolated all the data from the comment class creating a new the data class correctly formatted.
     * then it is used the async axios function to send the request to the backend.
     * @return response
     * @exception general error
     * */
    PostComment=function (topicId,comment){
        let self=this
        return new Promise(function (resolve, reject){
            let data = {
                comment:comment
            }
            let headers = self.getHeaders()
            axios.post(self._urlFarmer+'PostComment/'+topicId, data,{headers:headers})
                .then((response)=>{
                    resolve(response)
                })
                .catch((error)=>{
                    reject(error)
                })
        })
    }
    /**
     * PUT Comment call
     * @param comment class to edit
     * @param topicId of the topic where the comment will be edited
     * @param commentId of the comment that will be edited
     * first it is extrapolated all the data from the comment class creating a new the data class correctly formatted.
     * then it is used the async axios function to send the request to the backend.
     * @return response
     * @exception general error
     * */
    PutComment=function (comment,topicId,commentId){
        let self=this
        return new Promise(function (resolve, reject){
            let headers = self.getHeaders()
            let data = {
                comment:comment,

            }
            axios.post(self._urlFarmer+'PutComment/'+topicId+'?commentId='+commentId, data,{headers:headers})
                .then((response)=>{
                    resolve(response)
                })
                .catch((error)=>{
                    reject(error)
                })
        })
    }
    /**
     * DELETE Comment call
     * @param id of comment to delete
     * it is used the async axios function to send the request to the backend.
     * @return response
     * @exception general error
     * */
    DeleteComment=function (id){
        let self=this
        return new Promise(function (resolve, reject){
            let headers = self.getHeaders()
            axios.get(self._urlFarmer+'DeleteComment/'+id,{headers:headers})
                .then((response)=>{
                    resolve(response)
                })
                .catch((error)=>{
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
    GetAllKnowledge = function () {
        let self = this
        return new Promise(function (resolve, reject) {
            let headers = self.getHeaders()
            axios.get(self._urlFarmer + 'getAllKnowledge/',{headers:headers})
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
    GetAllKnowledgeByWordF = function (search) {
        let self = this
        return new Promise(function (resolve, reject) {
            let headers = self.getHeaders()
            axios.get(self._urlFarmer + 'getAllKnowledgeByWordF/?word=' + search,{headers:headers})
                .then((response) => {
                    resolve(response)
                })
                .catch((error) => {
                    reject(error)
                })
        })
    }
    /**
     * Like Knowledge call
     * @param knowledgeId of knowledge to put the like
     * it is used the async axios function to send the request to the backend.
     * @return response
     * @exception general error
     * */
    LikeKnowledge=function (knowledgeId){
        let self=this
        return new Promise(function (resolve, reject){
            let headers = self.getHeaders()
            axios.get(self._urlFarmer+'LikeKnowledge/'+knowledgeId,{headers:headers})
                .then((response)=>{
                    resolve(response)
                })
                .catch((error)=>{
                    reject(error)
                })
        })
    }
    /**
     * UnLike Knowledge call
     * @param knowledgeId of knowledge to unlike
     * it is used the async axios function to send the request to the backend.
     * @return response
     * @exception general error
     * */
    UnLikeKnowledge=function (knowledgeId){
        let self=this
        return new Promise(function (resolve, reject){
            let headers = self.getHeaders()
            axios.get(self._urlFarmer+'UnLikeKnowledge/'+knowledgeId,{headers:headers})
                .then((response)=>{
                    resolve(response)
                })
                .catch((error)=>{
                    reject(error)
                })
        })
    }


    /**
     * PRODUCT CALLS
     * */
    /**
     * GET All Products By Farm call
     * it is used the async axios function to send the request to the backend.
     * @return response
     * @exception general error
     * */
    GetProductsByFarm=function () {
        let self = this
        return new Promise(function (resolve, reject) {
            let headers = self.getHeaders()
            axios.get(self._urlFarmer + 'GetProductsByFarm/',{headers:headers})
                .then((response) => {
                    resolve(response)
                })
                .catch((error) => {
                    reject(error)
                })
        })
    }
    /**
     * POST product call
     * @param product class to add
     * first it is extrapolated all the data from the product class creating a new the data class correctly formatted.
     * then it is used the async axios function to send the request to the backend.
     * @return response
     * @exception general error
     * */
    PostProduct=function (product){
        let self=this
        return new Promise(function (resolve, reject){
            let headers = self.getHeaders()
            let data = {
                product: product.name,
                type:product.type,
                specifics:product.specifics
            }
            axios.post(self._urlFarmer+'PostProduct/', data,{headers:headers})
                .then((response)=>{
                    resolve(response)
                })
                .catch((error)=>{
                    reject(error)
                })
        })
    }
    /**
     * PUT Product call
     * @param product class to edit
     * @param productId of the product that will be edited
     * first it is extrapolated all the data from the product class creating a new the data class correctly formatted.
     * then it is used the async axios function to send the request to the backend.
     * @return response
     * @exception general error
     * */
    PutProduct=function (product,productId){
        let self=this
        return new Promise(function (resolve, reject){
            let headers = self.getHeaders()
            let data = {
                product: product.name,
                type:product.type,
                specifics:product.specifics
            }
            axios.post(self._urlFarmer+'PutProduct/'+productId, data,{headers:headers})
                .then((response)=>{
                    resolve(response)
                })
                .catch((error)=>{
                    reject(error)
                })
        })
    }
    /**
     * DELETE Product call
     * @param id of product to delete
     * it is used the async axios function to send the request to the backend.
     * @return response
     * @exception general error
     * */
    DeleteProduct=function (id){
        let self=this
        return new Promise(function (resolve, reject){
            let headers = self.getHeaders()
            axios.get(self._urlFarmer+'DeleteProduct/'+id,{headers:headers})
                .then((response)=>{
                    resolve(response)
                })
                .catch((error)=>{
                    reject(error)
                })
        })
    }


    /**
     * PRODUCTION CALLS
     * */
    /**
     * GET All Production by Farm and Product call
     * it is used the async axios function to send the request to the backend.
     * @return response
     * @exception general error
     * */
    GetProductionsByFarmAndProduct=function (productId) {
        let self = this
        return new Promise(function (resolve, reject) {
            let headers = self.getHeaders()
            axios.get(self._urlFarmer + 'GetProductionsByFarmAndProduct/'+productId,{headers:headers})
                .then((response) => {
                    resolve(response)
                })
                .catch((error) => {
                    reject(error)
                })
        })
    }
    /**
     * POST Production call
     * @param productId of the product where the production will be added
     * @param production class to add
     * first it is extrapolated all the data from the production class creating a new the data class correctly formatted.
     * then it is used the async axios function to send the request to the backend.
     * @return response
     * @exception general error
     * */
    PostProduction=function (production,productId){
        let self=this
        return new Promise(function (resolve, reject){
            let headers = self.getHeaders()
            let data = {
                qta: production.amount,
                note:production.note
            }
            axios.post(self._urlFarmer+'PostProduction/'+productId, data,{headers:headers})
                .then((response)=>{
                    resolve(response)
                })
                .catch((error)=>{
                    reject(error)
                })
        })
    }
    /**
     * PUT Production call
     * @param production class to edit
     * @param productId of the product where the production will be edited
     * @param productionId of the production that will be edited
     * first it is extrapolated all the data from the production class creating a new the data class correctly formatted.
     * then it is used the async axios function to send the request to the backend.
     * @return response
     * @exception general error
     * */
    PutProduction=function (production,productId,productionId){
        let self=this
        return new Promise(function (resolve, reject){
            let headers = self.getHeaders()
            let data = {
                qta: production.amount,
                note:production.note
            }
            axios.post(self._urlFarmer+'PutProduction/'+productId+'?productionId='+productionId, data,{headers:headers})
                .then((response)=>{
                    resolve(response)
                })
                .catch((error)=>{
                    reject(error)
                })
        })
    }
    /**
     * DELETE Production call
     * @param id of production to delete
     * it is used the async axios function to send the request to the backend.
     * @return response
     * @exception general error
     * */
    DeleteProduction=function (id){
        let self=this
        return new Promise(function (resolve, reject){
            let headers = self.getHeaders()
            axios.get(self._urlFarmer+'DeleteProduction/'+id,{headers:headers})
                .then((response)=>{
                    resolve(response)
                })
                .catch((error)=>{
                    reject(error)
                })
        })
    }


    /**
     * SOIL DATA CALLS
     * */
    /**
     * GET Soil Data of the Farm call
     * it is used the async axios function to send the request to the backend.
     * @return response
     * @exception general error
     * */
    GetSoilDataOfFarm=function () {
        let self = this
        return new Promise(function (resolve, reject) {
            let headers = self.getHeaders()
            axios.get(self._urlFarmer + 'GetSoilDataOfFarm/',{headers:headers})
                .then((response) => {
                    resolve(response)
                })
                .catch((error) => {
                    reject(error)
                })
        })
    }
    /**
     * GET Water Consumption call
     * it is used the async axios function to send the request to the backend.
     * @return response
     * @exception general error
     * */
    GetWaterConsumption=function () {
        let self = this
        return new Promise(function (resolve, reject) {
            let headers = self.getHeaders()
            axios.get(self._urlFarmer + 'GetWaterConsumption/',{headers:headers})
                .then((response) => {
                    resolve(response)
                })
                .catch((error) => {
                    reject(error)
                })
        })
    }
    /**
     * GET Temperature call
     * it is used the async axios function to send the request to the backend.
     * @return response
     * @exception general error
     * */
    GetTemperature=function () {
        let self = this
        return new Promise(function (resolve, reject) {
            let headers = self.getHeaders()
            axios.get(self._urlFarmer + 'GetTemperature/',{headers:headers})
                .then((response) => {
                    resolve(response)
                })
                .catch((error) => {
                    reject(error)
                })
        })
    }
    /**
     * GET Humidity call
     * it is used the async axios function to send the request to the backend.
     * @return response
     * @exception general error
     * */
    GetHumidity=function () {
        let self = this
        return new Promise(function (resolve, reject) {
            let headers = self.getHeaders()
            axios.get(self._urlFarmer + 'GetHumidity/',{headers:headers})
                .then((response) => {
                    resolve(response)
                })
                .catch((error) => {
                    reject(error)
                })
        })
    }


    /**
     * MEETING CALLS
     * */
    /**
     * GET All Meeting call
     * it is used the async axios function to send the request to the backend.
     * @return response
     * @exception general error
     * */
    GetAllMeetingsF=function () {
        let self = this
        return new Promise(function (resolve, reject) {
            let headers = self.getHeaders()
            axios.get(self._urlFarmer + 'GetAllMeetingsF/',{headers:headers})
                .then((response) => {
                    resolve(response)
                })
                .catch((error) => {
                    reject(error)
                })
        })
    }
    /**
     * GET All Meeting Closed call
     * it is used the async axios function to send the request to the backend.
     * @return response
     * @exception general error
     * */
    GetAllMeetingsClosed=function () {
        let self = this
        return new Promise(function (resolve, reject) {
            let headers = self.getHeaders()
            axios.get(self._urlFarmer + 'GetAllMeetingsClosed/',{headers:headers})
                .then((response) => {
                    resolve(response)
                })
                .catch((error) => {
                    reject(error)
                })
        })
    }
    /**
     * Change status meeting call
     * @param id of meeting to change the status
     * @param state the new state of the meeting
     * it is used the async axios function to send the request to the backend.
     * @return response
     * @exception general error
     * */
    ChangeStatusMeeting=function (id,state) {
        let self = this
        return new Promise(function (resolve, reject) {
            let headers = self.getHeaders()
            axios.post(self._urlFarmer + 'ChangeStatusMeeting/'+id+'&state='+state,{headers:headers})
                .then((response) => {
                    resolve(response)
                })
                .catch((error) => {
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
    GetAllNotificationF=function () {
        let self = this
        return new Promise(function (resolve, reject) {
            let headers = self.getHeaders()
            axios.get(self._urlFarmer + 'GetAllNotificationF/',{headers:headers})
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
    DeleteNotificationF=function (id){
        let self=this
        return new Promise(function (resolve, reject){
            let headers = self.getHeaders()
            axios.get(self._urlFarmer+'DeleteNotificationF/'+id,{headers:headers})
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
    GetRequestByFarmerPendant= function () {
        let self = this
        return new Promise(function (resolve, reject) {
            let headers = self.getHeaders()
            axios.get(self._urlFarmer + 'GetRequestByFarmerPendant/',{headers:headers})
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
    GetRequestByFarmerNoFeed= function () {
        let self = this
        return new Promise(function (resolve, reject) {
            let headers = self.getHeaders()
            axios.get(self._urlFarmer + 'GetRequestByFarmerNoFeed/',{headers:headers})
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
    GetRequestByFarmerClosed= function () {
        let self = this
        return new Promise(function (resolve, reject) {
            let headers = self.getHeaders()
            axios.get(self._urlFarmer + 'GetRequestByFarmerClosed/',{headers:headers})
                .then((response) => {
                    resolve(response)
                })
                .catch((error) => {
                    reject(error)
                })
        })
    }
    /**
     * POST Request call
     * @param request request to add
     * first it is extrapolated all the data from the request class creating a new the data class correctly formatted.
     * then it is used the async axios function to send the request to the backend.
     * @return response
     * @exception general error
     * */
    PostRequest=function (request){
        let self=this
        return new Promise(function (resolve, reject){
            let headers = self.getHeaders()
            let data = {
                subject:request.subject,
                request:request.body
            }
            axios.post(self._urlFarmer+'PostRequest/', data,{headers:headers})
                .then((response)=>{
                    resolve(response)
                })
                .catch((error)=>{
                    reject(error)
                })
        })
    }
    /**
     * PUT Feedback call
     * @param feedback to add to the request
     * @param requestId of the request that will be edited
     * it is used the async axios function to send the request to the backend.
     * @return response
     * @exception general error
     * */
    PutFeedback=function (feedback,requestId){
        let self=this
        return new Promise(function (resolve, reject){
            let headers = self.getHeaders()
            let data = {
                feedback:feedback
            }

            axios.post(self._urlFarmer+'PutFeedBack/'+requestId, data,{headers:headers})
                .then((response)=>{
                    resolve(response)
                })
                .catch((error)=>{
                    reject(error)
                })
        })
    }
    /**
     * DELETE Request call
     * @param requestId of request to delete
     * it is used the async axios function to send the request to the backend.
     * @return response
     * @exception general error
     * */
    DeleteRequest=function (requestId){
        let self=this
        return new Promise(function (resolve, reject){
            let headers = self.getHeaders()
            axios.get(self._urlFarmer+'DeleteRequest/'+requestId,{headers:headers})
                .then((response)=>{
                    resolve(response)
                })
                .catch((error)=>{
                    reject(error)
                })
        })
    }
}

