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

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class OpenHelper extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "onionooCache";


    OpenHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        try
        {
            db.execSQL("CREATE  TABLE \"favProbes\" (\"fingerprint\" TEXT PRIMARY KEY  NOT NULL  UNIQUE , \"nickname\" TEXT)");
        }
        catch(SQLiteException s)
        {
            s.printStackTrace();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.v("onUpgrade", "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
        try
        {
            db.execSQL("DROP TABLE IF EXISTS favProbes");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        onCreate(db);
    }
}
