package org.acme.patient.onboarding;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import org.acme.patient.onboarding.app.Onboarding;
import org.acme.patient.onboarding.app.OnboardingImpl;
import org.acme.patient.onboarding.app.ServiceExecutorImpl;
import org.acme.patient.onboarding.model.Patient;
import org.acme.patient.onboarding.utils.NotificationServiceClient;
import org.acme.patient.onboarding.utils.OnboardingServiceClient;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class WorkflowClient {

    @ConfigProperty(name = "onboarding.task.queue")
    String taskQueue;
    @Inject
    @RestClient
    OnboardingServiceClient serviceClient;
    @Inject
    @RestClient
    NotificationServiceClient notificationServiceClient;
    private io.temporal.client.WorkflowClient client;
    private WorkerFactory factory;

    void onStart(@Observes StartupEvent ev) {
        WorkflowServiceStubs service = WorkflowServiceStubs.newInstance();
        client = io.temporal.client.WorkflowClient.newInstance(service);
        factory = WorkerFactory.newInstance(client);
        Worker worker = factory.newWorker(taskQueue);

        worker.registerWorkflowImplementationTypes(OnboardingImpl.class);
        worker.registerActivitiesImplementations(new ServiceExecutorImpl(serviceClient, notificationServiceClient));

        factory.start();
    }

    void onStop(@Observes ShutdownEvent ev) {
        factory.shutdown();
    }

    private io.temporal.client.WorkflowClient getClient() {
        return client;
    }


    public Patient onboardNewPatient(final Patient patient) {
        return this.getClient()
                .newWorkflowStub(Onboarding.class, WorkflowOptions.newBuilder()
                        .setWorkflowId(patient.getId())
                        .setTaskQueue(taskQueue)
                        .build())
                .onboardNewPatient(patient);
    }

    public String getStatus(String patientId) {

        return this.getClient()
                .newWorkflowStub(Onboarding.class, patientId)
                .getStatus();
    }

}
