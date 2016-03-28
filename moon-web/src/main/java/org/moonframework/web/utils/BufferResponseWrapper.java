package org.moonframework.web.utils;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * Created by lcj on 2016/1/11.
 */
public class BufferResponseWrapper extends HttpServletResponseWrapper {

    private ByteArrayOutputStream bout = new ByteArrayOutputStream();
    private PrintWriter pw;
    private HttpServletResponse response;

    public BufferResponseWrapper(HttpServletResponse response) {
        super(response);
        this.response = response;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return new BufferResponseOutputStream(bout);
    }

    @Override
    public PrintWriter getWriter() throws IOException {

        if(pw == null) {
            pw = new PrintWriter(new OutputStreamWriter(bout, this.response.getCharacterEncoding()));
        }
        return pw;
    }

    public byte[] getBuffer(){
        try{
            if(pw!=null){
                pw.close();
            }
            if(bout!=null){
                bout.flush();
                return bout.toByteArray();
            }
            return null;
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static class BufferResponseOutputStream extends ServletOutputStream{
        private ByteArrayOutputStream bout;
        public BufferResponseOutputStream(ByteArrayOutputStream bout){
            this.bout = bout;
        }

        @Override
        public void write(int b) throws IOException {
            this.bout.write(b);
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setWriteListener(WriteListener writeListener) {

        }
    }
}
