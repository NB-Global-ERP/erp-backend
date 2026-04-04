package com.nb.globalerp.training.sitebackendglobalerp.api.controller;

import com.nb.globalerp.training.sitebackendglobalerp.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v1/mail")
@RestController
public class MailController {
    private final EmailService emailService;
    @PostMapping("/test-email")
    public String testEmail() {
        emailService.sendReminder(
                "malinovskiy.denis@yandex.ru",
                "Тест",
                "<h2>Работает!</h2>"
        );
        return "OK";
    }
}
