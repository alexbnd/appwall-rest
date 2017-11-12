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
}
