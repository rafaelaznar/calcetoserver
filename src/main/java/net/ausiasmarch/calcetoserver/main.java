package net.ausiasmarch.calcetoserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class main {

    public static void main(String[] args) throws InterruptedException, IOException {
        int port = 8887;
        try {
            port = Integer.parseInt(args[0]);
        } catch (NumberFormatException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        WS oWS = new WS(port);
        oWS.start();
        System.out.println("Server started on port: " + oWS.getPort());

        BufferedReader oBufferedReaderIn = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String StrIncomming = oBufferedReaderIn.readLine();
            oWS.broadcast(StrIncomming);
            if (StrIncomming.equals("exit")) {
                oWS.stop(1000);
                break;
            }
        }
    }
}
