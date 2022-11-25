package br.com.praise.portal.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "clientepraiseapps")
public class Client {

    @Id
    @JsonProperty("_id")
    private String ID;
    private String chave;
    private String nome;
    private int licencas;
    private boolean ativo;

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
