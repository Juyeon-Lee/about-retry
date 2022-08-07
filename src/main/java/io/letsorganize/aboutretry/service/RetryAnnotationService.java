package io.letsorganize.aboutretry.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.RetryException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Slf4j
public class RetryAnnotationService {

    @Retryable(listeners = {"retryListener"}, backoff = @Backoff(delay = 100), maxAttempts = 3)
    public int retryMethod() {
        long seed = System.currentTimeMillis();
        Random random = new Random(seed);

        int notTwoMultiple = random.nextInt();
        if (notTwoMultiple % 2 == 0) {
            throw new RetryException("2의 배수입니다.");
        }

        if(notTwoMultiple %3==0){
            throw new RuntimeException("3의 배수입니다.");
        }

        return notTwoMultiple;
    }

    @Recover
    public void customException(RetryException e) {
        log.info("at recover- [{}], msg:{}", e.getClass().getSimpleName(), e.getMessage());
        // custom exception recover logic
    }

    @Recover
    public void otherException(Exception e) {
        log.info("at recover- [{}], msg:{}", e.getClass().getSimpleName(), e.getMessage());
        // default exception recover logic
    }

}
