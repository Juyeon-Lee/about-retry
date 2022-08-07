package io.letsorganize.aboutretry.config;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.listener.RetryListenerSupport;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RetryListener extends RetryListenerSupport {

    /**
     * 매 예외 발생 시 마다 실행됩니다.
     */
    @Override
    public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
        log.info("Retryable method {} threw {}th exception {}",
                context.getAttribute(RetryContext.NAME), context.getRetryCount(), throwable.toString());
    }

    /**
     * max retry 횟수에 다다랐을 때나 예외가 더 발생하지 않았을 경우 실행됩니다.
     */
    @Override
    public <T, E extends Throwable> void close(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
        log.info("Retryable method close- retryCount:{}, at:{}", context.getRetryCount(), context.getAttribute(RetryContext.NAME));
    }

    @Override
    public <T, E extends Throwable> boolean open(RetryContext context, RetryCallback<T, E> callback) {
        log.info("Retryable method open");

        return super.open(context, callback);
    }
}
