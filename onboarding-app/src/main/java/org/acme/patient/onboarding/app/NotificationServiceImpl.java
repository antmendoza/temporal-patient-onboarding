package org.acme.patient.onboarding.app;

import org.acme.patient.onboarding.utils.NotificationServiceClient;

public class NotificationServiceImpl implements NotificationService {




    private final  NotificationServiceClient notificationServiceClient;

    public NotificationServiceImpl(
            final NotificationServiceClient notificationServiceClient
    ) {this.notificationServiceClient = notificationServiceClient;}



    @Override
    public void notifyViaEmail(String email) {
        notificationServiceClient.notifyPatient(email);
    }

    @Override
    public void notifyViaText(String phone) {
        notificationServiceClient.notifyPatient(phone);
    }

}
