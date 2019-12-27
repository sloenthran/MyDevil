package pl.nogacz.mydevil.mapper;

import org.springframework.stereotype.Component;
import pl.nogacz.mydevil.domain.Domain;
import pl.nogacz.mydevil.dto.DomainDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DomainMapper {
    public DomainDto mapDomainToDomainDto(final Domain domain) {
        return DomainDto.builder()
                .domain(domain.getDomain())
                .domainType(domain.getDomainType())
                .domainInfo(domain.getDomainInfo())
                .phpEvalEnabled(domain.isPhpEvalEnabled())
                .phpExecEnabled(domain.isPhpExecEnabled())
                .gzipEnabled(domain.isGzipEnabled())
                .sslOnly(domain.isSslOnly())
                .wafLevel(domain.getWafLevel())
                .build();
    }

    public List<DomainDto> mapListDomainToListDomainDto(final List<Domain> domains) {
        return domains.stream()
                .map(this::mapDomainToDomainDto)
                .collect(Collectors.toList());
    }
}
