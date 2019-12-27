package pl.nogacz.mydevil.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.nogacz.mydevil.service.MailService;

import javax.annotation.PostConstruct;

@Component
public class MailScheduler {
    private static Logger LOGGER = LoggerFactory.getLogger(MailScheduler.class);

    private MailService service;

    public MailScheduler(MailService service) {
        this.service = service;
    }

    @PostConstruct
    public void initScheduler() {
        LOGGER.info("[MailScheduler] Scheduler initiated.");
    }

    @Scheduled(cron = "0 * * * * *")
    public void getMails() {
        service.getMails();
        LOGGER.info("[MailScheduler] Mails cached.");
    }
}