package br.com.praise.portal.Enum;

public enum Permissions {

    ADMIN("Admin", "Acesso a todas as permissões"),
    VIEW_DEVICE_LIST("Lista de dispositivos", "Acesso a visualizar a lista de dispositivos"),
    CHANGE_DEVICE_STATUS("Mudar status do dispositivo", "Acesso a mudar o status de ativo ou invativo do dispositivo");
    //CHANGE_CLIENT_STATUS("Mudar status do cliente", "Acesso a mudar o status de ativo ou inativo do cliente");

    private final String name;
    private final String desc;

    Permissions(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }
}
