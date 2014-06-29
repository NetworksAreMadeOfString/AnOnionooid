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

import com.networksaremadeofstring.anonionooid.API.Relay;
import com.networksaremadeofstring.anonionooid.API.Relays;
import com.networksaremadeofstring.anonionooid.R;

public class RelaysAdapater extends BaseAdapter
{
    public Relays relayList = null;
    private Context mContext;

    public RelaysAdapater(Context context, Relays results)
    {
        mContext = context;
        relayList = results;
    }

    public RelaysAdapater(Context context)
    {
        mContext = context;
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
            fing = relay.Fingerprint.substring(0,10) + "\n";
            fing += relay.Fingerprint.substring(10,20)  + "\n";
            fing += relay.Fingerprint.substring(20,30)  + "\n";
            fing += relay.Fingerprint.substring(30);

            ((TextView) convertView.findViewById(R.id.fingerprintTV)).setText(fing);
            ((TextView) convertView.findViewById(R.id.nicknameTV)).setText(relay.nickname);
            ((TextView) convertView.findViewById(R.id.oraddressTV)).setText(relay.or_addresses[0]);

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
