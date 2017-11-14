package com.radware.appwall.domain.entities;


import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "WEB_SERVERS", schema = "PUBLIC")
public class WebServerBinding {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "HOST_NAME", unique = true)
    private String hostName;

    @ManyToOne()
    @JoinColumn(name = "HOST_BINDINGS_ID", referencedColumnName = "ID", nullable = false)
    private HostBindings hostBindings;

    @Column(name = "DESCRIPTION", unique = true)
    public String description;
    @Column(name = "IP", unique = true)
    public String ip;
    @Column(name = "PORT", unique = true)
    public Integer port;
    @Column(name = "PROTOCOL", unique = true)
    public ProtocolEnum protocol;

    @Column(name = "SSL_V2", unique = true)
    public Boolean supportSSLv2;

    @Column(name = "SSL_V3", unique = true)
    public Boolean supportSSLv3;
    @Column(name = "TLS_1_0", unique = true)
    public Boolean supportTLSv10;
    @Column(name = "TLS_1_1", unique = true)
    public Boolean supportTLSv11;
    @Column(name = "TLS_1_2", unique = true)
    public Boolean supportTLSv12;

    public HostBindings getHostBindings() {
        return hostBindings;
    }

    public void setHostBindings(HostBindings hostBindings) {
        this.hostBindings = hostBindings;
    }


    public WebServerBinding() {
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(!(o instanceof WebServerBinding)) {
            return false;
        }
        WebServerBinding that = (WebServerBinding) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getWebServerName() {
        return hostBindings.getWebServerName();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public ProtocolEnum getProtocol() {
        return protocol;
    }

    public void setProtocol(ProtocolEnum protocol) {
        this.protocol = protocol;
    }

    public Boolean getSupportSSLv2() {
        return supportSSLv2;
    }

    public void setSupportSSLv2(Boolean supportSSLv2) {
        this.supportSSLv2 = supportSSLv2;
    }

    public Boolean getSupportSSLv3() {
        return supportSSLv3;
    }

    public void setSupportSSLv3(Boolean supportSSLv3) {
        this.supportSSLv3 = supportSSLv3;
    }

    public Boolean getSupportTLSv10() {
        return supportTLSv10;
    }

    public void setSupportTLSv10(Boolean supportTLSv10) {
        this.supportTLSv10 = supportTLSv10;
    }

    public Boolean getSupportTLSv11() {
        return supportTLSv11;
    }

    public void setSupportTLSv11(Boolean supportTLSv11) {
        this.supportTLSv11 = supportTLSv11;
    }

    public Boolean getSupportTLSv12() {
        return supportTLSv12;
    }

    public void setSupportTLSv12(Boolean supportTLSv12) {
        this.supportTLSv12 = supportTLSv12;
    }

    public enum ProtocolEnum {

        HTTP,
        HTTPS,
        TCP;

    }
}
