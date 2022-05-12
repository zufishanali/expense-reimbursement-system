package com.revature.reimbursementApi.services;

import com.revature.reimbursementApi.dtos.RequestEmailDTO;

import lombok.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;


/**
 * RestTemplate Example:
 *      https://attacomsian.com/blog/http-requests-resttemplate-spring-boot
 */


@Service
public class RequestEmailService {

    final Logger logger = LoggerFactory.getLogger(RequestEmailService.class);

    // @Value("${EMAIL_URL}")
    // private String emailUrl;

    public void createEmailRequest(RequestEmailDTO emailRequest){
        logger.info("Creating email request.");

        RestTemplate restTemplate = new RestTemplate();


        String url = "http://localhost:8587/sendEmail";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<RequestEmailDTO> entity = new HttpEntity<>(emailRequest, headers);

        logger.info("Request entity being sent: " + entity);

        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

        if(response.getStatusCode() == HttpStatus.OK) {
            logger.info(response.getBody());
        } else {
            logger.warn("Failure to send email.");
        }
    }
}
