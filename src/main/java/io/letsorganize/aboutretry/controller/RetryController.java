package io.letsorganize.aboutretry.controller;

import io.letsorganize.aboutretry.service.RetryAnnotationService;
import io.letsorganize.aboutretry.service.RetryInternalAnnotationService;
import io.letsorganize.aboutretry.service.RetryTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestController
@RestControllerAdvice
@RequiredArgsConstructor
public class RetryController {
    private final RetryAnnotationService annotationService;
    private final RetryTemplateService templateService;
    private final RetryInternalAnnotationService internalAnnotationService;

    @GetMapping(value = "/annotation")
    public String usingAnnotation() {
        try {
            int number = annotationService.retryMethod();
            return "SUCCESS! number=" +number;
        } catch (Exception e) {
            return e.getClass().getSimpleName() + "> msg : " + e.getMessage();
        }
    }

    @GetMapping(value = "/template")
    public String usingTemplate() {
        try {
            int number = templateService.retryMethod();
            return "SUCCESS! number=" +number;
        } catch (Exception e) {
            return e.getClass().getSimpleName() + "> msg : " + e.getMessage();
        }
    }

    @GetMapping(value = "/internal")
    public String usingInternalAnnotation() {
        try {
            int number = internalAnnotationService.retryMethod();
            return "SUCCESS! number=" +number;
        } catch (Exception e) {
            return e.getClass().getSimpleName() + "> msg : " + e.getMessage();
        }
    }
}
