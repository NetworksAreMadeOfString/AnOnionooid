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
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.networksaremadeofstring.anonionooid.API.Ooo;
import com.networksaremadeofstring.anonionooid.API.Relay;
import com.networksaremadeofstring.anonionooid.cache.LocalCache;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.Duration;


public class RelayGeneralDetailsFragment extends Fragment
{
    Ooo API;
    TextView nickname,uptime,or_address,contact,running;
    View progressBar;
    View rootView;
    Animation shakeIt;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        API = new Ooo();
        shakeIt = AnimationUtils.loadAnimation(getActivity(), R.anim.wobble);
    }

    @Override
    public void onResume()
    {
        super.onResume();

        new AsyncTask<Void, Void, Relay>()
        {
            @Override
            protected Relay doInBackground(Void... params)
            {
                try
                {
                    return API.getRelayGeneral(getArguments().getString(Ooo.ARG_ITEM_ID));
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Relay relay)
            {
                nickname.setText(relay.nickname);

                try
                {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date startTime = sdf.parse(relay.last_restarted);
                    Date now = new Date();

                    long diff = now.getTime() - startTime.getTime();
                    String time = "";
                    long x = diff / 1000;
                    long seconds = x % 60;
                    x /= 60;
                    long minutes = x % 60;
                    x /= 60;
                    long hours = x % 24;
                    x /= 24;
                    long days = x;
                    if(days > 1)
                        time = Long.toString(days) + "days ";

                    if(hours > 1)
                        time += Long.toString(hours) + "hours ";

                    time += Long.toString(minutes) + "mins ";

                    uptime.setText(time);
                }
                catch (Exception e)
                {
                    uptime.setText(relay.last_restarted);
                }

                String addresses = "";
                for(String address: relay.or_addresses)
                {
                    addresses += address +"\n";
                }

                or_address.setText(addresses);

                contact.setText(relay.contact);
                contact.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(),getString(R.string.tapContact),Toast.LENGTH_SHORT).show();
                    }
                });

                if(relay.running)
                {
                    running.setText("true");
                    running.setTextColor(getResources().getColor(R.color.torGreen));
                }
                else
                {
                    running.setText("false");
                    running.setTextColor(getResources().getColor(R.color.torRed));
                }

                if(relay.flags.Authority)
                {
                    ((ImageView) rootView.findViewById(R.id.authorityImage)).setAlpha(255);
                }
                else
                {
                    ((ImageView) rootView.findViewById(R.id.authorityImage)).setAlpha(30);
                }

                if(relay.flags.BadExit)
                {
                    ((ImageView) rootView.findViewById(R.id.badExitImage)).setAlpha(255);
                }
                else
                {
                    ((ImageView) rootView.findViewById(R.id.badExitImage)).setAlpha(30);
                }

                if(relay.flags.BadDirectory)
                {
                    ((ImageView) rootView.findViewById(R.id.badDirectoryImage)).setAlpha(255);
                }
                else
                {
                    ((ImageView) rootView.findViewById(R.id.badDirectoryImage)).setAlpha(30);
                }

                if(relay.flags.Exit)
                {
                    ((ImageView) rootView.findViewById(R.id.exitImage)).setAlpha(255);
                }
                else
                {
                    ((ImageView) rootView.findViewById(R.id.exitImage)).setAlpha(30);
                }

                if(relay.flags.Fast)
                {
                    ((ImageView) rootView.findViewById(R.id.fastImage)).setAlpha(255);
                }
                else
                {
                    ((ImageView) rootView.findViewById(R.id.fastImage)).setAlpha(30);
                }

                if(relay.flags.Guard)
                {
                    ((ImageView) rootView.findViewById(R.id.guardImage)).setAlpha(255);
                }
                else
                {
                    ((ImageView) rootView.findViewById(R.id.guardImage)).setAlpha(30);
                }

                if(relay.flags.HSDir)
                {
                    ((ImageView) rootView.findViewById(R.id.hsDirImage)).setAlpha(255);
                }
                else
                {
                    ((ImageView) rootView.findViewById(R.id.hsDirImage)).setAlpha(30);
                }

                if(relay.flags.Named)
                {
                    ((ImageView) rootView.findViewById(R.id.namedImage)).setAlpha(255);
                }
                else
                {
                    ((ImageView) rootView.findViewById(R.id.namedImage)).setAlpha(30);
                }

                if(relay.flags.Stable)
                {
                    ((ImageView) rootView.findViewById(R.id.stableImage)).setAlpha(255);
                }
                else
                {
                    ((ImageView) rootView.findViewById(R.id.stableImage)).setAlpha(30);
                }

                if(relay.flags.Running)
                {
                    ((ImageView) rootView.findViewById(R.id.runningImage)).setAlpha(255);
                }
                else
                {
                    ((ImageView) rootView.findViewById(R.id.runningImage)).setAlpha(30);
                }

                if(relay.flags.Unnamed)
                {
                    ((ImageView) rootView.findViewById(R.id.unnamedImage)).setAlpha(255);
                }
                else
                {
                    ((ImageView) rootView.findViewById(R.id.unnamedImage)).setAlpha(30);
                }

                if(relay.flags.Valid)
                {
                    ((ImageView) rootView.findViewById(R.id.validImage)).setAlpha(255);
                }
                else
                {
                    ((ImageView) rootView.findViewById(R.id.validImage)).setAlpha(30);
                }

                if(relay.flags.V2Dir)
                {
                    ((ImageView) rootView.findViewById(R.id.v2DirImage)).setAlpha(255);
                }
                else
                {
                    ((ImageView) rootView.findViewById(R.id.v2DirImage)).setAlpha(30);
                }

                progressBar.setVisibility(View.GONE);
            }
        }.execute(null, null, null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_details_swipe_general, container, false);

        nickname = (TextView) rootView.findViewById(R.id.nicknameTV);
        uptime = (TextView) rootView.findViewById(R.id.uptimeTV);
        or_address = (TextView) rootView.findViewById(R.id.oraddressTV);
        contact = (TextView) rootView.findViewById(R.id.contactTV);
        running = (TextView) rootView.findViewById(R.id.runningTV);
        progressBar = rootView.findViewById(R.id.progressBar);

        rootView.findViewById(R.id.authorityImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(shakeIt);
                Toast.makeText(getActivity(),getString(R.string.tapAuthority),Toast.LENGTH_SHORT).show();
            }
        });

        rootView.findViewById(R.id.badExitImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(shakeIt);
                Toast.makeText(getActivity(),getString(R.string.tapBadExit),Toast.LENGTH_SHORT).show();
            }
        });

        rootView.findViewById(R.id.badDirectoryImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(shakeIt);
                Toast.makeText(getActivity(),getString(R.string.tapBadDirectory),Toast.LENGTH_SHORT).show();
            }
        });

        rootView.findViewById(R.id.exitImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(shakeIt);
                Toast.makeText(getActivity(),getString(R.string.tapExit),Toast.LENGTH_SHORT).show();
            }
        });

        rootView.findViewById(R.id.fastImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(shakeIt);
                Toast.makeText(getActivity(),getString(R.string.tapFast),Toast.LENGTH_SHORT).show();
            }
        });

        rootView.findViewById(R.id.guardImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(shakeIt);
                Toast.makeText(getActivity(),getString(R.string.tapGuard),Toast.LENGTH_SHORT).show();
            }
        });

        rootView.findViewById(R.id.hsDirImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(shakeIt);
                Toast.makeText(getActivity(),getString(R.string.tapHSDir),Toast.LENGTH_SHORT).show();
            }
        });

        rootView.findViewById(R.id.namedImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(shakeIt);
                Toast.makeText(getActivity(),getString(R.string.tapNamed),Toast.LENGTH_SHORT).show();
            }
        });

        rootView.findViewById(R.id.stableImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(shakeIt);
                Toast.makeText(getActivity(),getString(R.string.tapStable),Toast.LENGTH_SHORT).show();
            }
        });

        rootView.findViewById(R.id.runningImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(shakeIt);
                Toast.makeText(getActivity(),getString(R.string.tapRunning),Toast.LENGTH_SHORT).show();
            }
        });

        rootView.findViewById(R.id.unnamedImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(shakeIt);
                Toast.makeText(getActivity(),getString(R.string.tapUnnamed),Toast.LENGTH_SHORT).show();
            }
        });

        rootView.findViewById(R.id.validImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(shakeIt);
                Toast.makeText(getActivity(),getString(R.string.tapValid),Toast.LENGTH_SHORT).show();
            }
        });

        rootView.findViewById(R.id.v2DirImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(shakeIt);
                Toast.makeText(getActivity(),getString(R.string.tapV2Dir),Toast.LENGTH_SHORT).show();
            }
        });


        return rootView;
    }
}
