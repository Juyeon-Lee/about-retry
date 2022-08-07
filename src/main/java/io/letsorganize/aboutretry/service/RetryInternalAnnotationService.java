package io.letsorganize.aboutretry.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.RetryException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Service
public class RetryInternalAnnotationService {
    public int retryMethod() {
        return internalRetryMethod();
    }

    /**
     * 같은 메서드에서 내부 메서드를 호출하면 프록시 적용이 되지 않아 재시도되지 않습니다.
     */
    @Retryable(listeners = {"retryListener"}, backoff = @Backoff(delay = 100))
    private int internalRetryMethod(){
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
