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

import org.json.JSONArray;
import org.json.JSONObject;

public class Relay
{
    public String Fingerprint = "xyz";
    public String nickname = "";
    public String[] or_addresses = null;
    public String dir_address = null;
    public boolean running = false;
    public RelayFlags flags = null;
    public String country = "";
    public String countryName = "";
    public String last_restarted = "";
    public int advertised_bandwidth = 0;
    public String contact;
    public String platform;
    public String asn;
    public String asnName;
    public String[] exitPolicy = null;

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

        try
        {
            if (json.has("country"))
                country = json.getString("country");
        }
        catch (Exception e)
        {
            country = "UNKNOWN";
        }

        try
        {
            if (json.has("country_name"))
                countryName = json.getString("country_name");
        }
        catch (Exception e)
        {
            countryName = "UNKNOWN";
        }

        try
        {
            if (json.has("as_number"))
                asn = json.getString("as_number");
        }
        catch (Exception e)
        {
            asn = "UNKNOWN";
        }

        try
        {
            if (json.has("as_name"))
                asnName = json.getString("as_name");
        }
        catch (Exception e)
        {
            asnName = "UNKNOWN";
        }

        try
        {
            if (json.has("platform"))
                platform = json.getString("platform");
        }
        catch (Exception e)
        {
            platform = "UNKNOWN";
        }

        try
        {
            if (json.has("exit_policy"))
            {
                JSONArray exitPolicyJSON = json.getJSONArray("exit_policy");
                int count = exitPolicyJSON.length();

                exitPolicy = new String[count];
                for (int i = 0; i < count; i++)
                {
                    exitPolicy[i] = exitPolicyJSON.getString(i);
                }
            }
        }
        catch (Exception e)
        {
            exitPolicy = null;
        }
    }
}
