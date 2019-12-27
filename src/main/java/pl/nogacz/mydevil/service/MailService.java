package pl.nogacz.mydevil.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pl.nogacz.mydevil.domain.mail.Alias;
import pl.nogacz.mydevil.domain.mail.Mail;
import pl.nogacz.mydevil.domain.mail.Mailbox;
import pl.nogacz.mydevil.util.socket.MyDevilSocket;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class MailService {
    private static Logger LOGGER = LoggerFactory.getLogger(MailService.class);

    private MyDevilSocket socket = MyDevilSocket.getInstance();
    private Gson gson = new Gson();

    @Cacheable("service_mails")
    public List<Mail> getMails() {
        List<Mail> mails = getMailsDomain();
        return getMailboxesAndAliasesFromDomain(mails);
    }

    private List<Mail> getMailsDomain() {
        String domainListJson = socket.getMailsDomain();
        JsonObject jsonObject = gson.fromJson(domainListJson, JsonObject.class);

        List<Mail> mails = new ArrayList<>();

        if(jsonObject.get("code").getAsString().contains("OK")) {
            JsonArray domains = jsonObject.getAsJsonArray("domains");

            for(int i = 0; i < domains.size(); i++) {
                JsonObject domain = domains.get(i).getAsJsonObject();

                Mail mail = Mail.builder()
                        .domain(domain.get("domain").getAsString())
                        .dkimStatus(domain.get("dkim_key").getAsBoolean())
                        .build();

                mails.add(mail);
            }
        }

        return mails;
    }

    private List<Mail> getMailboxesAndAliasesFromDomain(List<Mail> mails) {
        for(Mail mail : mails) {
            String domainInfo = socket.getMailboxesAndAliasesFromDomain(mail.getDomain());
            JsonObject jsonObject = gson.fromJson(domainInfo, JsonObject.class);

            if(jsonObject.get("code").getAsString().contains("OK")) {
                JsonArray mailboxes = jsonObject.getAsJsonArray("mailboxes");

                for (int i = 0; i < mailboxes.size(); i++) {
                    JsonObject mailboxObject = mailboxes.get(i).getAsJsonObject();

                    Mailbox mailbox = Mailbox.builder()
                            .address(mailboxObject.get("address_mailbox").getAsString())
                            .quota(mailboxObject.get("quota").getAsLong())
                            .quotaUsed(mailboxObject.get("quota_used").getAsLong())
                            .build();

                    mail.getMailboxes().add(mailbox);
                }

                JsonArray aliases = jsonObject.getAsJsonArray("aliases");

                for (int i = 0; i < aliases.size(); i++) {
                    JsonObject aliasObject = aliases.get(i).getAsJsonObject();

                    Alias alias = Alias.builder()
                            .from(aliasObject.get("address_alias").getAsString())
                            .to(aliasObject.get("address_goto").getAsString())
                            .build();

                    mail.getAliases().add(alias);
                }
            }
        }

        return mails;
    }
}
