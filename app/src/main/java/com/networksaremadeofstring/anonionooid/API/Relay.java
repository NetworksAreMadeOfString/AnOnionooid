package com.networksaremadeofstring.anonionooid.API;

import org.json.JSONObject;

/**
 {
 "nickname": "TorLand1",
 "fingerprint": "E1E922A20AF608728824A620BADC6EFC8CB8C2B8",
 "or_addresses": [
 "37.130.227.133:443",
 "[2a02:2498:e001:3c::133]:443"
 ],
 "dir_address": "37.130.227.133:80",
 "running": true,
 "flags": [
 "Exit",
 "Fast",
 "HSDir",
 "Running",
 "Unnamed",
 "V2Dir",
 "Valid"
 ],
 "country": "gb",
 "last_restarted": "2014-06-05 20:17:37",
 "advertised_bandwidth": 49453858
 }
 */
public class Relay
{
    public String Fingerprint = "";
    public String nickname = "";
    public String[] or_addresses = null;
    public String dir_address = null;
    public boolean running = false;
    public RelayFlags flags = null;
    public String country = "";
    public String last_restarted = "";
    public int advertised_bandwidth = 0;
    public String contact;

    public Relay () { }

    public Relay (JSONObject json)
    {
        try
        {
            if (json.has("fingerprint"))
                Fingerprint = json.getString("fingerprint");
        }
        catch (Exception e)
        {
            Fingerprint = "UNKNOWN";
        }

        try
        {
            if (json.has("nickname"))
                nickname = json.getString("nickname");
        }
        catch (Exception e)
        {
            nickname = "UNKNOWN";
        }

        try
        {
            if (json.has("contact"))
                contact = json.getString("contact");
        }
        catch (Exception e)
        {
            contact = "UNKNOWN";
        }

        try
        {
            if (json.has("or_addresses"))
            {
                int addressCount = json.getJSONArray("or_addresses").length();
                or_addresses = new String[addressCount];

                for(int i = 0; i < addressCount; i++)
                {
                    or_addresses[i] = json.getJSONArray("or_addresses").getString(i);
                }
            }
        }
        catch (Exception e)
        {
            or_addresses = null;
        }

        try
        {
            if (json.has("dir_address"))
                dir_address = json.getString("dir_address");
        }
        catch (Exception e)
        {
            dir_address = "N/A";
        }

        try
        {
            if (json.has("running"))
                running = json.getBoolean("running");
        }
        catch (Exception e)
        {
            running = false;
        }


        try
        {
            if (json.has("flags"))
                flags = new RelayFlags(json.getJSONArray("flags"));
        }
        catch (Exception e)
        {
            flags = null;
        }


        try
        {
            if (json.has("country"))
                country = json.getString("country");
        }
        catch (Exception e)
        {
            country = "??";
        }

        try
        {
            if (json.has("last_restarted"))
                last_restarted = json.getString("last_restarted");
        }
        catch (Exception e)
        {
            last_restarted = "UNKNOWN";
        }

        try
        {
            if (json.has("advertised_bandwidth"))
                advertised_bandwidth = json.getInt("advertised_bandwidth");
        }
        catch (Exception e)
        {
            advertised_bandwidth = 0;
        }
    }
}
