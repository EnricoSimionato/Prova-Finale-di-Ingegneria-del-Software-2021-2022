package it.polimi.ingsw.model.exception;

import javax.net.ssl.ExtendedSSLSession;

public class EffectCannotBeActivatedException extends Exception {
    public EffectCannotBeActivatedException(String message) {
        super(message);
    }
}
