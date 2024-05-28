package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URI;
import java.net.URISyntaxException;

public class App {
    public static void main(String[] args) throws InterruptedException {
        final Thread thread = new Thread(() -> { 
            System.out.println(getWebpage("https://www.example.com"));
        });

        thread.start();
        System.out.println("Thread has been started");
        thread.join();
        System.out.println("Thread has finished");
    }

    private static String getWebpage(final String url) {
        String webpage = null;
        final HttpURLConnection connection = makeConnection(url);

        try (final BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            StringBuffer content = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            
            webpage = content.toString();
        } catch (IOException exception) {
            exception.printStackTrace();
        } finally {
            connection.disconnect();
        }

        return webpage;
    }

    private static HttpURLConnection makeConnection(final String url) {
        try {
            final URL formattedURL = new URI(url).toURL();
            final HttpURLConnection connection = (HttpURLConnection) formattedURL.openConnection();
            connection.setRequestMethod("GET");

            return connection;
        } catch (URISyntaxException | IOException exception) {
            exception.printStackTrace();
        }

        return null;
    }
}
