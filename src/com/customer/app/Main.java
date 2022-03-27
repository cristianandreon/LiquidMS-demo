package com.customer.app;

import com.liquidms.JettyServer;
import com.liquid.connectionPool;
import com.liquidms.WatchDogThread;

import javax.servlet.Servlet;


public class Main {

    static boolean run = true;
    static connectionPool ds = null;

    public static void main(String[] args) throws Exception {

        System.out.println(
                        "██╗     ██╗ ██████╗ ██╗   ██╗██╗██████╗                                                    \n" +
                        "██║     ██║██╔═══██╗██║   ██║██║██╔══██╗                                                   \n" +
                        "██║     ██║██║   ██║██║   ██║██║██║  ██║                                                   \n" +
                        "██║     ██║██║▄▄ ██║██║   ██║██║██║  ██║                                                   \n" +
                        "███████╗██║╚██████╔╝╚██████╔╝██║██████╔╝                                                   \n" +
                        "╚══════╝╚═╝ ╚══▀▀═╝  ╚═════╝ ╚═╝╚═════╝                                                    \n" +
                        "                                                                                           \n" +
                        "███╗   ███╗██╗ ██████╗██████╗  ██████╗ ███████╗███████╗██████╗ ██╗   ██╗██╗ ██████╗███████╗\n" +
                        "████╗ ████║██║██╔════╝██╔══██╗██╔═══██╗██╔════╝██╔════╝██╔══██╗██║   ██║██║██╔════╝██╔════╝\n" +
                        "██╔████╔██║██║██║     ██████╔╝██║   ██║███████╗█████╗  ██████╔╝██║   ██║██║██║     █████╗  \n" +
                        "██║╚██╔╝██║██║██║     ██╔══██╗██║   ██║╚════██║██╔══╝  ██╔══██╗╚██╗ ██╔╝██║██║     ██╔══╝  \n" +
                        "██║ ╚═╝ ██║██║╚██████╗██║  ██║╚██████╔╝███████║███████╗██║  ██║ ╚████╔╝ ██║╚██████╗███████╗\n" +
                        "╚═╝     ╚═╝╚═╝ ╚═════╝╚═╝  ╚═╝ ╚═════╝ ╚══════╝╚══════╝╚═╝  ╚═╝  ╚═══╝  ╚═╝ ╚═════╝╚══════╝\n" +
                        "                                                                                           "
        );

        if(args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                if("-LiquidMS:run".equalsIgnoreCase(args[i])) {
                    System.out.println("Running server..");
                    JettyServer js = new JettyServer();
                    js.init();
                    js.addServletWithMapping((Class<? extends Servlet>) demoServlet.class, "/status");
                    js.start();
                } else if("-LiquidMS:test".equalsIgnoreCase(args[i])) {
                    if(args.length >= 2) {
                        if(args[1] != null && !args[1].isEmpty()) {
                            if("-test".equalsIgnoreCase(args[1])) {
                                // testServer();
                            }
                        }
                    }
                }
            }
        }

        // run watch dog
        System.out.println("Running watchdog..");
        WatchDogThread wd = new WatchDogThread();
        wd.start();

        while(run) {
            Thread.sleep(250);
        }
    }

    /*
    static public void testServer() throws URISyntaxException, IOException, InterruptedException {
        String url = "http://localhost:8090/status";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .headers("Host", "localhost", "Accept", "*")
                .GET()
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }
    */

}
