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

    String IP_AND_PORT_COMBINATION_ALREADY_EXIST = "Ip and port combination already exist";
    String HOST_NAME_ALREADY_EXIST = "Host name already exist";
    String HOST_NAME_EMPTY = "WebServerName is empty";
    String IP_IS_EMPTY = "IP is empty";
    String PORT_IS_EMPTY = "Port is empty";

    @Autowired
    private HostBindingsWebServersRepository webServersRepository;

    @Override
    public void initialize(final ValidWebServerBinding hasId) {
    }

    @Override
    public boolean isValid(final WebServerBinding webServerBinding,
            final ConstraintValidatorContext constraintValidatorContext) {
        if(webServerBinding.getHostName() == null || webServerBinding.getHostName().trim().isEmpty()) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(HOST_NAME_EMPTY).addConstraintViolation();
            return false;
        }
        WebServerBinding savedWebServerBinding = null;
        if(webServerBinding.getId() != null) {
            savedWebServerBinding = webServersRepository.findById(webServerBinding.getId());
        }
        if(webServerBinding.getIp() == null || webServerBinding.getIp().trim().isEmpty()) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(IP_IS_EMPTY).addConstraintViolation();
            return false;
        }
        if(webServerBinding.getPort() == null) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(PORT_IS_EMPTY).addConstraintViolation();
            return false;
        }

        WebServerBinding byHostNameIgnoreCase =
                webServersRepository.findByHostNameIgnoreCase(webServerBinding.getHostName());
        boolean isUpdate = byHostNameIgnoreCase != null && savedWebServerBinding != null &&
                savedWebServerBinding.getId().equals(byHostNameIgnoreCase.getId());
        if(byHostNameIgnoreCase != null && !isUpdate) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(HOST_NAME_ALREADY_EXIST)
                                      .addConstraintViolation();
            return false;
        }
        WebServerBinding byIpAndPort =
                webServersRepository.findByIpAndPort(webServerBinding.getIp(), webServerBinding.getPort());
        isUpdate = savedWebServerBinding != null && byIpAndPort != null &&
                savedWebServerBinding.getId().equals(byIpAndPort.getId());

        if(byIpAndPort != null && !isUpdate) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(IP_AND_PORT_COMBINATION_ALREADY_EXIST)
                                      .addConstraintViolation();
            return false;
        }
        return true;
    }
}
