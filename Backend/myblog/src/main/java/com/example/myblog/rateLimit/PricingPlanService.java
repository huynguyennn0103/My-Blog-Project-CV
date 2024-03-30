package com.example.myblog.rateLimit;

import com.example.myblog.entity.Category;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class PricingPlanService {
    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();
    public Bucket resolveBucket(String apiKey) {
        return cache.computeIfAbsent(apiKey, this::newBucket);
    }

    public Bucket newBucket(String apiKey) {
        PricingPlan pricingPlan = PricingPlan.resolvePlanFromApiKey(apiKey);
        return Bucket.builder()
                .addLimit(pricingPlan.getLimit())
                .build();
    }
}
