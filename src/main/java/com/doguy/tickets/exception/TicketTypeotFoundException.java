package com.doguy.tickets.exception;

public class TicketTypeotFoundException extends EventTicketException {
    public TicketTypeotFoundException() {

    }

    public TicketTypeotFoundException(String message) {
        super(message);
    }

    public TicketTypeotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public TicketTypeotFoundException(Throwable cause) {
        super(cause);
    }

    public TicketTypeotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
