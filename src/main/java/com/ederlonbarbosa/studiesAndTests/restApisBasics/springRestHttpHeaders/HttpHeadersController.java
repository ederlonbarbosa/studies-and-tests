package com.ederlonbarbosa.studiesAndTests.restApisBasics.springRestHttpHeaders;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.InetSocketAddress;
import java.util.Map;

/**
 * This code was made based on: https://www.baeldung.com/spring-rest-http-headers
 */
@Controller
@RequestMapping("/api/v1")
public class HttpHeadersController {

    /**
     * this api returns for example:
     * pt-BR,pt;q=0.9,en-US;q=0.8,en;q=0.7
     *
     * @param language
     * @return
     */
    @GetMapping("/greeting")
    public ResponseEntity<String> greeting(@RequestHeader("accept-language") String language) {
        return new ResponseEntity<>(language, HttpStatus.OK);
    }

    /**
     * this api returns for example:
     * 10 * 2 = 20
     *
     * @param myNumber
     * @return
     */
    @GetMapping("/double")
    public ResponseEntity<String> doubleNumber(@RequestHeader("my-number") int myNumber) {
        return new ResponseEntity<>(String.format("%d * 2 = %d",
                myNumber, (myNumber * 2)), HttpStatus.OK);
    }

    /**
     * this api returns for example:
     * {
     * "my-number": "10",
     * "accept-language": "pt-BR",
     * "user-agent": "PostmanRuntime/7.19.0",
     * "cache-control":"no-cache",
     * "postman-token":"263615dd-b044-4b8d-b15c-db63ca2326d1",
     * "host":"localhost:8080",
     * "accept-encoding":"gzip, deflate",
     * "cookie":"JSESSIONID=05DFB92008E3BE2AC50CBD0C9CFA439D; XSRF-TOKEN=c01e7a0d-72ab-4843-a74e-b04311231cd7",
     * "connection":"keep-alive"
     * }
     *
     * @param headers
     * @return
     */
    @GetMapping("/listHeaders")
    public ResponseEntity<Map<String, String>> listAllHeaders(@RequestHeader Map<String, String> headers) {
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    /**
     * this api print:
     * {my-number=[10], accept-language=[pt-BR], user-agent=[PostmanRuntime/7.19.0], accept=[**],cache-control=[no-cache],postman-token=[ea32d97d-ff88-45c6-9ee4-4569d594af29],host=[localhost:8080],accept-encoding=[gzip,deflate],cookie=[JSESSIONID=05DFB92008E3BE2AC50CBD0C9CFA439D;XSRF-TOKEN=c01e7a0d-72ab-4843-a74e-b04311231cd7],connection=[keep-alive]}
     * and returns:
     * 10
     *
     * @param headers
     * @return
     */
    @GetMapping("/multiValue")
    public ResponseEntity<Integer> multiValue(
            @RequestHeader MultiValueMap<String, String> headers) {
        System.out.println(headers);
        return new ResponseEntity<>(headers.size(), HttpStatus.OK);
    }

    /**
     * this api returns for example:
     * Base URL = http://localhost:8080
     *
     * @param headers
     * @return
     */
    @GetMapping("/getBaseUrl")
    public ResponseEntity<String> getBaseUrl(@RequestHeader HttpHeaders headers) {
        InetSocketAddress host = headers.getHost();
        String url = "http://" + host.getHostName() + ":" + host.getPort();
        return new ResponseEntity<>(String.format("Base URL = %s", url), HttpStatus.OK);
    }

    /**
     * this api return:
     * Was the optional header present? No!
     * or
     * Was the optional header present? Yes!
     *
     * @param optionalHeader
     * @return
     */
    @GetMapping("/nonRequiredHeader")
    public ResponseEntity<String> evaluateNonRequiredHeader(
            @RequestHeader(value = "optional-header", required = false) String optionalHeader) {
        return new ResponseEntity<>(String.format(
                "Was the optional header present? %s!",
                (optionalHeader == null ? "No" : "Yes")), HttpStatus.OK);
    }

    /**
     * this api returns for example:
     * Optional Header is 3600
     * or
     * Optional Header is 123
     *
     * @param optionalHeader
     * @return
     */
    @GetMapping("/default")
    public ResponseEntity<String> evaluateDefaultHeaderValue(
            @RequestHeader(value = "optional-header", defaultValue = "3600") int optionalHeader) {
        return new ResponseEntity<>(
                String.format("Optional Header is %d", optionalHeader), HttpStatus.OK);
    }
}
