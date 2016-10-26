package com.eventer.app.Event;


import com.eventer.app.model.Event;
import com.eventer.app.model.User;
import com.eventer.app.ui.GroupDialogBox;


/**
 * Created by gaurav on 25/10/16.
 */
public class EventRegistrationSystem {
    void setUpRegistration(String eid,Event mEvent,String uid,User userAdmin,Boolean userIsRegister) {

        if(!userIsRegister) {
            if (mEvent.maxReg == 1 && mEvent.minReg == 1) {
                // not a group event


            } else if (mEvent.maxReg == mEvent.minReg) {
                GroupDialogBox groupDialogBox = new GroupDialogBox();
                // group event with fixed no of registration in each group

            } else {
                GroupDialogBox groupDialogBox = new GroupDialogBox();
                // group event with variable no of registration in each group

            }
        }
        else
        {
            //if user is registered and admin
        }

    }
}
