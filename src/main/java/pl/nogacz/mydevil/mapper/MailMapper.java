package pl.nogacz.mydevil.mapper;

import org.springframework.stereotype.Component;
import pl.nogacz.mydevil.domain.mail.Alias;
import pl.nogacz.mydevil.domain.mail.Mail;
import pl.nogacz.mydevil.domain.mail.Mailbox;
import pl.nogacz.mydevil.dto.mail.AliasDto;
import pl.nogacz.mydevil.dto.mail.MailDto;
import pl.nogacz.mydevil.dto.mail.MailboxDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MailMapper {
    public MailDto mapMailToMailDto(final Mail mail) {
        return MailDto.builder()
                .domain(mail.getDomain())
                .dkimStatus(mail.isDkimStatus())
                .mailboxes(
                        mapListMailboxToListMailboxDto(mail.getMailboxes())
                )
                .aliases(
                        mapListAliasToListAliasDto(mail.getAliases())
                )
                .build();
    }

    public List<MailDto> mapListMailToListMailDto(final List<Mail> mails) {
        return mails.stream()
                .map(this::mapMailToMailDto)
                .collect(Collectors.toList());
    }

    public MailboxDto mapMailboxToMailboxDto(final Mailbox mailbox) {
        return MailboxDto.builder()
                .address(mailbox.getAddress())
                .quota(mailbox.getQuota())
                .quotaUsed(mailbox.getQuotaUsed())
                .build();
    }

    public List<MailboxDto> mapListMailboxToListMailboxDto(final List<Mailbox> mailboxes) {
        return mailboxes.stream()
                .map(this::mapMailboxToMailboxDto)
                .collect(Collectors.toList());
    }

    public AliasDto mapAliasToAliasDto(final Alias alias) {
        return AliasDto.builder()
                .from(alias.getFrom())
                .to(alias.getTo())
                .build();
    }

    public List<AliasDto> mapListAliasToListAliasDto(final List<Alias> aliases) {
        return aliases.stream()
                .map(this::mapAliasToAliasDto)
                .collect(Collectors.toList());
    }
}
