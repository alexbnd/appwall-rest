package com.radware.appwall.repository;

import com.radware.appwall.domain.entities.HttpTunnel;
import org.springframework.data.repository.CrudRepository;

public interface TunnelRepository extends CrudRepository<HttpTunnel, Long> {

    HttpTunnel findById(Long id);

    HttpTunnel findByNameIgnoreCase(String tunnelName);

}
