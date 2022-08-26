package org.acme;

import org.apache.camel.builder.RouteBuilder;

public class HelloWorldRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("rest:get:hello/{me}")
        .transform()
        .simple("### HELLO ${header.me}")
        .log("### REQUEST RESPONDED WITH BODY: ${body}");
        


        // rest("/helloWorld")
        //         .get()
        //         .route()
        //         .routeId("rest-hello-world")
        //         .setBody(constant("Hello World \n"))
        //         .log("Request responded with body: ${body}")
        //         .endRest();
    }

}