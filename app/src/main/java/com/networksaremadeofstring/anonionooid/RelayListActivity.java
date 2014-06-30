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

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;

import com.networksaremadeofstring.anonionooid.API.Ooo;


/**
 * An activity representing a list of Relays. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link RelayDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link RelayListFragment} and the item details
 * (if present) is a {@link RelayDetailFragment}.
 * <p>
 * This activity also implements the required
 * {@link RelayListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class RelayListActivity extends Activity
        implements RelayListFragment.Callbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getActionBar().setIcon(R.drawable.ab_icon);
        getActionBar().setSubtitle(R.string.MainSubtitle);
        setContentView(R.layout.activity_relay_list);

        if (findViewById(R.id.relay_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            /*((RelayListFragment) getFragmentManager()
                    .findFragmentById(R.id.relay_list))
                    .setActivateOnItemClick(true);*/
        }

        // TODO: If exposing deep links into your app, handle intents here.
    }

    /**
     * Callback method from {@link RelayListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(String id)
    {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(Ooo.ARG_ITEM_ID, id);
            RelayDetailFragment fragment = new RelayDetailFragment();
            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction()
                    .replace(R.id.relay_detail_container, fragment)
                    .commit();

        }
        else
        {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            //Intent detailIntent = new Intent(this, RelayDetailActivity.class);
            Intent detailIntent = new Intent(this, RelayDetailsSwipe.class);
            detailIntent.putExtra(Ooo.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }
}
