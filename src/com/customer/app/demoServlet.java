package com.customer.app;

import com.liquidms.LiquidMS;
import com.liquid.*;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class demoServlet implements Servlet {

    private static String HEAVY_RESOURCE = "Welcome in Liquid MicroService ver."+ LiquidMS.version;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        System.out.println("init");
        //
        // Create pooled connection
        //
        try {
            com.liquid.connection.addLiquidDBConnection("postgres", null, null, "LiquidX", "liquid", "liquid", true);
        } catch (Throwable e) {
            System.err.println("Connection error:"+e.getMessage());
        }
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        String result = "{";
        result += "\"header\":\""+HEAVY_RESOURCE+"\"";

        System.out.println("service");

        //
        // Read DB using Liquid
        //
        result += ",\"Settings\":[";
        ArrayList<Object> beans = bean.load_beans("liquidx.settings", "prop, value", "1=1");
        for (int i = 0; i < beans.size(); i++) {
            String prop = (String)utility.getEx( beans.get(i), "prop");
            Object value = utility.getEx( beans.get(i), "value");
            result += i > 0 ? "," : "";
            result += "{\""+prop+"\":\""+value+"\"}";
        }
        result += "]";

        result += "}";


        ByteBuffer content = ByteBuffer.wrap(result.getBytes(StandardCharsets.UTF_8));

        AsyncContext async = request.startAsync();
        ServletOutputStream out = response.getOutputStream();
        out.setWriteListener(new WriteListener() {
            @Override
            public void onWritePossible() throws IOException {
                while (out.isReady()) {
                    if (!content.hasRemaining()) {
                        ((HttpServletResponse)response).setStatus(HttpServletResponse.SC_OK);
                        ((HttpServletResponse)response).setContentType("application/json");
                        async.complete();
                        return;
                    }
                    out.write(content.get());
                }
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Async Error"+t.getMessage());
                async.complete();
            }
        });
    }

    @Override
    public String getServletInfo() {
        return "V1.0";
    }

    @Override
    public void destroy() {
        System.out.println("destroy");
    }
}
