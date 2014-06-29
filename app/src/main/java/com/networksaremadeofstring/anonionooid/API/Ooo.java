package com.networksaremadeofstring.anonionooid.API;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class Ooo
{
    public static final String ARG_ITEM_ID = "probe_fingerprint";

    public Relays getTopTen() throws JSONException, IOException
    {
        Relays topTen;

        try
        {
            JSONObject jsonObject = OooRequest("https://onionoo.torproject.org/details?type=relay&order=-consensus_weight&limit=10&fields=fingerprint,nickname,advertised_bandwidth,last_restarted,country,flags,or_addresses,dir_address,running,hashed_fingerprint&running=true");

            if(null == jsonObject)
            {
                topTen = new Relays();
            }
            else
            {
                topTen = new Relays(jsonObject);
            }
        }
        catch(Exception e)
        {
            topTen = new Relays();
        }

        return topTen;
    }

    public Relay getRelayGeneral(String fingerprint)
    {
        Relay relay;
        Log.e("Fingerprint", fingerprint);
        try
        {
            JSONObject jsonObject = OooRequest("https://onionoo.torproject.org/details?lookup=" + fingerprint);

            if(null == jsonObject)
            {
                relay = new Relay();
            }
            else
            {
                relay = new Relay(jsonObject.getJSONArray("relays").getJSONObject(0));
            }
        }
        catch(Exception e)
        {
            relay = new Relay();
        }

        return relay;

    }

    private JSONObject OooRequest(String URL)
    {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        JSONObject json;

        HttpGet httpGet = new HttpGet(URL);
        httpGet.setHeader("Accept", "application/json");

        try
        {
            HttpResponse response = httpClient.execute(httpGet);
            String rawJSON = EntityUtils.toString(response.getEntity());
            Log.e("rawJSON",rawJSON);
            response.getEntity().consumeContent();

            json = new JSONObject(rawJSON);

            return json;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
