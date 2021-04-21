package com.example.food_app;

import android.content.Context;

import com.example.food_app.RecycleView.Fooditems;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class RecyclerViewAdapterTest {

    private RecyclerViewAdapter adapter;
    private Context context;
    private boolean result;
    private List<Fooditems> list;

    ArrayList<String> testdata = new ArrayList<String>();


    @Test
    public void onCreateViewHolder() {
        adapter = new RecyclerViewAdapter(list, context);
        assertEquals(true, RecyclerViewAdapter.CreateViewHolder(result));
    }

    @Test
    public void onBindViewHolder() {
        adapter = new RecyclerViewAdapter(list, context);
        assertEquals(true, RecyclerViewAdapter.CreateViewHolder(result));
    }

    @Test
    public void getItemCount() {
        adapter = new RecyclerViewAdapter(list, context);
        assertEquals(true, RecyclerViewAdapter.CreateViewHolder(result));
    }
}