package ru.cource.accounting.controller;


import com.google.common.io.Files;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = "*")
@Controller
@RequestMapping("/api/logs")
@RequiredArgsConstructor

public class LogsController {

    @GetMapping("/download")
    public ResponseEntity<ByteArrayResource> getLogs() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        File file = new File("C:\\Program Files\\postgreSQL\\13\\data\\log_directory_new2\\postgresql-" + formatter.format(date) + ".log");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "force-download"));
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=logs.log");
        try {
            ByteArrayResource byteArrayResource = new ByteArrayResource(Files.toByteArray(file));
            return new ResponseEntity<>(byteArrayResource, headers, HttpStatus.CREATED);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
