package org.acme.patient.onboarding;

import org.acme.patient.onboarding.model.Patient;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@ApplicationScoped
@Path("/onboard")
@Tag(name = "New Patient Onboarding Endpoint")
public class OnboardingResource {

    @Inject
    WorkflowClient workflowClient;

    @POST
    public Patient doOnboard(Patient patient) {
        return workflowClient.onboardNewPatient(patient);
    }

    @GET
    public String getStatus(@QueryParam("id") String patientId) {
        // query workflow to get the status message
        try {
            return workflowClient.getStatus(patientId);
        } catch (Exception e) {
            e.printStackTrace();
            return "Unable to query workflow with id: " + patientId;
        }
    }

}
