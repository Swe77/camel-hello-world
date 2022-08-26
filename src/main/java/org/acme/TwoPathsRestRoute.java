package org.acme;

import org.apache.camel.builder.RouteBuilder;

public class TwoPathsRestRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("platform-http:/twoPaths/helloWorld?httpMethodRestrict=GET")
        .routeId("two-paths-hello")
        .setBody(constant("Hello World"))
        .log("### REQUEST RESPONDED WITH BODY: ${body}");

        from("platform-http:/twoPaths/sayHi?httpMethodRestrict=GET")
        .routeId("two-paths-hi")
        .setBody(constant("HI"))
        .log("### REQUEST RESPONDED WITH BODY: ${body}");
    }
    
}
