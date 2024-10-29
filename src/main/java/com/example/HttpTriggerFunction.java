


import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.*;

import java.util.Optional;

/**
 * Azure Functions with HTTP Trigger.
 */
public class HttpTriggerFunction {
    private static final String Partition_Key = "Subscibers";
    @FunctionName("addSubscriber")
    public HttpResponseMessage get(
            @HttpTrigger(name = "postSubscriber", methods = {HttpMethod.POST}, authLevel = AuthorizationLevel.FUNCTION, route="subscribers") HttpRequestMessage<Optional<String>> request,
            @TableOutput(name="subscriber", tableName="Subscriber", connection="AzureWebJobsStorage") OutputBinding<Subscriber> subscriber,
            final ExecutionContext context) {

        context.getLogger().info("Processing request to save subscriber email.");

        // Parse the request body to get the email
        Optional<String> requestBodyOpt = request.getBody();

        // Use ifPresent to check and extract email from the request body
        if (requestBodyOpt.isPresent()) {
            String requestBody = requestBodyOpt.get();

            // Assuming the email is in a field like: {"email": "example@example.com"}
            if (requestBody.contains("email")) {
                String email = requestBody.split(":")[1].replace("\"", "").replace("}", "").trim();

                // Get the first letter of the email as RowKey
                String rowKey = email.substring(0, 1).toUpperCase();  // Convert to uppercase for uniformity

                Subscriber subscriberEntity = new Subscriber(Partition_Key, rowKey + "-" + email, email);
                // Add the entity to the output binding
                subscriber.setValue(subscriberEntity);
                return request.createResponseBuilder(HttpStatus.OK)
                        .body("Email saved successfully.")
                        .build();
            } else {
                return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                        .body("Invalid request. No email field found.")
                        .build();
            }
        } else {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                    .body("Request body is missing.")
                    .build();
        }
    }
}
