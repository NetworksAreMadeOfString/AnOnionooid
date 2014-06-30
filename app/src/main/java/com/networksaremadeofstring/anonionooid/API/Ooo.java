/*
* Copyright (C) 2014 - Gareth Llewellyn
*
* This file is part of AnOnionooid - https://networksaremadeofstring.com/anonionooid/
*
* This program is free software: you can redistribute it and/or modify it
* under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful, but WITHOUT
* ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
* FOR A PARTICULAR PURPOSE. See the GNU General Public License
* for more details.
*
* You should have received a copy of the GNU General Public License along with
* this program. If not, see <http://www.gnu.org/licenses/>
*/
package com.networksaremadeofstring.anonionooid.API;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Ooo
{
    public static final String ARG_ITEM_ID = "probe_fingerprint";
    public static final String ARG_relay_fingerprint = "relay_fingerprint";
    public static final String ARG_relay_nickname = "relay_nickname";

    public Relays getLanding(List<String> favourites)
    {
        Relays allRelays = null;

        try
        {
            allRelays = getTopTen();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        if(null == allRelays)
            allRelays = new Relays();

        allRelays.addRelays(getRelays(favourites));

        return allRelays;
    }

    public List<Relay> getRelays(List<String> fingerprints)
    {
        List<Relay> relays = new ArrayList<Relay>();

        for(String fingerprint: fingerprints)
        {
            try
            {
                JSONObject jsonObject = OooRequest("https://onionoo.torproject.org/details?fingerprint=" + fingerprint);

                if (null != jsonObject)
                {
                    relays.add(new Relay(jsonObject.getJSONArray("relays").getJSONObject(0)));
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        return relays;
    }

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

    public JSONObject getRelayGraphs(String fingerprint)
    {
        try
        {
            JSONObject jsonObject = OooRequest("https://onionoo.torproject.org/bandwidth?lookup=" + fingerprint);

            if(null == jsonObject)
            {
                return null;
            }
            else
            {
                return jsonObject.getJSONArray("relays").getJSONObject(0);
            }

        }
        catch(Exception e)
        {
            return null;
        }
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
            //Log.e("rawJSON",rawJSON);
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
