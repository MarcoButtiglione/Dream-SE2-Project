package com.se2project.dream.controller;


import com.se2project.dream.extraClasses.Response;
import com.se2project.dream.service.LoginService;
import com.se2project.dream.service.NotificationAgronomistService;
import com.se2project.dream.service.NotificationFarmerService;
import org.springframework.web.bind.annotation.*;
@RequestMapping("/api")
@CrossOrigin
@RestController
public class NotificationController {
    private final NotificationFarmerService notificationFarmerService;
    private final NotificationAgronomistService notificationAgronomistService;
    private final LoginService loginService;

    public NotificationController(NotificationFarmerService notificationFarmerService, NotificationAgronomistService notificationAgronomistService, LoginService loginService) {
        this.notificationFarmerService = notificationFarmerService;
        this.notificationAgronomistService = notificationAgronomistService;
        this.loginService = loginService;
    }

    /**
     * Get all notification of the logged Agronomist
     * @param tokenA agronomist authentication token generated on login
     * first it extrapolates the id from the token by the function @see getIdFromTokenA() in loginService
     * then with the function getAllNotification @see notificationAgronomistService it get all the notification
     * @return set of notifications
     * @exception 404 Agronomist Not Found
     * @exception 404 No Notification Found
     * */
    @GetMapping("/agronomist/GetAllNotificationA/")
    Response getAllNotificationA(@RequestHeader("Authorization") String tokenA) {
        Long agronomistId =loginService.getIdFromTokenA(tokenA);
        return notificationAgronomistService.getAllNotification(agronomistId);
    }

    /**
     * Get all notification of the logged Farmer
     * @param tokenF farmer authentication token generated on login
     * first it extrapolates the id from the token by the function @see getIdFromTokenF() in loginService
     * then with the function getAllNotification @see notificationFarmerService it get all the notification
     * @return set of notifications
     * @exception 404 Farmer Not Found
     * @exception 404 No Notification Found
     * */
    @GetMapping("/farmer/GetAllNotificationF/")
    Response getAllNotificationF(@RequestHeader("Authorization") String tokenF) {
        Long farmerId =loginService.getIdFromTokenF(tokenF);
        return notificationFarmerService.getAllNotification(farmerId);
    }

    /**
     * function that delete a notification when the logged Farmer see it
     * @param tokenA farmer authentication token generated on login
     * first it extrapolates the id from the token by the function @see getIdFromTokenF() in loginService
     * then with the function deleteNotification @see notificationAgronomistService it delete the notification
     * @return notificationDeleted
     * @exception 404 Agronomist Not Found
     * @exception 404 Notification Not Found
     * @exception 400 You cannot delete this notification
     * */
    @GetMapping("/agronomist/DeleteNotificationA/{notificationId}")
    Response deleteNotificationA(@PathVariable Long notificationId,@RequestHeader("Authorization") String tokenA){
        Long agronomistId =loginService.getIdFromTokenA(tokenA);
        return notificationAgronomistService.deleteNotification(notificationId, agronomistId);
    }

    /**
     * function that delete a notification when the logged Farmer see it
     * @param tokenF farmer authentication token generated on login
     * first it extrapolates the id from the token by the function @see getIdFromTokenF() in loginService
     * then with the function deleteNotification @see notificationFarmerService it delete the notification
     * @return notificationDeleted
     * @exception 404 Farmer Not Found
     * @exception 404 Notification Not Found
     * @exception 400 You cannot delete this notification
     * */
    @GetMapping("/farmer/DeleteNotificationF/{notificationId}")
    Response deleteNotificationF(@PathVariable Long notificationId, @RequestHeader("Authorization") String tokenF){
        Long farmerId =loginService.getIdFromTokenF(tokenF);
        return notificationFarmerService.deleteNotification(notificationId, farmerId);
    }
}
