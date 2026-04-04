package com.nb.globalerp.training.sitebackendglobalerp.services;

import com.nb.globalerp.training.sitebackendglobalerp.persistence.repo.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final EmailService emailService;
    private final StudentRepository studentRepository;

    @Scheduled(cron = "0 27 2 * * *", zone = "Asia/Novosibirsk")
    public void notifyStudents() {
        var students = studentRepository.getStudentsToNotify();

        log.info("Start sending notifications. Students count: {}", students.size());

        for (var s : students) {
            try {
                String html = buildHtml(s.getFirstName(), s.getCompletionPercent());

                emailService.sendReminder(
                        s.getEmail(),
                        "Курс скоро закончится!",
                        html
                );

                log.info("Email sent to {}", s.getEmail());

            } catch (Exception e) {
                log.error("Failed to send email to {}: {}", s.getEmail(), e.getMessage());
            }
        }
    }

    private String buildHtml(String name, Double percent) {
        return """
            <h2>⚠️ Курс скоро завершится</h2>
            <p>Здравствуйте, %s!</p>
            <p>Ваш прогресс: <b>%.2f%%</b></p>
            <p>У вас остался <b>1 день</b> для завершения курса.</p>
            <p style="color:red;">Пожалуйста, завершите обучение вовремя!</p>
        """.formatted(name, percent * 100); // если у тебя 0.85 → 85%
    }
}