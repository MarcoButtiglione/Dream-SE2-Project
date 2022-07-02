package com.se2project.dream.service;

import com.se2project.dream.entity.*;
import com.se2project.dream.extraClasses.Response;
import com.se2project.dream.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

@Service
public class MeetingService {
    private final MeetingRepository meetingRepository;
    private final AgronomistRepository agronomistRepository;
    private final FarmerRepository farmerRepository;
    private final NotificationFarmerRepository notificationFarmerRepository;
    private final NotificationAgronomistRepository notificationAgronomistRepository;

    public MeetingService(MeetingRepository meetingRepository, AgronomistRepository agronomistRepository, FarmerRepository farmerRepository, NotificationFarmerRepository notificationFarmerRepository, NotificationAgronomistRepository notificationAgronomistRepository) {
        this.meetingRepository = meetingRepository;
        this.agronomistRepository = agronomistRepository;
        this.farmerRepository = farmerRepository;
        this.notificationFarmerRepository = notificationFarmerRepository;
        this.notificationAgronomistRepository = notificationAgronomistRepository;
    }

    /**
     * Get all meetings of an agronomist filtered by date
     * @param agronomistId of the logged in agronomist
     * @param date to filter meeting
     * first is checked if the agronomist exist by function findById @see agronomistRepository else @exception 404 Agronomist Not Found
     * then with the function findAllByAgronomistD @see meetingRepository it return all the meeting of that day
     * if no meeting was found @exception 404 No Meeting Found
     * else @return set of meetings
     */
    public Response getMeetingsAbyDate(Long agronomistId, String date){
        Response response = new Response();
        Agronomist agronomist=agronomistRepository.findById(agronomistId).orElse(null);
        if(agronomist==null){
            response.setCode(404);
            response.setMessage("Agronomist Not Found");
        }
        else{
            Iterable<Meeting> meetings=meetingRepository.findAllByAgronomistD(agronomistId, date);
            if(meetings.toString().equals("[]")){
                response.setCode(404);
                response.setMessage("No Meetings Found");
            }
            else{
                response.setCode(200);
                response.setMessage("success");
                response.setResults(Collections.singleton(meetings));
            }
        }
        return response;
    }

    /**
     * Get all meetings of an agronomist filtered by date where day is today
     * @param agronomistId of the logged in agronomist
     * first is checked if the agronomist exist by function findById @see agronomistRepository else @exception 404 Agronomist Not Found
     * then with the function findAllByAgronomistD(today) @see meetingRepository it return all the meeting of today
     * if no meeting was found @exception 404 No Meeting Found
     * else @return set of meetings
     */
    public Response getTodaysMeetingsA(Long agronomistId){
        Response response = new Response();
        Agronomist agronomist=agronomistRepository.findById(agronomistId).orElse(null);
        if(agronomist==null){
            response.setCode(404);
            response.setMessage("Agronomist Not Found");
        }
        else{
            String date = String.valueOf(LocalDate.now());
            List<Meeting> meetings=meetingRepository.findAllByAgronomistD(agronomistId, date);
            if(meetings.toString().equals("[]")){
                response.setCode(404);
                response.setMessage("No Meetings Found");
            }
            else{
                response.setCode(200);
                response.setMessage("success");
                response.setResults(Collections.singleton(meetings));
            }
        }
        return response;
    }

    /**
     * Get all meetings of an agronomist after current day
     * @param agronomistId of the logged in agronomist
     * first is checked if the agronomist exist by function findById @see agronomistRepository else @exception 404 Agronomist Not Found
     * then with the function findAllByAgronomist @see meetingRepository it return all the meeting after current day
     * if no meeting was found @exception 404 No Meeting Found
     * else @return set of meetings
     */
    public Response getAllMeetingsA(Long agronomistId){
        Response response = new Response();
        Agronomist agronomist=agronomistRepository.findById(agronomistId).orElse(null);
        if(agronomist==null){
            response.setCode(404);
            response.setMessage("Agronomist Not Found");
        }
        else{
            Iterable<Meeting> meetings=meetingRepository.findAllByAgronomist(agronomistId, LocalDate.now());
            if(meetings.toString().equals("[]")){
                response.setCode(404);
                response.setMessage("No Meetings Found");
            }
            else{
                response.setCode(200);
                response.setMessage("success");
                response.setResults(Collections.singleton(meetings));
            }
        }
        return response;
    }

    /**
     * Get all meetings of an agronomist that are in conclused status
     * @param agronomistId of the logged in agronomist
     * first is checked if the agronomist exist by function findById @see agronomistRepository else @exception 404 Agronomist Not Found
     * then with the function findAllOldByAgronomist @see meetingRepository it return all the meeting before current day and time
     * if no meeting was found @exception 404 No Meeting Found
     * else for each meeting if status confirmed update meeting with status conclused and add meeting to conclusedMeetings
     * else if status rejected or pendant delete meeting
     * @return conclusedMeetings
     */
    public Response getAllMeetingsConclused(Long agronomistId){
        Response response = new Response();
        Agronomist agronomist=agronomistRepository.findById(agronomistId).orElse(null);
        if(agronomist==null){
            response.setCode(404);
            response.setMessage("Agronomist Not Found");
        }
        else{
            Iterable<Meeting> meetings=meetingRepository.findAllOldByAgronomist(agronomistId, LocalDate.now());
            if(meetings.toString().equals("[]")){
                response.setCode(404);
                response.setMessage("No Meetings Found");
            }
            else{
                for(Meeting m:meetings){
                    if (m.getDate().isBefore(LocalDate.now()) || (m.getDate().equals(LocalDate.now()) & m.getStartTime().isBefore(LocalTime.now()))) {
                        if (m.getState().equals("confirmed")) {
                            m.setState("conclused");
                            meetingRepository.save(m);
                        } else if (m.getState().equals("rejected") || m.getState().equals("pendant")) {
                            meetingRepository.delete(m);
                        }
                    }
                }
                Iterable<Meeting> meetingsConclused=meetingRepository.findConclusedByAgronomist(agronomistId);
                response.setCode(200);
                response.setMessage("success");
                response.setResults(Collections.singleton(meetingsConclused));
            }
        }
        return response;
    }

    /**
     * Get all meetings of an farmer filtered by date where day is today
     * @param farmerId of the logged in farmer
     * first is checked if the farmer exist by function findById @see farmerRepository else @exception 404 Farmer Not Found
     * then with the function findAllByFarmerD(today) @see meetingRepository it return all the meeting of today
     * if no meeting was found @exception 404 No Meeting Found
     * else @return set of meetings
     */
    public Response getTodaysMeetingsF(Long farmerId){
        Response response = new Response();
        Farmer farmer=farmerRepository.findById(farmerId).orElse(null);
        if(farmer==null){
            response.setCode(404);
            response.setMessage("Farmer Not Found");
        }
        else{
            String date = String.valueOf(LocalDate.now());
            Iterable<Meeting> meetings=meetingRepository.findAllByFarmerD(farmerId,date);
            if(meetings.toString().equals("[]")){
                response.setCode(404);
                response.setMessage("No Meetings Found");
            }
            else{
                response.setCode(200);
                response.setMessage("success");
                response.setResults(Collections.singleton(meetings));
            }
        }

        return response;
    }

    /**
     * Get all meetings of an farmer after current day
     * @param farmerId of the logged in farmer
     * first is checked if the farmer exist by function findById @see farmerRepository else @exception 404 Farmer Not Found
     * then with the function findAllByFarmer @see meetingRepository it return all the meeting after current day
     * if no meeting was found @exception 404 No Meeting Found
     * else @return set of meetings
     */
    public Response getAllMeetingsF(Long farmerId){
        Response response = new Response();
        Farmer farmer=farmerRepository.findById(farmerId).orElse(null);
        if(farmer==null){
            response.setCode(404);
            response.setMessage("Farmer Not Found");
        }
        else{
            Iterable<Meeting> meetings=meetingRepository.findAllByFarmer(farmerId,LocalDate.now());
            if(meetings.toString().equals("[]")){
                response.setCode(404);
                response.setMessage("No Meetings Found");
            }
            else{
                response.setCode(200);
                response.setMessage("success");
                response.setResults(Collections.singleton(meetings));
            }
        }
        return response;
    }

    /**
     * Get all meetings of an agronomist filtered by date
     * @param farmerId of the logged in farmer
     * @param date to filter meeting
     * first is checked if the farmer exist by function findById @see farmerRepository else @exception 404 Farmer Not Found
     * then with the function findAllByFarmerD @see meetingRepository it return all the meeting of that day
     * if no meeting was found @exception 404 No Meeting Found
     * else @return set of meetings
     */
    public Response getMeetingFbyDate(Long farmerId, String date){
        Response response = new Response();
        Farmer farmer=farmerRepository.findById(farmerId).orElse(null);
        if(farmer==null){
            response.setCode(404);
            response.setMessage("Farmer Not Found");
        }
        else{
            Iterable<Meeting> meetings=meetingRepository.findAllByFarmerD(farmerId,date);
            if(meetings.toString().equals("[]")){
                response.setCode(404);
                response.setMessage("No Meetings Found");
            }
            else{
                response.setCode(200);
                response.setMessage("success");
                response.setResults(Collections.singleton(meetings));
            }
        }

        return response;
    }

    /**
     * Get all meetings of an agronomist that are in closed status
     * @param farmerId of the logged in farmer
     * first is checked if the farmer exist by function findById @see farmerRepository else @exception 404 Farmer Not Found
     * then with the function findAllClosed @see meetingRepository it return all the meeting closed with evaluation of the farmer
     * if no meeting was found @exception 404 No Meeting Found
     * @return closeddMeetings
     */
    public Response getAllMeetingsClosed(Long farmerId) {
        Response response = new Response();
        Farmer farmer=farmerRepository.findById(farmerId).orElse(null);
        if(farmer==null){
            response.setCode(404);
            response.setMessage("Farmer Not Found");
        }
        else{
            Iterable<Meeting> meetings=meetingRepository.findAllClosed(farmerId);
            if(meetings.toString().equals("[]")){
                response.setCode(404);
                response.setMessage("No Meetings Found");
            }
            else{
                response.setCode(200);
                response.setMessage("success");
                response.setResults(Collections.singleton(meetings));
            }
        }

        return response;
    }

    /**
     * create a new Meeting
     * @param agronomistId the agronmist that create the meeting
     * @param farmerId with witch the meeting will be done
     * @param newMeeting knowledge data to insert
     * first is checked if the agronomist exist by function findById @ see agronomistRepository @exception 404 Agronomist Not Found
     * first is checked if the farmer exist by function findById @ see farmerRepository @exception 404 Farmer Not Found
     * then is checked if the timing selected is correct:
     *                   1)start before 06:00 or end after 20:30 @exception The selected meeting time is out of working hour!
     *                   2)start before end @exception The selected meeting time is incorrect the start time should be before the end time!
     *                   3)there's no other meetings that day? meeting saved and notification to farmer saved in the db
     *                   4)there's meeting that day with overlapse timing @exception The selected meeting time overlapse with another meeting that day!
     *                   else save meeting and send notification
     *                   5) today is day before meeting day @exception The selected meeting date is incorrect it should have at least a day of forewarning!
     * @return newMeeting
     */
    public Response createMeeting(Long farmerId, Long agronomistId, Meeting newMeeting) {
        Response response = new Response();
        Farmer farmer = farmerRepository.findById(farmerId).orElse(null);
        if (farmer == null) {
            response.setCode(404);
            response.setMessage("Farmer Not Found");
        } else {
            Agronomist agronomist = agronomistRepository.findById(agronomistId).orElse(null);
            if (agronomist == null) {
                response.setCode(404);
                response.setMessage("Agronomist Not Found");
            } else {
                if(newMeeting.getDate().isAfter(LocalDate.now())) {
                    if (newMeeting.getStartTime().isBefore(LocalTime.of(6, 0)) || newMeeting.getEndTime().isAfter(LocalTime.of(20, 30))) {
                        response.setCode(400);
                        response.setMessage("The selected meeting time is out of working hour!");
                    } else {
                        if (newMeeting.getStartTime().isAfter(newMeeting.getEndTime())) {
                            response.setCode(400);
                            response.setMessage("The selected meeting time is incorrect the start time should be before the end time!");
                        } else {
                            String date = String.valueOf(newMeeting.getDate());
                            List<Meeting> meetings = meetingRepository.findAllByAgronomistD(agronomistId, date);
                            if (meetings.size() == 0) {
                                newMeeting.setFarmer(farmer);
                                newMeeting.setAgronomist(agronomist);
                                newMeeting.setState("pendant");
                                newMeeting.setEvaluation(0);
                                newMeeting.setNote(null);
                                meetingRepository.save(newMeeting);
                                String desc= "The local agronomist Mr/Ms. "+agronomist.getLastName()+" just created a meeting for the "+ newMeeting.getDate()+ " from  "+newMeeting.getStartTime().toString().subSequence(0,5)+" untill "+newMeeting.getEndTime().toString().subSequence(0,5)+" do you want to accept or reject the proposal?";
                                NotificationFarmer notification = new NotificationFarmer("NEW_MEETING", desc,farmer,newMeeting);
                                notificationFarmerRepository.save(notification);
                                response.setCode(200);
                                response.setMessage("success");
                                response.setResults(Collections.singleton(newMeeting));
                            } else {
                                for (Meeting m : meetings) {
                                    LocalTime start = m.getStartTime();
                                    LocalTime end = m.getEndTime();
                                    if ((newMeeting.getStartTime().equals(start) || newMeeting.getStartTime().isAfter(start) & newMeeting.getStartTime().isBefore(end))
                                            || (newMeeting.getEndTime().equals(end) || newMeeting.getEndTime().isBefore(end) & newMeeting.getEndTime().isAfter(start))
                                            || (newMeeting.getStartTime().isBefore(start) && newMeeting.getEndTime().isAfter(end))) {
                                        response.setCode(400);
                                        response.setMessage("The selected meeting time overlapse with another meeting that day!");
                                    } else {
                                        newMeeting.setFarmer(farmer);
                                        newMeeting.setAgronomist(agronomist);
                                        newMeeting.setState("pendant");
                                        meetingRepository.save(newMeeting);
                                        String desc= "The local agronomist Mr/Ms. "+agronomist.getLastName()+" just created a meeting for the "+ newMeeting.getDate()+ " from  "+newMeeting.getStartTime().toString().subSequence(0,5)+" untill "+newMeeting.getEndTime().toString().subSequence(0,5)+" do you want to accept or reject the proposal?";
                                        NotificationFarmer notification = new NotificationFarmer("NEW_MEETING", desc,farmer,newMeeting);
                                        notificationFarmerRepository.save(notification);
                                        response.setCode(200);
                                        response.setMessage("success");
                                        response.setResults(Collections.singleton(newMeeting));
                                    }
                                }

                            }
                        }
                    }
                }
                else{
                    response.setCode(400);
                    response.setMessage("The selected meeting date is incorrect it should have at least a day of forewarning!");
                }
            }

        }
        return response;
    }
    
    /**
     * create a new Meeting
     * @param agronomistId the agronmist that create the meeting
     * @param farmerId with witch the meeting will be done
     * @param newMeeting knowledge data to insert
     * first is checked if the agronomist exist by function findById @ see agronomistRepository @exception 404 Agronomist Not Found
     * first is checked if the farmer exist by function findById @ see farmerRepository @exception 404 Farmer Not Found
     * then is checked if the timing selected is correct:
     *                   1)start before 06:00 or end after 20:30 @exception The selected meeting time is out of working hour!
     *                   2)start before end @exception The selected meeting time is incorrect the start time should be before the end time!
     *                   3)there's no other meetings that day? meeting saved and notification to farmer saved in the db
     *                   4)there's meeting that day with overlapse timing @exception The selected meeting time overlapse with another meeting that day!
     *                   else save meeting and send notification
     *                   5) today is day before meeting day @exception The selected meeting date is incorrect it should have at least a day of forewarning!
     * @return newMeeting
     */
    public Response changeStatusMeeting(Long farmerId, Long meetingId, String state){
        Response response = new Response();
        Farmer farmer=farmerRepository.findById(farmerId).orElse(null);
        if(farmer==null)
        {
            response.setCode(404);
            response.setMessage("Farmer Not Found");
        }
        else{
            Meeting meeting= meetingRepository.findById(meetingId).orElse(null);
            if(meeting==null)
            {
                response.setCode(404);
                response.setMessage("Meeting Not Found");
            }
            else {
                if (farmerId!=meeting.getFarmer()) {
                    response.setCode(400);
                    response.setMessage("You are not allowed to confirm or reject this meet");
                } else {
                    LocalDate meetDay = meeting.getDate();
                    if (meetDay.isAfter(LocalDate.now()))
                    {
                        if(meeting.getState().equals("pendant")) {
                            if (state.equals("confirmed")) {
                                meeting.setState("confirmed");
                            } else if (state.equals("rejected")) {
                                meeting.setState("rejected");
                            }
                            meetingRepository.save(meeting);
                            Agronomist agronomist= agronomistRepository.findById(meeting.getAgronomist()).orElse(null);
                            String desc= "The farmer Mr/Ms. "+farmer.getLastName()+" just "+ meeting.getState() + " the meeting of the "+ meeting.getDate()+ " from  "+meeting.getStartTime().toString().subSequence(0,5)+" untill "+meeting.getEndTime().toString().subSequence(0,5);
                            NotificationAgronomist notification = new NotificationAgronomist("STATUS_MEETING", desc, agronomist,meeting,null);
                            notificationAgronomistRepository.save(notification);
                            response.setCode(400);
                            response.setMessage("success");
                            response.setResults(Collections.singleton(meeting));
                        }
                        else {
                            if(meeting.getState().equals("confirmed")) {
                                if (state.equals("rejected")) {
                                    meeting.setState("rejected");
                                }
                                meetingRepository.save(meeting);
                                Agronomist agronomist= agronomistRepository.findById(meeting.getAgronomist()).orElse(null);
                                String desc= "The farmer Mr/Ms. "+farmer.getLastName()+" just cancelled the meeting of the "+ meeting.getDate()+ " from  "+meeting.getStartTime().toString().subSequence(0,5)+" untill "+meeting.getEndTime().toString().subSequence(0,5);
                                NotificationAgronomist notification = new NotificationAgronomist("STATUS_MEETING", desc, agronomist,meeting,null);
                                notificationAgronomistRepository.save(notification);
                                response.setCode(400);
                                response.setMessage("success");
                                response.setResults(Collections.singleton(meeting));
                            }else {
                                response.setCode(400);
                                response.setMessage("You've already confirmed/rejected this meeting");
                            }
                        }
                    }
                    else{
                        response.setCode(400);
                        response.setMessage("It's too late to confirm or reject this meeting");
                    }
                }
            }
        }
        return response;
    }

    public Response closeMeeting(Long agronomistId, Long meetingId, Meeting evaluation){
        Response response =new Response();
        Agronomist agronomist = agronomistRepository.findById(agronomistId).orElse(null);
        if(agronomist==null)
        {
            response.setCode(404);
            response.setMessage("Farmer Not Found");
        }
        else{
            Meeting meeting= meetingRepository.findById(meetingId).orElse(null);
            if(meeting==null)
            {
                response.setCode(404);
                response.setMessage("Meeting Not Found");
            }
            else {
                if (agronomistId.equals(meeting.getAgronomist())) {
                    LocalDate meetDay = meeting.getDate();
                    if (meetDay.isBefore(LocalDate.now()) || (meetDay.equals(LocalDate.now()) & meeting.getStartTime().isBefore(LocalTime.now())))
                    {
                        if(meeting.getState().equals("conclused")) {
                            meeting.setEvaluation(evaluation.getEvaluation());
                            meeting.setNote(evaluation.getNote());
                            meeting.setState("closed");
                            meetingRepository.save(meeting);
                            Farmer farmer = farmerRepository.findById(meeting.getFarmer()).orElse(null);
                            String desc= "The local agronomist Mr/Ms. "+agronomist.getFirstName()+" just closed the meeting of the "+ meeting.getDate()+ "go take a look of the evaluation of your farm";
                            NotificationFarmer notification = new NotificationFarmer("CLOSED_MEETING", desc,farmer,null);
                            notificationFarmerRepository.save(notification);
                            response.setCode(200);
                            response.setMessage("success");
                            response.setResults(Collections.singleton(meeting));
                        }
                        else{
                            response.setCode(400);
                            response.setMessage("You already evaluate this meeting!");
                        }
                    }
                    else{
                        response.setCode(400);
                        response.setMessage("You cannot evaluate the farmer before the meeting");
                    }

                }
                else {
                    response.setCode(400);
                    response.setMessage("You are not allowed to evaluate this meet");
                }
            }
        }
        return response;
    }

    public Response deleteMeetingA(Long agronomistId, Long meetingId ){
        Response response =new Response();
        Agronomist agronomist = agronomistRepository.findById(agronomistId).orElse(null);
        if(agronomist==null)
        {
            response.setCode(404);
            response.setMessage("Farmer Not Found");
        }
        else {
            Meeting meeting = meetingRepository.findById(meetingId).orElse(null);
            if (meeting == null) {
                response.setCode(404);
                response.setMessage("Meeting Not Found");
            }else {
                if (agronomistId.equals(meeting.getAgronomist())) {
                    if(meeting.getDate().isAfter(LocalDate.now())) {
                        meetingRepository.delete(meeting);
                        Farmer farmer = farmerRepository.findById(meeting.getFarmer()).orElse(null);
                        String desc= "The local agronomist Mr/Ms. "+agronomist.getFirstName()+" just cancelled the meeting of the "+ meeting.getDate()+ "";
                        NotificationFarmer notification = new NotificationFarmer("CANCEL_MEETING", desc,farmer,null);
                        notificationFarmerRepository.save(notification);
                        response.setCode(200);
                        response.setMessage("success");
                        response.setResults(Collections.singleton(meeting));
                    }
                    else{
                        response.setCode(400);
                        response.setMessage("It's to late to delete this meeting;");
                    }
                } else {
                    response.setCode(400);
                    response.setMessage("You are not allowed to cancel this meet");
                }
            }
        }
        return response;
    }

    public Response updateMeeting(Long agronomistId, Long meetingId, Meeting meetingDTo){
        Response response=new Response();
        Agronomist agronomist=agronomistRepository.findById(agronomistId).orElse(null);
        if(agronomist==null){
            response.setCode(404);
            response.setMessage("Agronomist Not Found");
        }
        else{
            Meeting meeting=meetingRepository.findById(meetingId).orElse(null);
            if(meeting==null){
                response.setCode(404);
                response.setMessage("Meeting Not Found");
            }
            else{
                if(meeting.getDate().isAfter(LocalDate.now())) {
                    if (agronomistId.equals(meeting.getAgronomist())) {
                        if (meetingDTo.getDate().isAfter(LocalDate.now())) {
                            if (meetingDTo.getStartTime().isBefore(LocalTime.of(6, 0)) || meetingDTo.getEndTime().isAfter(LocalTime.of(20, 30))) {
                                response.setCode(400);
                                response.setMessage("The selected meeting time is out of working hour!");
                            } else {
                                if (meetingDTo.getStartTime().isAfter(meetingDTo.getEndTime())) {
                                    response.setCode(400);
                                    response.setMessage("The selected meeting time incorrect the start time should be before the end time!");
                                } else {
                                    String date = String.valueOf(meetingDTo.getDate());
                                    List<Meeting> meetings = meetingRepository.findAllByAgronomistD(agronomistId, date);
                                    if (meetings.size() == 0) {
                                        Meeting old= meeting;
                                        meeting.setState("pendant");
                                        meeting.setDate(meetingDTo.getDate());
                                        meeting.setStartTime(String.valueOf(meetingDTo.getStartTime()));
                                        meeting.setEndTime(String.valueOf(meetingDTo.getEndTime()));
                                        meeting.setNote(null);
                                        meeting.setEvaluation(0);
                                        meetingRepository.save(meeting);
                                        Farmer farmer=farmerRepository.findById(meeting.getFarmer()).orElse(null);
                                        String desc= "The local agronomist Mr/Ms. "+agronomist.getLastName()+" just changed the meeting of the "+ old.getDate()+ " and now the new meeting is the "+meeting.getDate()+" from "+meeting.getStartTime().toString().subSequence(0,5)+" untill "+meeting.getEndTime().toString().subSequence(0,5)+" do you want to accept or reject the proposal?";
                                        NotificationFarmer notification = new NotificationFarmer("UPDATE_MEETING", desc,farmer,meeting);
                                        notificationFarmerRepository.save(notification);
                                        response.setCode(200);
                                        response.setMessage("success");
                                        response.setResults(Collections.singleton(meeting));
                                    } else {
                                        for (Meeting m : meetings) {
                                            if(m!=meeting) {
                                                LocalTime start = m.getStartTime();
                                                LocalTime end = m.getEndTime();
                                                if ((meetingDTo.getStartTime().equals(start) || meetingDTo.getStartTime().isAfter(start) & meetingDTo.getStartTime().isBefore(end))
                                                        || (meetingDTo.getEndTime().equals(end) || meetingDTo.getEndTime().isBefore(end) & meetingDTo.getEndTime().isAfter(start))
                                                        || (meetingDTo.getStartTime().isBefore(start) && meetingDTo.getEndTime().isAfter(end))) {
                                                    response.setCode(400);
                                                    response.setMessage("The selected meeting time overlapse with another meeting that day!");
                                                    return response;
                                                }
                                            }
                                        }
                                        Meeting old = meeting;
                                        meeting.setState("pendant");
                                        meeting.setDate(meetingDTo.getDate());
                                        meeting.setStartTime(String.valueOf(meetingDTo.getStartTime()));
                                        meeting.setEndTime(String.valueOf(meetingDTo.getEndTime()));
                                        meeting.setNote(null);
                                        meeting.setEvaluation(0);
                                        meetingRepository.save(meeting);
                                        Farmer farmer = farmerRepository.findById(meeting.getFarmer()).orElse(null);
                                        String desc = "The local agronomist Mr/Ms. " + agronomist.getLastName() + " just changed the meeting of the " + old.getDate() + " and now the new meeting is the " + meeting.getDate() + " from " + meeting.getStartTime().toString().subSequence(0, 5) + " untill " + meeting.getEndTime().toString().subSequence(0, 5) + " do you want to accept or reject the proposal?";
                                        NotificationFarmer notification = new NotificationFarmer("UPDATE_MEETING", desc, farmer, meeting);
                                        notificationFarmerRepository.save(notification);
                                        response.setCode(200);
                                        response.setMessage("success");
                                        response.setResults(Collections.singleton(meeting));
                                    }
                                }
                            }
                        } else {
                            response.setCode(400);
                            response.setMessage("The selected meeting day is incorrect!");
                        }
                    }
                    else{
                        response.setCode(400);
                        response.setMessage("You are not allowed to update this meeting");
                    }
                }
                else{
                    response.setCode(400);
                    response.setMessage("It's too late to update this meeting");
                }
            }
        }
        return response;
    }


}
