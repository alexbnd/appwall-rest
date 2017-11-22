package com.radware.appwall.repository;

import com.radware.appwall.domain.entities.HostBindings;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HostBindingsRepository extends CrudRepository<HostBindings, Long> {


    HostBindings findById(Long id);

    HostBindings findByHostNameIgnoreCase(String hostName);

    @Query( "select o from HostBindings o inner join o.webServers w where w.id = :webServerId" )
    List<HostBindings> findByWebServerId(@Param("webServerId") Long webServerId);
}
