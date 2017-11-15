package com.radware.appwall.repository;

import com.radware.appwall.domain.entities.WebServerBinding;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HostBindingsWebServersRepository extends CrudRepository<WebServerBinding, Long> {

    WebServerBinding findById(Long id);

    WebServerBinding findByHostNameIgnoreCase(String hostName);

    WebServerBinding findByIpAndPort(String ip, Integer port);
}
