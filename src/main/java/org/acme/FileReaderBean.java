package org.acme;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileReaderBean {
    
    private final Logger log = LoggerFactory.getLogger(FileReaderBean.class);
    
    public static String getServerDirURI() throws URISyntaxException {
        return Paths.get(FileReaderBean.class.getResource("/")
                    .toURI()).getParent() + "/camel-file-rest-dir";
    }

    public void listFile(Exchange exchange) throws URISyntaxException {
        File serverDir = new File(getServerDirURI());
        if(serverDir.exists()) {
            List<String> fileList = new ArrayList<>();
            for(File file : serverDir.listFiles()) {
                fileList.add(file.getName());
            }

            if(!fileList.isEmpty()) {
                log.info("### SETTING LIST OF FILES");
                exchange.getMessage().setBody(fileList);
            } else {
                log.info("### NO FILES FOUND");
            }
        } else {
            log.info("### NO FILES CREATED YET");
        }
    }
}
