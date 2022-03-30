package com.customer.app;

import com.liquidms.LiquidMS;

public class Main {

    public static void main(String[] args) throws Exception {

        try {

            // Create pooled connection
            com.liquid.connection.addLiquidDBConnection("postgres", null, null, "LiquidX", "liquid", "liquid", true);

            // Register servlet
            LiquidMS.addServlet(demoServlet.class, "/status");

            // Run service
            LiquidMS.run(args);

        } catch (Throwable e) {
            System.err.println("Connection error:"+e.getMessage());
        }

    }

}
