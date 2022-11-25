package br.com.praise.portal.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "dispositivopraiseapps")
public class Device {
    
    @Id
    @JsonProperty("_id")
    private String ID;
    private String dtativacao;
    private String chave;
    private String version;
    private String model;
    private String manufacturer;
    private String codvend;
    private boolean ativo;
    private int __v;

    public Device(String id, String dtativacao, String chave, String version, String model, String manufacturer, String codved, boolean ativo, int __v) {
        this.ID = id;
        this.dtativacao = dtativacao;
        this.chave = chave;
        this.version = version;
        this.model = model;
        this.manufacturer = manufacturer;
        this.codvend = codved;
        this.ativo = ativo;
        this.__v = __v;
    }

    public Device(String dtativacao, String chave, String version, String model, String manufacturer, String codved, boolean ativo, int __v) {
        this.dtativacao = dtativacao;
        this.chave = chave;
        this.version = version;
        this.model = model;
        this.manufacturer = manufacturer;
        this.codvend = codved;
        this.ativo = ativo;
        this.__v = __v;
    }

    public Device() {}

    @Transient
    public String getTimeFormat() {
        return dtativacao.substring(7,21);
    }
    @Transient
    public String getAtivoModel() {
        if(this.ativo)
            return "table-success";
        return "table-danger";
    }

    @Transient
    public String getDisplayAtivo() {
        if(this.ativo)
            return "btn btn-danger";
        return "btn btn-success";
    }

    @Transient
    public String getDisplayAtivoText() {
        if (this.ativo)
            return "Inativar";
        return "Ativar";
    }
}
