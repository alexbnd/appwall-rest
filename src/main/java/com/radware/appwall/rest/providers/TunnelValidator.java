package com.radware.appwall.rest.providers;

import com.radware.appwall.domain.entities.HttpTunnel;
import com.radware.appwall.repository.TunnelRepository;
import com.radware.appwall.validation.ValidTunnel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Service
public class TunnelValidator implements ConstraintValidator<ValidTunnel, HttpTunnel> {

    public static final String NAME_ALREADY_EXIST = "Tunnel already exist";
    public static final String HOST_NAME_EMPTY = "Tunnel Name is empty";

    @Autowired
    private TunnelRepository tunnelRepository;

    @Override
    public void initialize(final ValidTunnel hasId) {
    }

    @Override
    public boolean isValid(final HttpTunnel webServerBinding,
            final ConstraintValidatorContext constraintValidatorContext) {
        String error = validate(webServerBinding);
        if(error != null) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(error).addConstraintViolation();
            return false;
        }
        return true;
    }

    public String validate(HttpTunnel tunnel) {
        //TODO add odd validations to tunnel
        if(tunnel.getName() == null || tunnel.getName().trim().isEmpty()) {
            return HOST_NAME_EMPTY;
        }
        HttpTunnel httpTunnel = null;
        if(tunnel.getId() != null) {
            httpTunnel = tunnelRepository.findById(tunnel.getId());
        }

        HttpTunnel byHostNameIgnoreCase = tunnelRepository.findByNameIgnoreCase(tunnel.getName());
        boolean isUpdate = byHostNameIgnoreCase != null && httpTunnel != null &&
                httpTunnel.getId().equals(byHostNameIgnoreCase.getId());
        if(byHostNameIgnoreCase != null && !isUpdate) {
            return NAME_ALREADY_EXIST;
        }
        return null;
    }
}
