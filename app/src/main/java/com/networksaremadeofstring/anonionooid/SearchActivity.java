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

import android.app.Activity;
import android.app.Fragment;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.FrameLayout;
import android.widget.SearchView;

import com.networksaremadeofstring.anonionooid.API.Ooo;


/**
 * An activity representing a list of Relays. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link com.networksaremadeofstring.anonionooid.RelayDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link com.networksaremadeofstring.anonionooid.RelayListFragment} and the item details
 * (if present) is a {@link com.networksaremadeofstring.anonionooid.RelayDetailFragment}.
 * <p>
 * This activity also implements the required
 * {@link com.networksaremadeofstring.anonionooid.RelayListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class SearchActivity extends Activity implements RelayListFragment.Callbacks
{

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private String searchTerm = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getActionBar().setIcon(R.drawable.ab_icon);
        getActionBar().setSubtitle(R.string.MainSubtitle);
        //setContentView(R.layout.activity_relay_list);
        setContentView(R.layout.activity_search);

        handleIntent(getIntent());

        Fragment listFragment = new RelayListFragment();
        Bundle searchTermBundle = new Bundle();
        searchTermBundle.putString(Ooo.ARG_SEARCH,searchTerm);
        listFragment.setArguments(searchTermBundle);
        getFragmentManager().beginTransaction()
                .add(R.id.fragmentHolder, listFragment)
                .commit();

        /*if (findViewById(R.id.relay_detail_container) != null)
        {
            mTwoPane = true;
        }*/

    }

    @Override
    protected void onNewIntent(Intent intent)
    {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction()))
        {
            searchTerm = intent.getStringExtra(SearchManager.QUERY);
        }
    }

    /**
     * Callback method from {@link com.networksaremadeofstring.anonionooid.RelayListFragment.Callbacks}
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

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }*/
}
