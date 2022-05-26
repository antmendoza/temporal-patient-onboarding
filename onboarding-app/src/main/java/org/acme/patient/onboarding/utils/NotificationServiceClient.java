package org.acme.patient.onboarding.utils;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("notification")
@RegisterRestClient
public interface NotificationServiceClient {


    @POST
    @Path("notify")
    void notifyPatient(String contact);

}
