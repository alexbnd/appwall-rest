package com.radware.appwall.rest.providers;

import com.radware.appwall.domain.entities.WebServerBinding;
import com.radware.appwall.repository.HostBindingsWebServersRepository;
import com.radware.appwall.validation.ValidWebServerBinding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Service
public class WebServerValidator implements ConstraintValidator<ValidWebServerBinding, WebServerBinding> {

    public static final String IP_AND_PORT_COMBINATION_ALREADY_EXIST = "Ip and port combination already exist";
    public static final String HOST_NAME_ALREADY_EXIST = "Host name already exist";
    public static final String HOST_NAME_EMPTY = "WebServerName is empty";
    public static final String IP_IS_EMPTY = "IP is empty";
    public static final String PORT_IS_EMPTY = "Port is empty";

    @Autowired
    private HostBindingsWebServersRepository webServersRepository;

    @Override
    public void initialize(final ValidWebServerBinding hasId) {
    }

    @Override
    public boolean isValid(final WebServerBinding webServerBinding,
            final ConstraintValidatorContext constraintValidatorContext) {
        String error = validate(webServerBinding);
        if(error != null) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(error).addConstraintViolation();
            return false;
        }
        return true;
    }

    public String validate(WebServerBinding webServerBinding) {
        if(webServerBinding.getHostName() == null || webServerBinding.getHostName().trim().isEmpty()) {
            return HOST_NAME_EMPTY;
        }
        WebServerBinding savedWebServerBinding = null;
        if(webServerBinding.getId() != null) {
            savedWebServerBinding = webServersRepository.findById(webServerBinding.getId());
        }
        if(webServerBinding.getIp() == null || webServerBinding.getIp().trim().isEmpty()) {
            return IP_IS_EMPTY;
        }
        if(webServerBinding.getPort() == null) {
            return PORT_IS_EMPTY;
        }

        WebServerBinding byHostNameIgnoreCase =
                webServersRepository.findByHostNameIgnoreCase(webServerBinding.getHostName());
        boolean isUpdate = byHostNameIgnoreCase != null && savedWebServerBinding != null &&
                savedWebServerBinding.getId().equals(byHostNameIgnoreCase.getId());
        if(byHostNameIgnoreCase != null && !isUpdate) {
            return HOST_NAME_ALREADY_EXIST;
        }
        WebServerBinding byIpAndPort =
                webServersRepository.findByIpAndPort(webServerBinding.getIp(), webServerBinding.getPort());
        isUpdate = savedWebServerBinding != null && byIpAndPort != null &&
                savedWebServerBinding.getId().equals(byIpAndPort.getId());

        if(byIpAndPort != null && !isUpdate) {
            return IP_AND_PORT_COMBINATION_ALREADY_EXIST;
        }
        return null;
    }
}
