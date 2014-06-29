package com.networksaremadeofstring.anonionooid.API;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gareth on 29/06/2014.
 */
public class Relays
{
    List<Relay> relays = null;

    public Relays(JSONObject rawJSON)
    {
        try
        {
            JSONArray relaysJSON = rawJSON.getJSONArray("relays");

            int relayCount = relaysJSON.length();

            if(null == relays)
                relays = new ArrayList<Relay>();

            for(int i = 0; i < relayCount; i++)
            {
                relays.add(new Relay(relaysJSON.getJSONObject(i)));
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    public Relays()
    {
        relays = new ArrayList<Relay>();
    }

    public Relay getRelay(int index)
    {
        return relays.get(index);
    }

    public String getRelayFingerprint(int index)
    {
        return relays.get(index).Fingerprint;
    }

    public int getSize()
    {
        if(null != relays)
        {
            return relays.size();
        }
        else
        {
            return 0;
        }
    }
}
