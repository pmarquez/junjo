package info.pmarquezh.junjo.config;

//   Standard Libraries Imports
import java.util.Locale;

//   Third Party Libraries Imports
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

//   ns Framework Imports

//   Domain Imports


/**
 * I18nConfig.java<br><br>
 * Creation Date 2022-08-18 12:02<br><br>
 * <b>DESCRIPTION:</b><br><br>
 * <p></p>
 *
 * <PRE>
 * <table width="90%" border="1" cellpadding="3" cellspacing="2">
 * <tr><th colspan="2">   History   </th></tr>
 *
 * <tr>
 * <td width="20%">Version 1.0<br>
 * Version Date: 2022-08-18 12:02<br>
 *
 * @author pmarquezh </td>
 * <td width="80%"><p>Creation</p></td>
 * </tr>
 * </table>
 * </PRE>
 * @author pmarquezh
 * @version 1.0 - 2022-08-18 12:02
 */
@Slf4j
@Configuration
public class I18nConfig implements WebMvcConfigurer {

    @Value ( "${info.pmarquezh.junjo.defaultLocaleLanguage}" )
    private String lang;
    @Value ( "${info.pmarquezh.junjo.defaultLocaleCountry}" )
    private String country;

    @Bean ( "messageSource" )
    public MessageSource messageSource ( ) {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource ( );
                                    messageSource.setBasenames ( "language/messages" );
                                    messageSource.setDefaultEncoding ( "UTF-8" );
        return messageSource;
    }

    @Bean
    public LocaleResolver localeResolver ( ) {
        SessionLocaleResolver   slr = new SessionLocaleResolver ( );
                                slr.setDefaultLocale        ( new Locale ( lang, country ) );
                                slr.setLocaleAttributeName  ( "current.locale" );
                                slr.setTimeZoneAttributeName ( "current.timezone" );
        return slr;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor ( ) {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor ( );
                                localeChangeInterceptor.setParamName ( "language" );
        return localeChangeInterceptor;
    }

    @Override
    public void addInterceptors ( InterceptorRegistry registry ) {
        registry.addInterceptor ( this.localeChangeInterceptor ( ) );
    }

    @Override
    @Bean
    public LocalValidatorFactoryBean getValidator ( ) {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean ( );
                                  bean.setValidationMessageSource ( this.messageSource ( ) );

        return bean;
    }

}