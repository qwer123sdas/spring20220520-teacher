package com.choong.spr.authentication;

import java.util.Collection;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

public class ThirdPartyAuthenticationProvider implements AuthenticationProvider {
    
    private Class<? extends Authentication> supportingClass = UsernamePasswordAuthenticationToken.class;
    /*
    
	 <bean id="thirdPartyAuthenticationProvider" class="com.choong.spr.authentication.ThirdPartyAuthenticationProvider">
        <!-- here set your external authentication validator in case you can't autowire it -->
        <property name="externalAuthenticationValidator" ref="yourExternalAuthenticationValidator" />
    </bean>
    
    */
    // This represents your existing username/password validation class
    // Bind it with an @Autowired or set it in your security config
    private ExternalAuthenticationValidator externalAuthenticationValidator;

    /* (non-Javadoc)
     * @see org.springframework.security.authentication.AuthenticationProvider#authenticate(org.springframework.security.core.Authentication)
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    	ThirdPartyValidationResponse response = this.externalAuthenticationValidator.validate(authentication.getName(), authentication.getCredentials().toString());
        if(!response.isValid()){
            throw new BadCredentialsException("username and/or password not valid");
        }
        Collection<? extends GrantedAuthority> authorities = null; 
        // you must fill this authorities collection
        return new UsernamePasswordAuthenticationToken(
                    authentication.getName(),
                    authentication.getCredentials(),
                    authorities
                );      
    }

    /* (non-Javadoc)
     * @see org.springframework.security.authentication.AuthenticationProvider#supports(java.lang.Class)
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return this.supportingClass.isAssignableFrom(authentication);
    }

    public ExternalAuthenticationValidator getExternalAuthenticationValidator() {
        return externalAuthenticationValidator;
    }

    public void setExternalAuthenticationValidator(ExternalAuthenticationValidator externalAuthenticationValidator) {
        this.externalAuthenticationValidator = externalAuthenticationValidator;
    }   

}
