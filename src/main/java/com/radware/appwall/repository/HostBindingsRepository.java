package com.radware.appwall.repository;

import com.radware.appwall.domain.entities.HostBindings;
import org.springframework.data.repository.CrudRepository;

public interface HostBindingsRepository extends CrudRepository<HostBindings, Long> {


    HostBindings findById(Long id);

    HostBindings findByHostNameIgnoreCase(String hostName);
}
