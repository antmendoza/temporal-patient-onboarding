package org.acme.patient.onboarding.app;

import org.acme.patient.onboarding.model.Patient;
import org.acme.patient.onboarding.utils.ActivityStubUtils;

public class OnboardingImpl implements Onboarding {


    PatientService patientService = ActivityStubUtils.getActivitiesStub(PatientService.class);
    NotificationService notificationService = ActivityStubUtils.getActivitiesStub(NotificationService.class);

    String status;
    Patient onboardingPatient;

    @Override
    public Patient onboardNewPatient(Patient patient) {
        onboardingPatient = patient;

        try {
            // 1. assign hospital to patient
            status = "Assigning hospital to patient: " + onboardingPatient.getName();
            onboardingPatient.setHospital(
                    patientService.assignHospitalToPatient(onboardingPatient.getZip()));

            // 2. assign doctor to patient
            status = "Assigning doctor to patient: " + onboardingPatient.getName();
            onboardingPatient.setDoctor(
                    patientService.assignDoctorToPatient(onboardingPatient.getCondition()));

            // 3. notify patient with preferred contact method
            status = "Notifying patient: " + onboardingPatient.getName();
            switch (onboardingPatient.getContactMethod()) {
                case PHONE:
                    notificationService.notifyViaEmail(onboardingPatient.getEmail());
                    break;

                case TEXT:
                    notificationService.notifyViaText(onboardingPatient.getPhone());
                    break;
            }

            // 4. finalize onboarding
            status = "Finalizing onboarding for: " + onboardingPatient.getName();
            patient.setOnboarded(
                    patientService.finalizeOnboarding());

        } catch (Exception e) {
            patient.setOnboarded("no");
        }

        return onboardingPatient;
    }

    @Override
    public String getStatus() {
        return status;
    }

}
