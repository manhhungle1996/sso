package vn.com.atomi.openbanking.authservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Locale;

@Component
public class I18n {
    private static ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    @Bean(name = "messageSource")
    public ResourceBundleMessageSource getMessageSource() {
        messageSource.setBasenames("language/messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setUseCodeAsDefaultMessage(true);
        return messageSource;
    }
    @Bean
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
        localeResolver.setDefaultLocale(new Locale("vi","VN")); // set default to vi_VN
        return localeResolver;
    }

    public static String getMessage(String msgCode) {
        Locale locale = LocaleContextHolder.getLocale();
        if (locale.getLanguage() == null || locale.getLanguage().isEmpty()) {
            locale = new Locale("vi");
        }
        return messageSource.getMessage(msgCode, null, locale);
    }

    public static String getMessage(String msgCode, Object... arg) {
        Locale locale = LocaleContextHolder.getLocale();
        if (locale.getLanguage() == null || locale.getLanguage().isEmpty()) {
            locale = new Locale("vi");
        }
        return String.format(messageSource.getMessage(msgCode,null, locale), arg);
    }
}