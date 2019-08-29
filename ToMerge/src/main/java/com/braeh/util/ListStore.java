package com.braeh.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ListStore
{
    private File storgaeFile;
    private ArrayList<String> values;

    public ListStore(File file)
    {
        this.storgaeFile = file;
        this.values = new ArrayList();
        if (!this.storgaeFile.exists()) {
            try
            {
                this.storgaeFile.createNewFile();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void load()
    {
        try
        {
            DataInputStream input = new DataInputStream(new FileInputStream(this.storgaeFile));
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String line;
            while ((line = reader.readLine()) != null)
            {
                if (!this.values.contains(line)) {
                    this.values.add(line);
                }
            }
            reader.close();
            input.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void save()
    {
        try
        {
            FileWriter stream = new FileWriter(this.storgaeFile);
            BufferedWriter out = new BufferedWriter(stream);
            for (String value : this.values)
            {
                out.write(value);
                out.newLine();
            }
            out.close();
            stream.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public boolean contains(String value)
    {
        return this.values.contains(value);
    }

    public void add(String value)
    {
        if (!contains(value)) {
            this.values.add(value);
        }
    }

    public void remove(String value)
    {
        this.values.remove(value);
    }

    public ArrayList<String> getValues()
    {
        return this.values;
    }
}

