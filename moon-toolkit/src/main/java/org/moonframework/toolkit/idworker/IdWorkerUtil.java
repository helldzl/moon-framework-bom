package org.moonframework.toolkit.idworker;

/**
 * IdWorkerUtil
 * Created by lcj on 2015/10/20.
 */
public class IdWorkerUtil {

    private static IdWorker idWorker;

    public static Long nextLong(){
        return idWorker.nextId();
    }

    public static String nextString(){
        return ""+idWorker.nextId();
    }

    public static String nextHexString(){
        return Long.toHexString(idWorker.nextId());
    }

    public static String nextMongoId(){
        return String.format("%024d", idWorker.nextId());
    }

    public void setIdWorker(IdWorker idWorker) {
        IdWorkerUtil.idWorker = idWorker;
    }
}
