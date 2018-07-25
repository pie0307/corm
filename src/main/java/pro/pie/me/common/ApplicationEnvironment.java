package pro.pie.me.common;


/**
 * 应用当前环境信息
 **/
public class ApplicationEnvironment {

    private static ThreadLocal<ISession> clientenvrioment = new ThreadLocal<>();

    private static ThreadLocal<String> accessTokenThreadLocal = new ThreadLocal<>();

    public static ThreadLocal<String> getAccessTokenThreadLocal() {
        return accessTokenThreadLocal;
    }

    public static ThreadLocal<ISession> getClientenvrioment() {
        return clientenvrioment;
    }

    public static void setAccessTokenThreadLocal(String accessTokenThreadLocal) {
        ApplicationEnvironment.accessTokenThreadLocal.set(accessTokenThreadLocal);
    }

    public static ISession getSession() {

        return clientenvrioment.get();
    }

    public static void setSessionVoThreadLocal(ISession sessionVoThreadLocal) {
        clientenvrioment.set(sessionVoThreadLocal);
    }

    public static String getEmpCode() {
        return clientenvrioment.get().getUserCode();
    }

    public static String getUserName() {
        return clientenvrioment.get().getUserName();
    }

    public static String getCityCode() {
        return clientenvrioment.get().getCityCode();
    }

    public static String getJobCode() {
        return clientenvrioment.get().getJobCode();
    }
}
