package org.acme;

import org.apache.camel.builder.RouteBuilder;

public class FileServerRoute extends RouteBuilder {

    public static final String MEDIA_TYPE_APP_JSON = "application/json";
    public static final String MEDIA_TYPE_TEXT_PLAIN = "text/plain";
    public static final String CODE_201_MESSAGE = "BAUAU 201";
    public static final String CODE_204_MESSAGE = "BAUAU 204";
    public static final String CODE_500_MESSAGE = "BAUAU 500";
    public static final String DIRECT_GET_FILES = "DIRECT_GET_FILES";
    public static final String DIRECT_SAVE_FILE = "direct:save-file";

    @Override
    public void configure() throws Exception {
        createFileServerApiDefinition();
        createFileServerRoutes();
    }

    private void createFileServerApiDefinition() {
        restConfiguration()
                .apiContextPath("/fileServer/doc")
                .apiProperty("api.title", "File Server API")
                .apiProperty("api.version", "1.0.0")
                .apiProperty("api.description", "REST API to save files");
        rest("/fileServer")
            .get("/file")
            .id("get-files")
            .description("Generates a list of saved files")
            .produces(MEDIA_TYPE_APP_JSON)
            .responseMessage().code(200).endResponseMessage()
            .responseMessage().code(204)
            .message(CODE_204_MESSAGE).endResponseMessage()
            .responseMessage().code(500)
            .message(CODE_500_MESSAGE).endResponseMessage()
            .to(DIRECT_GET_FILES)
        .post("/file")
            .id("save-file")
            .description(
                    "Saves the HTTP Request body into a File, using " +
                    "the fileName header to set the file name. ")
            .consumes(MEDIA_TYPE_TEXT_PLAIN)
            .produces(MEDIA_TYPE_TEXT_PLAIN)
            .responseMessage().code(201)
        .message(CODE_201_MESSAGE).endResponseMessage()
            .responseMessage().code(500)
        .message(CODE_500_MESSAGE).endResponseMessage()
            .to(DIRECT_SAVE_FILE);
    }

    private void createFileServerRoutes() {
    }

}
