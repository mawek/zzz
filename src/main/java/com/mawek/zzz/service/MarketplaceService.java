package com.mawek.zzz.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mawek.zzz.model.Loan;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.List;

import static org.apache.commons.io.IOUtils.closeQuietly;

public class MarketplaceService {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();


    public List<Loan> getLoans() {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet("https://api.zonky.cz/loans/marketplace");
            httpget.addHeader("X-Order", "-datePublished");

            System.out.println("Executing request " + httpget.getRequestLine());

            // Create a custom response handler
            ResponseHandler<List<Loan>> responseHandler = response -> {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();

                    return entity != null ? OBJECT_MAPPER.readValue(EntityUtils.toString(entity), new TypeReference<List<Loan>>() {
                    }) : null;
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            };

            return httpclient.execute(httpget, responseHandler);

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            closeQuietly(httpclient);
        }
    }
}
