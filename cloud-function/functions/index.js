'use strict';
const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);


const allowedYearPrefix = 16;
//Restricting events
const restrictEventId = ["-workshop4-", "-workshop3-","-workshop2-","-workshop1-","-Python-","-e2-"];
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

/*Broadcasts notification to participants
* The organizers message is sent to all the particapants 
- STEPS
* Get Event logo url
* Get reg user keys
* Get FCM token for all the keys
* Send FCM message to all participants 
*/
exports.brodcastEventNotifications = functions.database.ref('/broadcast/{broadcastId}')
.onCreate(event => {

  console.log("Broadcast triggered");
  const broadcast = event.data.val();
  const broadcastEvent = broadcast.event;
  const broadcastTitle = broadcast.title;
  const broadcastMessage = broadcast.message;

  const root = event.data.ref.root;

  return root.child(`/events/${broadcastEvent}/logoURL`).once('value').then(snap => {
    const logoURL = snap.val();
    return root.child(`/events/${broadcastEvent}/title`).once('value').then(snap =>{
    const eventName = snap.val(); 
      return root.child(`/events/${broadcastEvent}/registers`).once('value').then(snap =>{
        //Array to store all the user keys
      let userKeys = [];
      if(snap.exists())//Okay so there are particiants lets get the userKeys
      {
          snap.forEach((child) => {
          console.log("Broadcast user keys :", child.key); 
          userKeys.push(child.key);
          }); 
          
          //Time to get fcm token Use Promise ALL (its neater)
          let fcmTokens = [];
          for(var i = 0; i < userKeys.length; i++)
            fcmTokens.push(root.child(`/fcm-tokens/${userKeys[i]}/token`).once('value'))
          
          return Promise.all(fcmTokens).then(results => {
            if(results.length == 0)//No tokens found :(
            return;
            else
            {
              //Final FCM token might be less than the actual no of user key because two user may have the sam etoken
              let finalfcmTokens = [];
              for (var j = 0; j < results.length ; j++)
              {
                if(results[j].exists())//Check if FCM token exists for corresponding userKey
                {
                  finalfcmTokens.push(results[j].val());
                  console.log("FCm tokens final ",results[j].val());  
                }
               
              }
              //Time tosend notification but first create a payload
              const payload = {
                data: {
                    textMessage:"True",
                    notificationIcon: logoURL,
                    message: "Foobar 1.0 is for us, the Computer Science & Engineering students to showcase our talents in our core fields as well as other parallels. Although open to all departments of CUFE and to the Management students on the Kengeri Campus, it will serve as a platform for the students of Computer Science across deaneries come together and explore horizons.",
                    title: broadcastTitle
                  }
              };
              //Sends Notification Tada
              return admin.messaging().sendToDevice(finalfcmTokens, payload)
              .then(function (response) {
                  console.log("Successfully sent message:", response);
              })
              .catch(function (error) {
                  console.log("Error sending message:", error);
               });
            }
          })  
          
      }


      })
    })
  });

});