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
package com.networksaremadeofstring.anonionooid.cache;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.networksaremadeofstring.anonionooid.API.Relay;

import java.util.ArrayList;
import java.util.List;


public class LocalCache
{
    private SQLiteDatabase database;
    private OpenHelper dbHelper;

    private String[] favRelayColumns = {"fingerprint","nickname"};

    static public int RESULT_BLOCKED = 0;
    static public int RESULT_OK = 1;
    static public int RESULT_ERROR = 2;

    public LocalCache(Context context)
    {
        dbHelper = new OpenHelper(context);
    }

    public void open() throws SQLException
    {
        if(null != dbHelper)
            database = dbHelper.getWritableDatabase();
    }

    public void close()
    {
        if(null != dbHelper)
            dbHelper.close();
    }

    public boolean addFavRelay(Relay relay)
    {
        boolean success = false;
        try
        {
            database.beginTransaction();
            ContentValues values = new ContentValues(2);
            values.put("fingerprint", relay.Fingerprint);
            values.put("nickname", relay.nickname);

            database.insert("favProbes", null, values);
            database.setTransactionSuccessful();
            success = true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            database.endTransaction();
        }

        return success;
    }

    public boolean addFavRelay(String fingerprint, String nickname)
    {
        boolean success = false;
        try
        {
            database.beginTransaction();
            ContentValues values = new ContentValues(2);
            values.put("fingerprint", fingerprint);
            values.put("nickname", nickname);

            database.insert("favProbes", null, values);
            database.setTransactionSuccessful();
            success = true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            database.endTransaction();
        }

        return success;
    }

    public boolean removeFavRelay(String fingerprint)
    {
        boolean success = false;

        try
        {
            database.beginTransaction();
            database.delete("favProbes","fingerprint=?", new String[]{fingerprint});
            database.setTransactionSuccessful();
            success = true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            database.endTransaction();
        }

        return success;
    }


    public List<String> getFavourites()
    {
        Cursor cursor = null;
        List<String> fingerprints = new ArrayList<String>();

        try
        {
            cursor = database.query("favProbes", favRelayColumns,  null, null, null, null, null);

            while(cursor.moveToNext())
            {
                fingerprints.add(cursor.getString(0));
            }

            if(null != cursor && !cursor.isClosed())
                cursor.close();
        }
        catch (Exception e)
        {
            if(null != cursor && !cursor.isClosed())
                cursor.close();

            e.printStackTrace();
            return fingerprints;
        }

        return fingerprints;
    }

    public boolean isAFavourite(String fingerprint)
    {
        Cursor cursor = null;

        try
        {
            cursor = database.query("favProbes", favRelayColumns,  "fingerprint=?", new String[]{fingerprint}, null, null, null);

            if(cursor.moveToFirst())
            {
                if(null != cursor && !cursor.isClosed())
                    cursor.close();
                return true;
            }
            else
            {
                if(null != cursor && !cursor.isClosed())
                    cursor.close();
                return false;
            }
        }
        catch (Exception e)
        {
            if(null != cursor && !cursor.isClosed())
                cursor.close();

            e.printStackTrace();
            return false;
        }
    }
}
