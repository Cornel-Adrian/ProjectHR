package com.companyhr.web.controller;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.companyhr.controller.CsvOutputHandler;
import com.companyhr.model.DaysOff;
import com.companyhr.model.EmployeeCredentials;
import com.companyhr.repository.DaysOffRepository;
import com.companyhr.repository.EmployeeCredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class FileDownloadController{


    @Autowired
    DaysOffRepository daysOffRepository;

    @Autowired
    EmployeeCredentialsRepository employeeCredentialsRepository;

    private static final String INTERNAL_FILE="output.csv";
    private static final String EXTERNAL_FILE_PATH="/home/securiter/Downloads/Proiect/app/src/main/resources/its-all-connected.jpg";

    /*
     * Download a file from
     *   - inside project, located in resources folder.
     *   - outside project, located in File system somewhere.
     */
    @RequestMapping(value="/download/{type}", method = RequestMethod.GET)
    public void downloadFile(HttpServletResponse response, @PathVariable("type") String type) throws IOException {
        List<DaysOff> listOfdays=daysOffRepository.findAll();
        List<EmployeeCredentials> credy=employeeCredentialsRepository.findAll();

        CsvOutputHandler Coh=new CsvOutputHandler();
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        File file = null;


            if(classloader.getResource(INTERNAL_FILE).getFile() != null){
                file = new File(classloader.getResource(INTERNAL_FILE).getFile());
            }else{
                file = new File(EXTERNAL_FILE_PATH);
            }

        if(type.equalsIgnoreCase("download")){
            System.out.println("Download path.");
            response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + file.getName() +"\""));
            Coh.writeCsvFile(file.getAbsolutePath(), listOfdays, credy);
        }else{
            file = new File(EXTERNAL_FILE_PATH);
            System.out.println("Browser path.");
            response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() +"\""));
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

        response.setContentLength((int)file.length());
        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        FileCopyUtils.copy(inputStream, response.getOutputStream());
    }

}
