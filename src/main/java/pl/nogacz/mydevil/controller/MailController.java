package pl.nogacz.mydevil.controller;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.nogacz.mydevil.domain.mail.Mail;
import pl.nogacz.mydevil.dto.mail.MailDto;
import pl.nogacz.mydevil.mapper.MailMapper;
import pl.nogacz.mydevil.service.MailService;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class MailController {
    private MailService mailService;
    private MailMapper mailMapper;

    @GetMapping("mails")
    @Cacheable("controller_mails")
    public List<MailDto> getMails() {
        List<Mail> mails = mailService.getMails();
        return mailMapper.mapListMailToListMailDto(mails);
    }
}
