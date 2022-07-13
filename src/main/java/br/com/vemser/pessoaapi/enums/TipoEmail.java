package br.com.vemser.pessoaapi.enums;

import java.util.Arrays;

public enum TipoEmail {
    CREATE("create"),
    PUT("put"),
    DELETE("delete");

    private String tipoEmail;

    TipoEmail(String tipoEmail){
        this.tipoEmail = tipoEmail;
    }

    public String getTipo(){
        return tipoEmail;
    }

    public static TipoEmail emailTipo(String tipoEmail){
        return Arrays.stream(TipoEmail.values())
                .filter(tp -> tp.getTipo().equals(tipoEmail))
                .findFirst()
                .get();
    }
}
