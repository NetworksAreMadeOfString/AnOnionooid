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
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;
import com.networksaremadeofstring.anonionooid.API.Ooo;
import org.json.JSONObject;


public class RelayGraphDetailsFragment extends Fragment
{
    Ooo API;
    LinearLayout graphContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_details_swipe_graphs, container, false);
        graphContainer = (LinearLayout) rootView.findViewById(R.id.graphContainer);
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
        try {
            graphContainer.removeAllViews();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        new AsyncTask<Void, Void, JSONObject>()
        {
            @Override
            protected JSONObject doInBackground(Void... params)
            {
                try
                {
                    return API.getRelayGraphs(getArguments().getString(Ooo.ARG_ITEM_ID));
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(JSONObject graphDetails)
            {
                //3 Day View
                try
                {
                    int writeValueCount = graphDetails.getJSONObject("write_history").getJSONObject("3_days").getJSONArray("values").length();
                    GraphView.GraphViewData[] writeValues = new GraphView.GraphViewData[writeValueCount];
                    for (int i = 0; i < writeValueCount; i++)
                    {
                        writeValues[i] = new GraphView.GraphViewData(i, graphDetails.getJSONObject("write_history").getJSONObject("3_days").getJSONArray("values").getInt(i));
                    }
                    GraphViewSeries writeSeries = new GraphViewSeries("Write Rate", new GraphViewSeries.GraphViewSeriesStyle(Color.rgb(31, 119, 180),1),writeValues);

                    int readValueCount = graphDetails.getJSONObject("read_history").getJSONObject("3_days").getJSONArray("values").length();
                    GraphView.GraphViewData[] readValues = new GraphView.GraphViewData[readValueCount];
                    for (int i = 0; i < readValueCount; i++)
                    {
                        readValues[i] = new GraphView.GraphViewData(i, graphDetails.getJSONObject("read_history").getJSONObject("3_days").getJSONArray("values").getInt(i));
                    }
                    GraphViewSeries readSeries = new GraphViewSeries("Read Rate",new GraphViewSeries.GraphViewSeriesStyle(Color.rgb(255, 127, 14),1),readValues);

                    GraphView graphView = new LineGraphView(getActivity(), "3 Day Bandwidth Activity");
                    graphView.addSeries(writeSeries);
                    graphView.addSeries(readSeries);
                    graphView.setMinimumHeight(400);
                    graphView.setMinimumWidth(200);
                    graphView.setShowLegend(true);
                    graphView.setScalable(true);
                    graphView.setHorizontalLabels(new String[]{graphDetails.getJSONObject("read_history").getJSONObject("3_days").getString("first"),graphDetails.getJSONObject("read_history").getJSONObject("3_days").getString("last")});
                    graphContainer.addView(graphView);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                //1 week View
                try
                {
                    int writeValueCount = graphDetails.getJSONObject("write_history").getJSONObject("1_week").getJSONArray("values").length();
                    GraphView.GraphViewData[] writeValues = new GraphView.GraphViewData[writeValueCount];
                    for (int i = 0; i < writeValueCount; i++)
                    {
                        writeValues[i] = new GraphView.GraphViewData(i, graphDetails.getJSONObject("write_history").getJSONObject("1_week").getJSONArray("values").getInt(i));
                    }
                    GraphViewSeries writeSeries = new GraphViewSeries("Write Rate", new GraphViewSeries.GraphViewSeriesStyle(Color.rgb(31, 119, 180),1),writeValues);

                    int readValueCount = graphDetails.getJSONObject("read_history").getJSONObject("1_week").getJSONArray("values").length();
                    GraphView.GraphViewData[] readValues = new GraphView.GraphViewData[readValueCount];
                    for (int i = 0; i < readValueCount; i++)
                    {
                        readValues[i] = new GraphView.GraphViewData(i, graphDetails.getJSONObject("read_history").getJSONObject("1_week").getJSONArray("values").getInt(i));
                    }
                    GraphViewSeries readSeries = new GraphViewSeries("Read Rate",new GraphViewSeries.GraphViewSeriesStyle(Color.rgb(255, 127, 14),1),readValues);

                    GraphView graphView = new LineGraphView(getActivity(), "1 Week Bandwidth Activity");
                    graphView.addSeries(writeSeries);
                    graphView.addSeries(readSeries);
                    graphView.setMinimumHeight(400);
                    graphView.setMinimumWidth(200);
                    graphView.setShowLegend(true);
                    graphView.setHorizontalLabels(new String[]{graphDetails.getJSONObject("read_history").getJSONObject("1_week").getString("first"),graphDetails.getJSONObject("read_history").getJSONObject("1_week").getString("last")});
                    graphView.setScalable(true);

                    graphContainer.addView(graphView);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                //1 Month View
                try
                {
                    int writeValueCount = graphDetails.getJSONObject("write_history").getJSONObject("1_month").getJSONArray("values").length();
                    GraphView.GraphViewData[] writeValues = new GraphView.GraphViewData[writeValueCount];
                    for (int i = 0; i < writeValueCount; i++)
                    {
                        writeValues[i] = new GraphView.GraphViewData(i, graphDetails.getJSONObject("write_history").getJSONObject("1_month").getJSONArray("values").getInt(i));
                    }
                    GraphViewSeries writeSeries = new GraphViewSeries("Write Rate", new GraphViewSeries.GraphViewSeriesStyle(Color.rgb(31, 119, 180),1),writeValues);

                    int readValueCount = graphDetails.getJSONObject("read_history").getJSONObject("1_month").getJSONArray("values").length();
                    GraphView.GraphViewData[] readValues = new GraphView.GraphViewData[readValueCount];
                    for (int i = 0; i < readValueCount; i++)
                    {
                        readValues[i] = new GraphView.GraphViewData(i, graphDetails.getJSONObject("read_history").getJSONObject("1_month").getJSONArray("values").getInt(i));
                    }
                    GraphViewSeries readSeries = new GraphViewSeries("Read Rate",new GraphViewSeries.GraphViewSeriesStyle(Color.rgb(255, 127, 14),1),readValues);

                    GraphView graphView = new LineGraphView(getActivity(), "1 Month Bandwidth Activity");
                    graphView.addSeries(writeSeries);
                    graphView.addSeries(readSeries);
                    graphView.setMinimumHeight(400);
                    graphView.setMinimumWidth(200);
                    graphView.setShowLegend(true);
                    graphView.setScalable(true);
                    graphView.setHorizontalLabels(new String[]{graphDetails.getJSONObject("read_history").getJSONObject("1_month").getString("first"),graphDetails.getJSONObject("read_history").getJSONObject("1_month").getString("last")});
                    graphContainer.addView(graphView);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }.execute(null, null, null);
    }
}
