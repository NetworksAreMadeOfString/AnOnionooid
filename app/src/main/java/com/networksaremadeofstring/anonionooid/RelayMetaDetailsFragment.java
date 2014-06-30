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
package com.networksaremadeofstring.anonionooid;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.networksaremadeofstring.anonionooid.API.Ooo;
import com.networksaremadeofstring.anonionooid.API.Relay;

import java.text.NumberFormat;


public class RelayMetaDetailsFragment extends Fragment
{
    Ooo API;
    TextView platform,country,asn,bandwidth,exitpolicy;
    View progressBar;
    View rootView;
    TableLayout exitPolicyTable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_details_swipe_meta, container, false);

        platform = (TextView) rootView.findViewById(R.id.platformTV);
        country = (TextView) rootView.findViewById(R.id.countryTV);
        asn = (TextView) rootView.findViewById(R.id.asnTV);
        bandwidth = (TextView) rootView.findViewById(R.id.bandwidthTV);
        exitpolicy = (TextView) rootView.findViewById(R.id.exitPolicyStatement);
        progressBar = rootView.findViewById(R.id.progressBar);
        exitPolicyTable = (TableLayout) rootView.findViewById(R.id.exitPolicyTable);
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        API = new Ooo();
    }

    @Override
    public void onResume()
    {
        super.onResume();

        new AsyncTask<Void, Void, Relay>() {
            @Override
            protected Relay doInBackground(Void... params) {
                try {
                    return API.getRelayGeneral(getArguments().getString(Ooo.ARG_ITEM_ID));
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Relay relay)
            {
                platform.setText(relay.platform);
                country.setText(relay.countryName);
                asn.setText(relay.asn + " / " + relay.asnName);

                float bw = relay.advertised_bandwidth / 1000;
                NumberFormat formatter = NumberFormat.getNumberInstance();
                formatter.setMinimumFractionDigits(2);
                formatter.setMaximumFractionDigits(2);
                if(bw > 1000)
                {
                    bw = bw / 1000;
                    bandwidth.setText(formatter.format(bw) + "MB/s");
                }
                else
                {
                    bandwidth.setText(formatter.format(bw) + "kB/s");
                }

                if(relay.flags.Exit)
                {
                    exitpolicy.setText("accept");
                    exitpolicy.setTextColor(getResources().getColor(R.color.torGreen));
                }
                else
                {
                    exitpolicy.setText("deny");
                    exitpolicy.setTextColor(getResources().getColor(R.color.torRed));
                }
                int exitPolicyCount = relay.exitPolicy.length;

                //Just in case
                exitPolicyTable.removeAllViews();

                for (int i = 0; i < exitPolicyCount; i++)
                {
                    TableRow tr1 = new TableRow(getActivity());
                    tr1.setLayoutParams(new TableRow.LayoutParams( ViewGroup.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                    TextView textview = new TextView(getActivity());
                    textview.setText(relay.exitPolicy[i]);
                    textview.setTypeface(Typeface.create("sans-serif-condensed", Typeface.NORMAL));
                    if(relay.exitPolicy[i].contains("accept"))
                    {
                        textview.setTextColor(getResources().getColor(R.color.torGreen));
                    }
                    else
                    {
                        textview.setTextColor(getResources().getColor(R.color.torRed));
                    }
                    tr1.addView(textview);
                    exitPolicyTable.addView(tr1);
                }
                progressBar.setVisibility(View.GONE);
            }
        }.execute(null, null, null);
    }
}
