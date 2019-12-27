package pl.nogacz.mydevil.domain.mail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Mailbox implements Serializable {
    private String address;
    private Long quota;
    private Long quotaUsed;
}
