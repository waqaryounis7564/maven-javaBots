package services.common;

import org.openqa.selenium.remote.ErrorCodes;

public class CssDataNullException extends Exception {
    public CssDataNullException() {
        super("unable to get data, Css query not works");
    }

    CssDataNullException(String message) {
        super(message);
    }
}
