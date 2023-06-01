package tech.devopscontinens.sofarpcsofatracer.controller;

import com.alipay.common.tracer.core.async.SofaTracerRunnable;
import com.alipay.common.tracer.core.context.span.SofaTracerSpanContext;
import com.alipay.common.tracer.core.context.trace.SofaTraceContext;
import com.alipay.common.tracer.core.holder.SofaTraceContextHolder;
import com.alipay.common.tracer.core.span.SofaTracerSpan;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class SampleRestController {

    private static final Logger logger = LogManager.getLogger("SampleRestController.class");
    private static final String TEMPLATE = "Hello, %s!";

    private final AtomicLong    counter  = new AtomicLong();

    /***
     * http://localhost:8080/zipkin
     * @param name name
     * @return map
     */
    @RequestMapping("/helloZipkin")
    public Map<String, Object> zipkin(@RequestParam(value = "name", defaultValue = "SOFATracer Zipkin Remote Report") String name) {


        SofaTraceContext ctx = SofaTraceContextHolder.getSofaTraceContext();
        SofaTracerSpan span = ctx.getCurrentSpan();
        if (span != null) {
            span.setTag("name", name);
        }

        SofaTracerSpanContext sofaTracerSpanContext = span.getSofaTracerSpanContext();
        String traceId = sofaTracerSpanContext.getTraceId();
        String spanId = sofaTracerSpanContext.getSpanId();

//        System.out.println("Span information traceId - " + traceId + " spanId - " + spanId);
//        ThreadContext.put("SOFA-TraceId", "123456");
        logger.info("MDC information");
//        String traceId = TracerContextUtil.getTraceId();

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("success", true);
        resultMap.put("id", counter.incrementAndGet());
        resultMap.put("content", String.format(TEMPLATE, name));

//
//        // Asynchronous thread transparent transmission
//        final SofaTracerRunnable sofaTracerRunnable = new SofaTracerRunnable(new Runnable() {
//            @Override
//            public void run() {
//                logger.info("SOFATracer Print TraceId and SpanId In Child Thread.");
//            }
//        });
//
//        Thread thread = new Thread(sofaTracerRunnable);
//        thread.start();
        return resultMap;
    }
}
