package com.example.myblog.interceptor;

import com.example.myblog.payload.response.base.BaseResponse;
import com.example.myblog.rateLimit.PricingPlanService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import io.github.bucket4j.Refill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;

@Component
public class InterceptorAppConfig implements HandlerInterceptor {
    @Autowired
    private PricingPlanService pricingPlanService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getServletPath().contains("public")){
            return true;
        }
        String apiKey = request.getHeader("X-api-key");
        if(apiKey == null || apiKey.isEmpty()){
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setStatusCode(500);
            baseResponse.setMessage("Missing header: X-api-key");
            response.setContentType("application/json");
            new ObjectMapper().writeValue(response.getOutputStream(), baseResponse);
            return false;
        }

        Bucket tokenBucket = pricingPlanService.resolveBucket(apiKey);
        ConsumptionProbe probe = tokenBucket.tryConsumeAndReturnRemaining(1);
        if(probe.isConsumed()){
            System.out.println("Can still consume: " + probe.getRemainingTokens());
            response.addHeader("X-Rate-Limit-Remaining", String.valueOf(probe.getRemainingTokens()));
            return true;
        }

        long waitForRefill = probe.getNanosToWaitForRefill() / 1_000_000_000;
        response.addHeader("X-Rate-Limit-Retry-After-Seconds", String.valueOf(waitForRefill));

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(500);
        baseResponse.setMessage("Traffic is busy now, try again after " + String.valueOf(waitForRefill) + " seconds");
        response.setContentType("application/json");
        new ObjectMapper().writeValue(response.getOutputStream(), baseResponse);
        return false;
    }
}
