package com.radware.appwall.rest.providers;

import com.radware.appwall.domain.entities.HostBindings;
import com.radware.appwall.repository.HostBindingsRepository;
import com.radware.appwall.validation.ValidHostBinding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Service
public class HostBindingValidator implements ConstraintValidator<ValidHostBinding, HostBindings> {

    public static final String HOST_NAME_ALREADY_EXIST = "Host name already exist";
    public static final String HOST_NAME_EMPTY = "Host name is empty";

    @Autowired
    private HostBindingsRepository webServersRepository;

    @Override
    public void initialize(final ValidHostBinding hasId) {
    }

    @Override
    public boolean isValid(final HostBindings webServerBinding,
            final ConstraintValidatorContext constraintValidatorContext) {
        String error = validate(webServerBinding);
        if(error != null) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(error).addConstraintViolation();
            return false;
        }
        return true;
    }

    public String validate(HostBindings hostBindings) {

        if(hostBindings.getHostName() == null || hostBindings.getHostName().trim().isEmpty()) {
            return HOST_NAME_EMPTY;
        }
        HostBindings savedWebServerBinding = null;
        if(hostBindings.getId() != null) {
            savedWebServerBinding = webServersRepository.findById(hostBindings.getId());
        }
        HostBindings byHostNameIgnoreCase = webServersRepository.findByHostNameIgnoreCase(hostBindings.getHostName());
        boolean isUpdate = byHostNameIgnoreCase != null && savedWebServerBinding != null &&
                savedWebServerBinding.getId().equals(byHostNameIgnoreCase.getId());
        if(byHostNameIgnoreCase != null && !isUpdate) {
            return HOST_NAME_ALREADY_EXIST;
        }
        return null;
    }
}
