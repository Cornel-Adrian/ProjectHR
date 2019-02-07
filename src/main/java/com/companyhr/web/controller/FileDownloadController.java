package com.companyhr.web.controller;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.charset.Charset;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class FileDownloadController{

    private static final String INTERNAL_FILE="its-all-connected.jpg";
    private static final String EXTERNAL_FILE_PATH="/home/securiter/Downloads/Proiect/app/src/main/resources/its-all-connected.jpg";

    /*
     * Download a file from
     *   - inside project, located in resources folder.
     *   - outside project, located in File system somewhere.
     */
    @RequestMapping(value="/download/{type}", method = RequestMethod.GET)
    public void downloadFile(HttpServletResponse response, @PathVariable("type") String type) throws IOException {

        File file = null;
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            if(classloader.getResource(INTERNAL_FILE).getFile() != null){
                file = new File(classloader.getResource(INTERNAL_FILE).getFile());
            }else{
                file = new File(EXTERNAL_FILE_PATH);
            }

        if(!file.exists()){
            String errorMessage = "Sorry. The file you are looking for does not exist";
            System.out.println(errorMessage);
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
            outputStream.close();
            return;
        }

        String mimeType= URLConnection.guessContentTypeFromName(file.getName());
        if(mimeType==null){
            System.out.println("mimetype is not detectable, will take default");
            mimeType = "application/octet-stream";
        }


        System.out.println("mimetype : "+mimeType);

        response.setContentType(mimeType);

        if(type.equalsIgnoreCase("download")){
            System.out.println("Download path.");
            response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + file.getName() +"\""));
        }else{
            System.out.println("Browser path.");
            response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() +"\""));
        }
        response.setContentLength((int)file.length());
        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        FileCopyUtils.copy(inputStream, response.getOutputStream());
    }

}
