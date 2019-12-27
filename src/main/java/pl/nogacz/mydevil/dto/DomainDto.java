package pl.nogacz.mydevil.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DomainDto implements Serializable {
    private String domain;
    private String domainType;
    private String domainInfo;
    private boolean phpEvalEnabled;
    private boolean phpExecEnabled;
    private boolean gzipEnabled;
    private boolean sslOnly;
    private int wafLevel;
}
