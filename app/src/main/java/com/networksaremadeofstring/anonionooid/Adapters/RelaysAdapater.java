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

package com.networksaremadeofstring.anonionooid.Adapters;

import android.content.Context;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.networksaremadeofstring.anonionooid.API.Ooo;
import com.networksaremadeofstring.anonionooid.API.Relay;
import com.networksaremadeofstring.anonionooid.API.Relays;
import com.networksaremadeofstring.anonionooid.R;
import com.networksaremadeofstring.anonionooid.cache.LocalCache;

import java.util.List;

public class RelaysAdapater extends BaseAdapter
{
    public Relays relayList = null;
    private Context mContext;
    private LocalCache lc;
    List<String> favFingerprints;
    public RelaysAdapater(Context context, Relays results)
    {
        mContext = context;
        relayList = results;
        refreshFavourites();
    }

    public RelaysAdapater(Context context)
    {
        mContext = context;
        refreshFavourites();
    }

    public void refreshFavourites()
    {
        if(null == lc)
            lc = new LocalCache(mContext);

        /*lc.open();
        favFingerprints = lc.getFavourites();
        lc.close();
        this.notifyDataSetChanged();*/
    }

    public void updateRelayList(Relays relays)
    {
        relayList = relays;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount()
    {
        return relayList.getSize();
    }

    @Override
    public Object getItem(int position)
    {
        return relayList.getRelay(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Relay relay = relayList.getRelay(position);

        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.relay_grid, null);
        }

        Log.e("Adapater", relay.Fingerprint);

        if(null != relay)
        {
            String fing = "";
            try
            {
                fing = relay.Fingerprint.substring(0, 10) + "\n";
                fing += relay.Fingerprint.substring(10, 20) + "\n";
                fing += relay.Fingerprint.substring(20, 30) + "\n";
                fing += relay.Fingerprint.substring(30);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                fing = "Unknown Fingerprint";
            }

            lc.open();
            if(lc.isAFavourite(relay.Fingerprint))
            {
                convertView.findViewById(R.id.favIcon).setVisibility(View.VISIBLE);
            }
            else
            {
                convertView.findViewById(R.id.favIcon).setVisibility(View.GONE);
            }
            lc.close();

            ((TextView) convertView.findViewById(R.id.fingerprintTV)).setText(fing);

            try
            {
                ((TextView) convertView.findViewById(R.id.nicknameTV)).setText(relay.nickname);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                ((TextView) convertView.findViewById(R.id.nicknameTV)).setText("????");
            }

            try
            {
                ((TextView) convertView.findViewById(R.id.oraddressTV)).setText(relay.or_addresses[0]);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                ((TextView) convertView.findViewById(R.id.oraddressTV)).setText("0.0.0.0:0");
            }

            if(relay.flags.Authority)
            {
                ((ImageView) convertView.findViewById(R.id.authorityImage)).setAlpha(255);
            }
            else
            {
                ((ImageView) convertView.findViewById(R.id.authorityImage)).setAlpha(30);
            }

            if(relay.flags.BadExit)
            {
                ((ImageView) convertView.findViewById(R.id.badExitImage)).setAlpha(255);
            }
            else
            {
                ((ImageView) convertView.findViewById(R.id.badExitImage)).setAlpha(30);
            }

            if(relay.flags.BadDirectory)
            {
                ((ImageView) convertView.findViewById(R.id.badDirectoryImage)).setAlpha(255);
            }
            else
            {
                ((ImageView) convertView.findViewById(R.id.badDirectoryImage)).setAlpha(30);
            }

            if(relay.flags.Exit)
            {
                ((ImageView) convertView.findViewById(R.id.exitImage)).setAlpha(255);
            }
            else
            {
                ((ImageView) convertView.findViewById(R.id.exitImage)).setAlpha(30);
            }

            if(relay.flags.Fast)
            {
                ((ImageView) convertView.findViewById(R.id.fastImage)).setAlpha(255);
            }
            else
            {
                ((ImageView) convertView.findViewById(R.id.fastImage)).setAlpha(30);
            }

            if(relay.flags.Guard)
            {
                ((ImageView) convertView.findViewById(R.id.guardImage)).setAlpha(255);
            }
            else
            {
                ((ImageView) convertView.findViewById(R.id.guardImage)).setAlpha(30);
            }

            if(relay.flags.HSDir)
            {
                ((ImageView) convertView.findViewById(R.id.hsDirImage)).setAlpha(255);
            }
            else
            {
                ((ImageView) convertView.findViewById(R.id.hsDirImage)).setAlpha(30);
            }

            if(relay.flags.Named)
            {
                ((ImageView) convertView.findViewById(R.id.namedImage)).setAlpha(255);
            }
            else
            {
                ((ImageView) convertView.findViewById(R.id.namedImage)).setAlpha(30);
            }

            if(relay.flags.Stable)
            {
                ((ImageView) convertView.findViewById(R.id.stableImage)).setAlpha(255);
            }
            else
            {
                ((ImageView) convertView.findViewById(R.id.stableImage)).setAlpha(30);
            }

            if(relay.flags.Running)
            {
                ((ImageView) convertView.findViewById(R.id.runningImage)).setAlpha(255);
            }
            else
            {
                ((ImageView) convertView.findViewById(R.id.runningImage)).setAlpha(30);
            }

            if(relay.flags.Unnamed)
            {
                ((ImageView) convertView.findViewById(R.id.unnamedImage)).setAlpha(255);
            }
            else
            {
                ((ImageView) convertView.findViewById(R.id.unnamedImage)).setAlpha(30);
            }

            if(relay.flags.Valid)
            {
                ((ImageView) convertView.findViewById(R.id.validImage)).setAlpha(255);
            }
            else
            {
                ((ImageView) convertView.findViewById(R.id.validImage)).setAlpha(30);
            }

            if(relay.flags.V2Dir)
            {
                ((ImageView) convertView.findViewById(R.id.v2DirImage)).setAlpha(255);
            }
            else
            {
                ((ImageView) convertView.findViewById(R.id.v2DirImage)).setAlpha(30);
            }
        }

        return convertView;
    }
}
