package pl.nogacz.mydevil.domain.mail;

import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Mail implements Serializable {
    private String domain;
    private boolean dkimEnabled;

    @Builder.Default
    private List<Mailbox> mailboxes = new ArrayList<>();
    @Builder.Default
    private List<Alias> aliases = new ArrayList<>();
}
