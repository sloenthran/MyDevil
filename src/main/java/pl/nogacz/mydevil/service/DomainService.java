package pl.nogacz.mydevil.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pl.nogacz.mydevil.domain.Domain;
import pl.nogacz.mydevil.util.socket.MyDevilSocket;

import java.util.ArrayList;
import java.util.List;

@Service
public class DomainService {
    private MyDevilSocket socket = MyDevilSocket.getInstance();
    private Gson gson = new Gson();

    @Cacheable("service_domains")
    public List<Domain> getDomains() {
        String domainListJson = socket.getDomains();
        JsonObject jsonObject = gson.fromJson(domainListJson, JsonObject.class);

        List<Domain> domains = new ArrayList<>();

        if(jsonObject.get("code").getAsString().contains("OK")) {
            JsonArray websites = jsonObject.getAsJsonArray("websites");

            for(int i = 0; i < websites.size(); i++) {
                JsonObject website = websites.get(i).getAsJsonObject();

                Domain domain = Domain.builder()
                        .domain(website.get("domain").getAsString())
                        .domainType(website.get("www_type").getAsString())
                        .domainInfo(website.get("info").getAsString())
                        .phpEvalEnabled(website.get("option_php_eval").getAsBoolean())
                        .phpExecEnabled(website.get("option_php_exec").getAsBoolean())
                        .gzipEnabled(website.get("option_gzip").getAsBoolean())
                        .sslOnly(website.get("option_sslonly").getAsBoolean())
                        .wafLevel(website.get("option_waf").getAsInt())
                        .build();

                domains.add(domain);
            }
        }

        return domains;
    }
}
