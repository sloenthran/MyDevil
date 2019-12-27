package pl.nogacz.mydevil.controller;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.nogacz.mydevil.domain.Domain;
import pl.nogacz.mydevil.dto.DomainDto;
import pl.nogacz.mydevil.mapper.DomainMapper;
import pl.nogacz.mydevil.service.DomainService;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class DomainController {
    private DomainService service;
    private DomainMapper mapper;

    @GetMapping("domains")
    @Cacheable("controller_domains")
    public List<DomainDto> getDomains() {
        List<Domain> domains = service.getDomains();
        return mapper.mapListDomainToListDomainDto(domains);
    }
}
