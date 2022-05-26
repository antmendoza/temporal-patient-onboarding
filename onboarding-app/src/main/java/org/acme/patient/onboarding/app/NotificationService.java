package org.acme.patient.onboarding.app;

import io.temporal.activity.ActivityInterface;
import org.acme.patient.onboarding.model.Doctor;
import org.acme.patient.onboarding.model.Hospital;

@ActivityInterface
public interface NotificationService {
    void notifyViaEmail(String email);
    void notifyViaText(String number);
}
