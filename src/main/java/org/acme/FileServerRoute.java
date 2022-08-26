package org.acme;

import java.net.URISyntaxException;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

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

    private void createSaveFileRoute() throws URISyntaxException {
        from (DIRECT_SAVE_FILE)
        .routeId("save-file")
        .setHeader(Exchange.FILE_NAME, simple("${header.fileName}"))
        .to("file:" + FileReaderBean.getServerDirURI())
        .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(201))
        .setHeader(Exchange.CONTENT_TYPE, constant(MEDIA_TYPE_TEXT_PLAIN))
        .setBody(constant(CODE_201_MESSAGE));
        
    }

    private void createGetFilesRoute() {
        from(DIRECT_GET_FILES)
        .routeId("get-files")
        .log("### GETTING FILES LIST")
        .bean(FileReaderBean.class, "listFile")
        .choice()
        .when(simple("${body} != null"))
            .marshal().json(JsonLibrary.Jsonb);
    }

    private void createFileServerRoutes() {
    }

}
