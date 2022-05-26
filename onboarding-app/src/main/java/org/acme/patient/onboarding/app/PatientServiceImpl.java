package org.acme.patient.onboarding.app;

import org.acme.patient.onboarding.model.Doctor;
import org.acme.patient.onboarding.model.Hospital;
import org.acme.patient.onboarding.utils.OnboardingServiceClient;

public class PatientServiceImpl implements PatientService {

    private OnboardingServiceClient serviceClient;

    public PatientServiceImpl(
            OnboardingServiceClient serviceClient
    ) {
        this.serviceClient = serviceClient;
    }

    @Override
    public Hospital assignHospitalToPatient(String zip) {
        // call onboarding service
        Hospital hospital = serviceClient.assignHospitalToPatient(zip);
        // simulate some work...
        sleep(5);
        return hospital;
    }

    @Override
    public Doctor assignDoctorToPatient(String condition) {
        Doctor doctor = serviceClient.assignDoctorToPatient(condition);
        // simulate some work...
        sleep(5);
        return doctor;
    }


    @Override
    public String finalizeOnboarding() {
        // simulate some work...
        sleep(5);
        return "yes";
    }

    private void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException ee) {
            // Empty
        }
    }
}
