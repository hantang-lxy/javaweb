package cn.kgc.tangcco.util.exciption;

/**
 * @author ShangYanshuo
 * @version 1.0
 * @date 2020/6/179:53
 */
public class ProcedureException extends RuntimeException{
    private static final long serialVersionUID = -218922938227601417L;

    public ProcedureException() {
        super();
    }

    public ProcedureException(String message) {
        super(message);
    }

    public ProcedureException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProcedureException(Throwable cause) {
        super(cause);
    }

    protected ProcedureException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
