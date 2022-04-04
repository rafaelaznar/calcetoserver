package net.ausiasmarch.calcetoserver;

import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Collections;
import org.java_websocket.drafts.Draft;
import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
//import java.net.http.WebSocket;

public class WS extends WebSocketServer {

    public WS(int port) throws UnknownHostException {
        super(new InetSocketAddress(port));
    }

    public WS(InetSocketAddress address) {
        super(address);
    }

    public WS(int port, Draft_6455 draft) {
        super(new InetSocketAddress(port), Collections.<Draft>singletonList(draft));
    }

    @Override
    public void onStart() {
        System.out.println("Server started!");
        setConnectionLostTimeout(0);
        setConnectionLostTimeout(100);
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        broadcast("New connection: " + handshake.getResourceDescriptor());
        conn.send("Welcome!");
        System.out.println("New connection: " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        broadcast(conn + " left");
        System.out.println(conn + " left");
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        broadcast(message);
        System.out.println("msg: " + conn + ": " + message);
    }

    @Override
    public void onMessage(WebSocket conn, ByteBuffer message) {
        broadcast(message.array());
        System.out.println("msg: " + conn + ": " + message);
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        PrintWriter outFile = new PrintWriter(System.out, true);
        System.out.println("ERROR: " + ex.getMessage());
        ex.printStackTrace(outFile);
        if (conn != null) {
            System.out.println("Connection reminds open ");
        }
    }

}
