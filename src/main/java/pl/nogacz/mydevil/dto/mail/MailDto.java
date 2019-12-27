package pl.nogacz.mydevil.dto.mail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MailDto implements Serializable {
    private String domain;
    private boolean dkimStatus;

    @Builder.Default
    private List<MailboxDto> mailboxes = new ArrayList<>();
    @Builder.Default
    private List<AliasDto> aliases = new ArrayList<>();
}
