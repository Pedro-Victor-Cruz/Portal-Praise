package br.com.praise.portal.Enum;

public enum RolesEnum {

    ADMIN("Administrador"),
    GERENTE("Gerente"),
    CLIENTE("Cliente");

    private String role;
    RolesEnum(String user) {
        this.role = user;
    }
}
