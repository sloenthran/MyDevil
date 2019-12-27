package pl.nogacz.mydevil.dto.mail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MailboxDto implements Serializable {
    private String address;
    private Long quota;
    private Long quotaUsed;
}
