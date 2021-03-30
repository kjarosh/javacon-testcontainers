package io.github.kjarosh.javacon.testcontainers.service.rest;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Kamil Jarosz
 */
@RestController
public class HealthCheckController {
    @RequestMapping("/healthcheck")
    @ResponseBody
    public String healthCheck() {
        return "OK";
    }
}
