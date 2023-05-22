package tech.devopscontinens.sofarpcsofatracer.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class SampleRestController {
    private static final String TEMPLATE = "Hello, %s!";

    private final AtomicLong    counter  = new AtomicLong();

    /***
     * http://localhost:8080/zipkin
     * @param name name
     * @return map
     */
    @RequestMapping("/helloZipkin")
    public Map<String, Object> zipkin(@RequestParam(value = "name", defaultValue = "SOFATracer Zipkin Remote Report") String name) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("success", true);
        resultMap.put("id", counter.incrementAndGet());
        resultMap.put("content", String.format(TEMPLATE, name));
        return resultMap;
    }
}
