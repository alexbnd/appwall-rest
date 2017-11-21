package com.radware.appwall.domain.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "HOST_BIND", schema = "PUBLIC")
public class HostBindings {

    @Id
    @Expose
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    @SerializedName("ID")
    private Long id;

    @Expose
    @SerializedName("GetUserIPFromHTTPHeader")
    @Column(name = "USER_IP")
    private String getUserIPFromHTTPHeader;

    @Expose
    @SerializedName("HostName")
    @Column(name = "HOST_NAME")
    private String hostName;

    @Expose
    @SerializedName("HostBindingsWebServers")
    @JoinTable(
            name="HOST_TO_WEB_SERVER_MAPPING",
            joinColumns=@JoinColumn(name="HOST_ID", referencedColumnName="ID"),
            inverseJoinColumns=@JoinColumn(name="WEB_SERVER_ID", referencedColumnName="ID"))
    @ElementCollection(targetClass=WebServerBinding.class, fetch = FetchType.EAGER)
    private List<WebServerBinding> webServers;

    public HostBindings() {
    }

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
        return hostName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getGetUserIPFromHTTPHeader() {
        return getUserIPFromHTTPHeader;
    }

    public void setGetUserIPFromHTTPHeader(String getUserIPFromHTTPHeader) {
        this.getUserIPFromHTTPHeader = getUserIPFromHTTPHeader;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }


    public List<WebServerBinding> getWebServers() {
        return webServers;
    }

    public void setWebServers(List<WebServerBinding> webServers) {
        this.webServers = webServers;
    }

}
