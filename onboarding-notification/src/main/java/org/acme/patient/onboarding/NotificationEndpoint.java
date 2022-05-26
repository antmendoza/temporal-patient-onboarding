package org.acme.patient.onboarding;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@ApplicationScoped
@Path("notification")
@Tag(name = "NotificationEndpoint Services ")
public class NotificationEndpoint {


    @POST
    @Path("notify")
    public synchronized void notifyPatient(String contact) {
        System.out.println("do something... " + contact);
    }

}

