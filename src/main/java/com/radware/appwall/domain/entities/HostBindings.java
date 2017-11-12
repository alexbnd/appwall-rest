package com.radware.appwall.domain.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "HOST_BIND", schema = "PUBLIC")
public class HostBindings {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;
/*
    @Column(name = "HOST_NAME")
    private String hostName;*/

    @Column(name = "USER_IP")
    private String getUserIPFromHTTPHeader;

    @Column(name = "WEB_SERVER_NAME")
    private String webServerName;

    @OneToMany(mappedBy = "hostBindings", fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<WebServerBinding> webServers = new HashSet<>();

    public HostBindings() {
    }
/*
    public HostBindings(String hostName) {
        this.hostName = hostName;
    }*/

    @Override
    public boolean equals(Object obj) {
        if((obj == null) || (!(obj instanceof HostBindings))) {
            return false;
        }
        HostBindings other = ((HostBindings) obj);
        return (other.id.equals(this.id));
    }

    @Override
    public int hashCode() {
        return (id.hashCode());
    }

    @Override
    public String toString() {
        return webServerName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

/*    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }*/

    public String getGetUserIPFromHTTPHeader() {
        return getUserIPFromHTTPHeader;
    }

    public void setGetUserIPFromHTTPHeader(String getUserIPFromHTTPHeader) {
        this.getUserIPFromHTTPHeader = getUserIPFromHTTPHeader;
    }

    public String getWebServerName() {
        return webServerName;
    }

    public void setWebServerName(String webServerName) {
        this.webServerName = webServerName;
    }


    public Set<WebServerBinding> getWebServers() {
        return webServers;
    }

    public void setWebServers(Set<WebServerBinding> webServers) {
        this.webServers = webServers;
    }

}
