'use strict';
const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);


const allowedYearPrefix = 16;
//Restricting events
const restrictEventId = ["-KVF7zZfBsmo58drIGWa", "4434435fddv"];
/*
 * Restricts registration 
*/ 
exports.moderator = functions.database
    .ref('/events/{eventId}/registers/{userId}').onCreate(event => {
      const user = event.data.val();
      const regno = user.regno;
      const root = event.data.ref.root;
      const eventid = event.params.eventId;
      const userid = event.params.userId;
      const yearprefix = parseInt(regno.substring(0,2));
      let isEventRestricted = false;
    
      //Check if the eventid is restricted by Iterating the restrictEventIdArray If 
      for (var i = 0 ; i < restrictEventId.length; i++)
        if(restrictEventId[i] == eventid){
        isEventRestricted = true;
        break;
      }

      console.log("is event restricted ",isEventRestricted)  
      if(yearprefix != allowedYearPrefix && isEventRestricted)
      {
        return event.data.adminRef.remove().then(function() {
          console.log("Remove succeeded.");
          console.log("Event id ",eventid);
          //Now decrement the counter
          return root.child(`/events/${eventid}/registerCount`).once('value').then(snap =>{
              const regCount = parseInt(snap.val())-1;
              console.log("New count value ",regCount);
              return root.child(`/events/${eventid}`).update({
                registerCount: regCount,
              }).then(function(){
                /*
                Sends a notification Informing his registration is not successful
                -First Get the event name !
                -Get the FCM token from fcm-token/{userid}
                -Send Notification
                */
               return root.child(`/events/${eventid}/title`).once('value').then(snap =>{
                 const eventName = snap.val();
                 return root.child(`/fcm-tokens/${userid}/token`).once('value').then(snap =>{
                 //Get token Value  
                 const token = snap.val();
                 console.log('FCM token ',token);
                 //Generate Payload
                 const payload = {
                  data: {
                      textMessage:"True",
                      notificationIcon: "https://safebytes.com/wp-content/uploads/2017/03/warning_exclamation.png",
                      message: "Registration open only for 2nd years",
                      title: eventName+" Reg Failed"
                    }
                };
                 //Send Notification
                return admin.messaging().sendToDevice(token, payload)
                 .then(function (response) {
                     console.log("Successfully sent message:", response);
                 })
                 .catch(function (error) {
                     console.log("Error sending message:", error);
                  });   
                 })
               })  
              })
            })  
            })
        .catch(function(error) {
          console.log("Remove failed: " + error.message)
        });
      }
      
});

// exports.unregisterFromEvent = functions.database.ref('/events/{eventId}/registers/{userId}')
// .onDelete(event => {
//   const user = event.data.previous.val();
//   const regno = user.regno;
//   const root = event.data.ref.root;
//   const eventid = event.params.eventId;
//   const userid = event.params.userId;
//   var adaRef = admin.database().ref(`/my-events/${userid}/${eventid}`);
//   return adaRef.remove()
//   .then(function() {
//     console.log("Remove succeeded.")
//   })
//   .catch(function(error) {
//     console.log("Remove failed: " + error.message)
//   });

// });