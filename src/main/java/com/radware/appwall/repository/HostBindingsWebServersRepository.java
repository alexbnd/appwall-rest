package com.radware.appwall.repository;

import com.radware.appwall.domain.entities.WebServerBinding;
import org.springframework.data.repository.CrudRepository;

public interface HostBindingsWebServersRepository extends CrudRepository<WebServerBinding, Long> {

    public WebServerBinding findById(Long id);
}
