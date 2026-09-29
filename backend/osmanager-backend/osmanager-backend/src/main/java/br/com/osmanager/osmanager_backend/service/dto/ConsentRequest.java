package br.com.osmanager.osmanager_backend.service.dto;

import jakarta.validation.constraints.AssertTrue;

public class ConsentRequest {

    @AssertTrue(message = "É necessário aceitar os Termos de Uso")
    private Boolean termsAccepted;

    @AssertTrue(message = "É necessário aceitar a Política de Privacidade")
    private Boolean privacyPolicyAccepted;

    public Boolean getTermsAccepted() {
        return termsAccepted;
    }

    public void setTermsAccepted(Boolean termsAccepted) {
        this.termsAccepted = termsAccepted;
    }

    public Boolean getPrivacyPolicyAccepted() {
        return privacyPolicyAccepted;
    }

    public void setPrivacyPolicyAccepted(Boolean privacyPolicyAccepted) {
        this.privacyPolicyAccepted = privacyPolicyAccepted;
    }
}
