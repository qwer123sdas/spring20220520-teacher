package com.choong.spr.authentication;

import java.io.Serializable;

public class ThirdPartyValidationResponse {
    public boolean isValid() {
    	return false;
    }
    
    public Serializable getToken() {
    	return null;
    }
}
