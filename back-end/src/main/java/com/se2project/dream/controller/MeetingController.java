package com.se2project.dream.controller;

import com.se2project.dream.entity.Meeting;
import com.se2project.dream.extraClasses.Response;
import com.se2project.dream.service.LoginService;
import com.se2project.dream.service.MeetingService;
import org.springframework.web.bind.annotation.*;
@RequestMapping("/api")
@CrossOrigin
@RestController
public class MeetingController {

    private final MeetingService meetingService;
    private final LoginService loginService;

    public MeetingController(MeetingService meetingService, LoginService loginService) {
        this.meetingService = meetingService;
        this.loginService = loginService;
    }

    /**
     * Get all meeting of the logged Agronomist filtered by date
     * @param tokenA agronomist authentication token generated on login
     * @param date on witch the meeting filtering is made
     * first it extrapolates the id from the token by the function @see getIdFromTokenA() in loginService
     * then with the function getMeetingsAbyDate @see MeetingService it returns all the meeting of that day
     * @return set of meeting
     * @exception 404 Agronomist Not Found
     * @exception 404 No Meeting Found
     * */
    @GetMapping("/agronomist/GetMeetingByAgronomistD/{date}")
    Response getMeetingByAgronomistD(@RequestHeader("Authorization") String tokenA, @PathVariable String date){
        Long agronomistId =loginService.getIdFromTokenA(tokenA);
        return meetingService.getMeetingsAbyDate(agronomistId, date);
    }

    /**
     * Get all today meeting of the logged Agronomist
     * @param tokenA agronomist authentication token generated on login
     * first it extrapolates the id from the token by the function @see getIdFromTokenA() in loginService
     * then with the function getTodaysMeetingsA @see MeetingService it returns all today's meeting
     * @return set of meeting
     * @exception 404 Agronomist Not Found
     * @exception 404 No Meeting Found
     * */
    @GetMapping("/agronomist/GetTodaysMeetingsA")
    Response getTodaysMeetingsA(@RequestHeader("Authorization") String tokenA) {
        Long agronomistId =loginService.getIdFromTokenA(tokenA);
        return meetingService.getTodaysMeetingsA(agronomistId);
    }

    /**
     * Get all next meeting of the logged Agronomist, next means all meeting that are after the moment he open the meeting page
     * @param tokenA agronomist authentication token generated on login
     * first it extrapolates the id from the token by the function @see getIdFromTokenA() in loginService
     * then with the function getAllMeetingsA @see MeetingService it returns all meetings
     * @return set of meeting
     * @exception 404 Agronomist Not Found
     * @exception 404 No Meeting Found
     * */
    @GetMapping("/agronomist/GetAllMeetingsA")
    Response getAllMeetingsA(@RequestHeader("Authorization") String tokenA) {
        Long agronomistId =loginService.getIdFromTokenA(tokenA);
        return meetingService.getAllMeetingsA(agronomistId);
    }

    /**
     * Get all meeting concluded of the logged Agronomist, concluded means all meeting that are older than the moment he
     * open the concluded meeting page
     * @param tokenA agronomist authentication token generated on login
     * first it extrapolates the id from the token by the function @see getIdFromTokenA() in loginService
     * then with the function getAllMeetingsConclused @see MeetingService it returns all meetings
     * @return set of meeting
     * @exception 404 Agronomist Not Found
     * @exception 404 No Meeting Found
     * */
    @GetMapping("/agronomist/GetAllMeetingsConclused")
    Response getAllMeetingsConclused(@RequestHeader("Authorization") String tokenA) {
        Long agronomistId =loginService.getIdFromTokenA(tokenA);
        return meetingService.getAllMeetingsConclused(agronomistId);
    }

    /**
     * Get all meeting of the logged Farmer filtered by date
     * @param tokenF farmer authentication token generated on login
     * @param date on witch the meeting filtering is made
     * first it extrapolates the id from the token by the function @see getIdFromTokenF() in loginService
     * then with the function getMeetingsFbyDate @see MeetingService it returns all the meeting of that day
     * @return set of meeting
     * @exception 404 Farmer Not Found
     * @exception 404 No Meeting Found
     * */
    @GetMapping("/farmer/GetMeetingByFarmerD/{date}")
    Response getMeetingByFarmerD(@RequestHeader("Authorization") String tokenF, @PathVariable String date) {
        Long farmerId =loginService.getIdFromTokenF(tokenF);
        return meetingService.getMeetingFbyDate(farmerId, date);
    }

    /**
     * Get all today meeting of the logged Farmer
     * @param tokenF agronomist authentication token generated on login
     * first it extrapolates the id from the token by the function @see getIdFromTokenF() in loginService
     * then with the function getTodaysMeetingsF @see MeetingService it returns all today's meeting
     * @return set of meeting
     * @exception 404 Farmer Not Found
     * @exception 404 No Meeting Found
     * */
    @GetMapping("/farmer/GetTodaysMeetingsF")
    Response getTodaysMeetingsF(@RequestHeader("Authorization") String tokenF) {
        Long farmerId =loginService.getIdFromTokenF(tokenF);
        return meetingService.getTodaysMeetingsF(farmerId);
    }

    /**
     * Get all next meeting of the logged Farmer, next means all meeting that are after the moment he open the meeting page
     * @param tokenF farmer authentication token generated on login
     * first it extrapolates the id from the token by the function @see getIdFromTokenF() in loginService
     * then with the function getAllMeetingsF @see MeetingService it returns all meetings
     * @return set of meeting
     * @exception 404 Farmer Not Found
     * @exception 404 No Meeting Found
     * */
    @GetMapping("/farmer/GetAllMeetingsF")
    Response getAllMeetingsF(@RequestHeader("Authorization") String tokenF) {
        Long farmerId =loginService.getIdFromTokenF(tokenF);
        return meetingService.getAllMeetingsF(farmerId);
    }

    /**
     * Get all meeting closed of the logged Farmer, closed means all meeting that has been evaluated by the agronomist
     * @param tokenF farmer authentication token generated on login
     * first it extrapolates the id from the token by the function @see getIdFromTokenF() in loginService
     * then with the function getAllMeetingsClosed @see MeetingService it returns all meetings
     * @return set of meeting
     * @exception 404 Farmer Not Found
     * @exception 404 No Meeting Found
     * */

    @GetMapping("/farmer/GetAllMeetingsClosed")
    Response getAllMeetingsClosed(@RequestHeader("Authorization") String tokenF) {
        Long farmerId =loginService.getIdFromTokenF(tokenF);
        return meetingService.getAllMeetingsClosed(farmerId);
    }

    /**
     * Function that allow the logged agronomist to create a meeting
     * @param tokenA agronomist authentication token generated on login
     * @param farmerId of the farmer the agronomist want to make a meeting with
     * @param newMeeting meeting to insert
     * first it extrapolates the id from the token by the function @see getIdFromTokenA() in loginService
     * then  with the function createMeeting @see meetingService create the meeting
     * @return newKnowledge
     * @exception 404 Farmer Not Found
     * @exception 404 Agronomist Not Found
     * @exception 400 The selected meeting time is out of working hour!
     * @exception 400 The selected meeting time is incorrect the start time should be before the end time!
     * @exception 400 The selected meeting time overlapse with another meeting that day!
     * @exception 400 The selected meeting date is incorrect it should have at least a day of forewarning!
     * */
    @PostMapping("/agronomist/PostMeeting/{farmerId}")
    Response createMeeting(@PathVariable Long farmerId, @RequestHeader("Authorization") String tokenA, @RequestBody Meeting newMeeting) {
        Long agronomistId =loginService.getIdFromTokenA(tokenA);
        return meetingService.createMeeting(farmerId, agronomistId, newMeeting);
    }

    /**
     * Function that allow the logged farmer to confirm, reject or cancel a meeting
     * @param tokenF agronomist authentication token generated on login
     * @param meetingId of the meeting the farmer want to confirm, reject or cancel
     * @param state status he want to put the meeting: confirmed or rejected
     * first it extrapolates the id from the token by the function @see getIdFromTokenF() in loginService
     * then  with the function changeStatusMeeting @see meetingService it update the status of the meeting
     * @return meeting
     * @exception 404 Farmer Not Found
     * @exception 404 Meeting Not Found
     * @exception 400 You are not allowed to confirm or reject this meet
     * @exception 400 You've already confirmed/rejected this meeting
     * @exception 400 It's too late to confirm or reject this meeting
     * */
    @PostMapping("/farmer/ChangeStatusMeeting/{meetingId}")
    Response changeStatusMeeting(@RequestHeader("Authorization") String tokenF, @PathVariable Long meetingId, @RequestParam String state) {
        Long farmerId =loginService.getIdFromTokenF(tokenF);
        return meetingService.changeStatusMeeting(farmerId, meetingId, state);
    }

    /**
     * Function that allow the logged agronomist to give an evaluation of a meeting
     * @param tokenA agronomist authentication token generated on login
     * @param meetingId of the meeting the agronomist want to evaluate
     * @param meeting value to update
     * first it extrapolates the id from the token by the function @see getIdFromTokenA() in loginService
     * then  with the function closeMeeting @see meetingService update the meeting and set the status on closed
     * @return meeting
     * @exception 404 Farmer Not Found
     * @exception 404 Agronomist Not Found
     * @exception 400 You already evaluate this meeting!
     * @exception 400 You cannot evaluate the farmer before the meeting
     * @exception 400 You are not allowed to evaluate this meet
     * */
    @PostMapping("/agronomist/CloseMeeting/{meetingId}")
    Response closeMeeting(@RequestHeader("Authorization") String tokenA, @PathVariable Long meetingId, @RequestBody Meeting meeting) {
        Long agronomistId =loginService.getIdFromTokenA(tokenA);
        return meetingService.closeMeeting(agronomistId, meetingId, meeting);
    }

    /**
     * Function that allow the logged agronomist to update a meeting
     * @param tokenA agronomist authentication token generated on login
     * @param meetingId of the meeting the agronomist want to update
     * @param meetingTdo meeting new value
     * first it extrapolates the id from the token by the function @see getIdFromTokenA() in loginService
     * then  with the function updateMeeting @see meetingService update the meeting
     * @return newKnowledge
     * @exception 404 Farmer Not Found
     * @exception 404 Agronomist Not Found
     * @exception 400 The selected meeting time is out of working hour!
     * @exception 400 The selected meeting time is incorrect the start time should be before the end time!
     * @exception 400 The selected meeting time overlapse with another meeting that day!
     * @exception 400 The selected meeting date is incorrect it should have at least a day of forewarning!
     * @exception 400 It's too late to update this meeting
     * */
    @PostMapping("/agronomist/PutMeeting/{meetingId}")
    Response updateMeeting(@PathVariable Long meetingId, @RequestHeader("Authorization") String tokenA, @RequestBody Meeting meetingTdo) {
        Long agronomistId =loginService.getIdFromTokenA(tokenA);
        return meetingService.updateMeeting(agronomistId,meetingId,meetingTdo);
    }

    /**
     * Function that allow the logged agronomist to delete a meeting
     * @param tokenA agronomist authentication token generated on login
     * @param meetingId of the meeting the agronomist want to evaluate
     * first it extrapolates the id from the token by the function @see getIdFromTokenA() in loginService
     * then  with the function deleteMeetingA @see meetingService delete the meeting
     * @return meeting
     * @exception 404 Farmer Not Found
     * @exception 404 Agronomist Not Found
     * @exception 400 It's to late to delete this meeting
     * @exception 400 You are not allowed to delete this meet
     * */
    @GetMapping("/agronomist/DeleteMeeting/{meetingId}")
    Response deleteMeeting(@RequestHeader("Authorization") String tokenA,@PathVariable Long meetingId) {
        Long agronomistId =loginService.getIdFromTokenA(tokenA);
       return meetingService.deleteMeetingA(agronomistId,meetingId);
    }
}