package jp.ac.doshisha.mikilab.testappksato;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class
SocketClient {
    static private InetSocketAddress endpoint;

    SocketClient(InetSocketAddress end) {
        endpoint = end;
    }

    public static void setEndpoint(InetSocketAddress end) {
        endpoint = end;
    }

    // get lights by JSON
    public static ArrayList<Light> getLights() {
        ArrayList<Light> lights = null;
        String json;

        try {
            // generate socket
            Socket socket = new Socket();
            socket.connect(endpoint);

            // setting
            OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream());
            BufferedWriter bw = new BufferedWriter(out);

            InputStreamReader in = new InputStreamReader(socket.getInputStream());
            BufferedReader br = new BufferedReader(in);

            // send command
            bw.write("GET_LIGHTS");
            bw.newLine();
            bw.flush();

            // receive message from server
            json = br.readLine();

            // close socket
            socket.close();

            // map to ArrayList from json
            ObjectMapper mapper = new ObjectMapper();
            lights = mapper.readValue(json, new TypeReference<ArrayList<Light>>() {
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lights;
    }

    public static String dimAllByLumCct(double luminosity, double cct) {
            // check
            if (luminosity < 0 || luminosity > 100) {
                return "Invalid luminosity";
            }
            if (cct < 0) {
                return "Invalid cct";
            }

            // generate command
            String cmd = "DOWNLIGHT_ALL\n";
            cmd += luminosity + "," + cct;

            return sendCommand(cmd);
    }

    public static String dimByLights(List<Light> lights) {
        // generate command
        String cmd = "DOWNLIGHT_INDIVIDUAL\n";

        for (Light light: lights) {
            cmd += String.valueOf(light.getId()) + "," +
                    String.valueOf(light.getLumPct()) + "," +
                    String.valueOf(light.getTemperature()) + ",";
        }

        return sendCommand(cmd);

    }

    public static String sendCommand(String cmd) {
        try {
            // generate socket
            Socket socket = new Socket();
            socket.connect(endpoint);

            // setting
            OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream());
            BufferedWriter bw = new BufferedWriter(out);

            InputStreamReader in = new InputStreamReader(socket.getInputStream());
            BufferedReader br = new BufferedReader(in);

            // send command
            bw.write(cmd);
            bw.newLine();
            bw.flush();

            // catch
            return br.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Error";
    }

}
