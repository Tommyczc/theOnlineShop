package com.theOnlineShop.controller.system.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class versionControllerDomain {
    private String gitHub;
    private String name;
    private String version;
    private String copyright;

    @Autowired
    public versionControllerDomain(@Value("${theOnlineShop.Github}") String gitHub,
                                   @Value("${theOnlineShop.Name}") String name,
                                   @Value("${theOnlineShop.Version}") String version,
                                   @Value("${theOnlineShop.Copyright}") String copyright){
        this.gitHub=gitHub;
        this.name=name;
        this.version=version;
        this.copyright=copyright;
    }


    public String getGitHub() {
        return gitHub;
    }

    public void setGitHub(String gitHub) {
        this.gitHub = gitHub;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCopyright() {
        return this.copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }
}
