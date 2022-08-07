package io.letsorganize.aboutretry.service;

import lombok.RequiredArgsConstructor;
import org.springframework.retry.RetryException;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class RetryTemplateService {

    private final RetryTemplate retryTemplate;

    public int retryMethod(){
        long seed = System.currentTimeMillis();
        Random random = new Random(seed);

        return retryTemplate.execute(retryContext -> getRandomeInt(random));
    }

    private int getRandomeInt(Random random) {
        int notTwoMultiple = random.nextInt();
        if (notTwoMultiple % 2 == 0) {
            throw new RetryException("2의 배수입니다.");
        }

        if(notTwoMultiple %3==0){
            throw new RuntimeException("3의 배수입니다.");
        }
        return notTwoMultiple;
    }


}
