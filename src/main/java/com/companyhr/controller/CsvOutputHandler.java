package com.companyhr.controller;

import com.companyhr.model.DaysOff;
import com.companyhr.model.EmployeeCredentials;
import com.companyhr.repository.DaysOffRepository;
import com.companyhr.repository.EmployeeCredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


public class CsvOutputHandler {
    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";
    private static final String FILE_HEADER = "employee_id,employee_name,start_date,end_date,reason";

    public void writeCsvFile(String fileName, List<DaysOff> listy, List<EmployeeCredentials> credy) {
        FileWriter fileWriter = null;
        System.out.println(fileName);

        try {
            fileWriter = new FileWriter(fileName);
            fileWriter.append(FILE_HEADER.toString());
            fileWriter.append(NEW_LINE_SEPARATOR);
            System.out.println(listy);
            System.out.println(credy);
            //Write a new student object list to the CSV file
            for (DaysOff dayOff : listy) {
                Long id=dayOff.getEmployeeId();

                EmployeeCredentials empy=credy.stream()
                        .filter(emCred -> id.equals(emCred.getId()))
                        .findAny().orElse(null);

                if(id != null) {
                    fileWriter.append(String.valueOf(id));
                    fileWriter.append(COMMA_DELIMITER);
                    if (empy != null) {
                        fileWriter.append(empy.getUsername());
                        fileWriter.append(COMMA_DELIMITER);
                    } else {
                        fileWriter.append("null");
                        fileWriter.append(COMMA_DELIMITER);
                    }

                    fileWriter.append(String.valueOf(dayOff.getStartDate()));
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(String.valueOf(dayOff.getEndDate()));
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(String.valueOf(dayOff.getReasonLeave()));
                    fileWriter.append(NEW_LINE_SEPARATOR);
                }else{
                    throw new Exception();
                }
            }
            System.out.println("CSV file was created successfully !!!");

        } catch (Exception e) {
            System.out.println("Error in CsvFileWriter !!!");
            e.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                System.out.println("Error while flushing/closing fileWriter !!!");
                e.printStackTrace();
            }

        }
    }


}
