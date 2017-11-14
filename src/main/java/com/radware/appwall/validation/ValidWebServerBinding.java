package com.radware.appwall.validation;

import com.radware.appwall.domain.entities.WebServerBinding;
import com.radware.appwall.repository.HostBindingsWebServersRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidWebServerBinding.Validator.class)
public @interface ValidWebServerBinding {

    String IP_AND_PORT_COMBINATION_ALREADY_EXIST = "Ip and port combination already exist";
    String HOST_NAME_ALREADY_EXIST = "Host name already exist";
    String HOST_NAME_EMPTY = "WebServerName is empty";
    String IP_IS_EMPTY = "IP is empty";
    String PORT_IS_EMPTY = "Port is empty";

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    public class Validator implements ConstraintValidator<ValidWebServerBinding, WebServerBinding> {

        @Autowired
        private HostBindingsWebServersRepository webServersRepository;

        @Override
        public void initialize(final ValidWebServerBinding hasId) {
        }

        @Override
        public boolean isValid(final WebServerBinding webServerBinding,
                final ConstraintValidatorContext constraintValidatorContext) {
            if(webServerBinding.getHostName() == null || webServerBinding.getHostName().trim().isEmpty()) {
                constraintValidatorContext.buildConstraintViolationWithTemplate(HOST_NAME_EMPTY)
                                          .addConstraintViolation();
                return false;
            }
            if(webServerBinding.getIp() == null || webServerBinding.getIp().trim().isEmpty()) {
                constraintValidatorContext.buildConstraintViolationWithTemplate(IP_IS_EMPTY).addConstraintViolation();
                return false;
            }
            if(webServerBinding.getPort() == null) {
                constraintValidatorContext.buildConstraintViolationWithTemplate(PORT_IS_EMPTY).addConstraintViolation();
                return false;
            }

            if(webServersRepository.findByHostNameIgnoreCase(webServerBinding.getHostName()) != null) {
                constraintValidatorContext.buildConstraintViolationWithTemplate(HOST_NAME_ALREADY_EXIST)
                                          .addConstraintViolation();
                return false;
            }
            List<WebServerBinding> byIpAndPort =
                    webServersRepository.findByIpAndPort(webServerBinding.getIp(), webServerBinding.getPort());
            if(byIpAndPort != null && !byIpAndPort.isEmpty()) {
                constraintValidatorContext.buildConstraintViolationWithTemplate(IP_AND_PORT_COMBINATION_ALREADY_EXIST)
                                          .addConstraintViolation();
                return false;
            }
            return true;
        }
    }
}