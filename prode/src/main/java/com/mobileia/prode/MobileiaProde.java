package com.mobileia.prode;

/**
 * Created by matiascamiletti on 9/2/18.
 */

public class MobileiaProde {

    public static String sBaseUrl;

    /**
     * Inicializa el modulo de trivia con la URL base
     * @param url
     */
    public static void init(String url){
        sBaseUrl = url;
    }

    /**
     * Devuelve la URL base configurada
     * @return
     */
    public static String getBaseUrl(){
        return sBaseUrl;
    }
}
