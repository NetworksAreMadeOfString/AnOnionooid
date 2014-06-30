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

    public void addRelays(List<Relay> moreRelays)
    {
        for (Relay relay: moreRelays)
        {
            boolean dupe = false;
            for (Relay existingRelay: relays)
            {
                if(existingRelay.Fingerprint.equals(relay.Fingerprint))
                {
                    Log.e("DUPE", relay.Fingerprint);
                    dupe = true;
                    break;
                }
                else
                {
                    Log.e("DUPE", relay.Fingerprint + " does not match " + existingRelay.Fingerprint);
                }
            }

            if(!dupe)
                relays.add(relay);
        }
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
