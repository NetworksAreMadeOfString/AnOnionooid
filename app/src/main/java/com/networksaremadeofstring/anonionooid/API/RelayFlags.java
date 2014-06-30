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

public class RelayFlags
{
    public boolean Authority = false;
    public boolean BadExit  = false;
    public boolean BadDirectory   = false;
    public boolean Named = false;
    public boolean Exit = false;
    public boolean Fast = false;
    public boolean HSDir = false;
    public boolean Running = false;
    public boolean Unnamed = false;
    public boolean V2Dir = false;
    public boolean Valid = false;
    public boolean Guard = false;
    public boolean Stable = false;

    public RelayFlags(JSONArray jsonArray)
    {
        int flagCount = jsonArray.length();

        for(int i = 0; i < flagCount; i++)
        {
            try
            {
                String flag = jsonArray.getString(i);
                Flags currentFlag = Flags.valueOf(flag);

                switch (currentFlag)
                {
                    case Exit:
                    {
                        Exit = true;
                    }
                    break;

                    case Fast:
                    {
                        Fast = true;
                    }
                    break;

                    case HSDir:
                    {
                        HSDir = true;
                    }
                    break;

                    case Running:
                    {
                        Running = true;
                    }
                    break;

                    case Unnamed:
                    {
                        Unnamed = true;
                    }
                    break;

                    case V2Dir:
                    {
                        V2Dir = true;
                    }
                    break;

                    case Valid:
                    {
                        Valid = true;
                    }
                    break;

                    case Guard:
                    {
                        Guard = true;
                    }
                    break;

                    case Stable:
                    {
                        Stable = true;
                    }
                    break;

                    case Authority :
                    {
                        Authority  = true;
                    }
                    break;

                    case BadExit :
                    {
                        BadExit  = true;
                    }
                    break;

                    case BadDirectory :
                    {
                        BadDirectory  = true;
                    }
                    break;

                    case Named :
                    {
                        Named  = true;
                    }
                    break;
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    private enum Flags {
        Authority,
        BadExit,
        BadDirectory,
        Named,
        Exit,
        Fast,
        HSDir,
        Running,
        Unnamed,
        V2Dir,
        Valid,
        Guard,
        Stable
    }


}
