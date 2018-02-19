package com.mobileia.prode.rest;

import android.content.Context;

import com.mobileia.authentication.MobileiaAuth;
import com.mobileia.authentication.core.entity.User;
import com.mobileia.core.rest.RestBuilder;
import com.mobileia.prode.MobileiaProde;

/**
 * Created by matiascamiletti on 9/2/18.
 */

class BaseRest extends RestBuilder {

    /**
     * Almacena el contexto
     */
    protected Context mContext;

    /**
     * Constructor
     * @param context
     */
    public BaseRest(Context context){
        super();
        mContext = context;
    }

    /**
     * Devuelve la URL base del prode
     * @return
     */
    @Override
    public String getBaseUrl() {
        return MobileiaProde.getBaseUrl();
    }

    /**
     * Obtiene el AccessToken del usuario logueado
     * @return
     */
    public String getAccessToken(){
        User current = MobileiaAuth.getInstance(mContext).getCurrentUser();
        if(current == null){
            return "03f7f76696e99f1fe1bb59eb494fdae37260192a";
        }
        return current.getAccessToken();
    }
}
