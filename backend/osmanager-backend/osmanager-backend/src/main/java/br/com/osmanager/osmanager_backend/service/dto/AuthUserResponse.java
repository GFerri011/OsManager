package br.com.osmanager.osmanager_backend.service.dto;


public class AuthUserResponse {

    private String email;
    private String role;

    public AuthUserResponse(String email, String role) {
        this.email = email;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }
}
