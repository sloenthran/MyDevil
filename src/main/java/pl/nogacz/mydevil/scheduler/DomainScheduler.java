package pl.nogacz.mydevil.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.nogacz.mydevil.service.DomainService;

import javax.annotation.PostConstruct;

@Component
public class DomainScheduler {
    private static Logger LOGGER = LoggerFactory.getLogger(DomainService.class);

    private DomainService service;

    public DomainScheduler(DomainService service) {
        this.service = service;
    }

    @PostConstruct
    public void initScheduler() {
        LOGGER.info("[DomainScheduler] Scheduler initiated.");
    }

    @Scheduled(cron = "0 * * * * *")
    public void getMails() {
        service.getDomains();
        LOGGER.info("[DomainScheduler] Domains cached.");
    }
}
