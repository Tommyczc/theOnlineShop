package com.theOnlineShop.controller.system.domain;

import org.springframework.beans.factory.annotation.Value;

public class versionControllerDomain {

    private String gitHub;

    private String name;

    private String version;

    private String copyright;

    public versionControllerDomain(String gitHub, String name, String version, String copyright){
        this.version=version;
        this.gitHub=gitHub;
        this.name=name;
        this.copyright=copyright;
    }

    public String getGitHub() {
        return gitHub;
    }

    public void setGitHub(String gitHub) {
        this.gitHub = gitHub;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }
}
