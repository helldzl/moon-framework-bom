package org.apache.http.examples.nlp;

import java.util.List;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/7/14
 */
public class TransEntity {

    private String from;
    private String to;
    private List<TransResult> trans_result;
    private String error_code;
    private String error_msg;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public List<TransResult> getTrans_result() {
        return trans_result;
    }

    public void setTrans_result(List<TransResult> trans_result) {
        this.trans_result = trans_result;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

}
