package com.zhang.mydemo.actionbar;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.widget.SearchView;
import android.widget.TextView;

import com.zhang.mydemo.R;

import java.lang.reflect.Field;

public abstract class BaseActionBarActivity extends DebugActivity {

    private String tag = null;
    public BaseActionBarActivity(String inTag) {
        super(R.menu.menu, R.layout.main, R.id.textViewId, inTag);
        tag = inTag;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv = getTextView();
        tv.setText(tag);
        getOverflowMenu();
    }

    private void getOverflowMenu() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        this.setupSearcherView(menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            this.reportBack(tag, "Home pressed");
            return true;
        }

        if (item.getItemId() == R.id.menu_invoke_tabnav){
            if (getNavMode() == ActionBar.NAVIGATION_MODE_TABS){
                this.reportBack(tag, "You are already in tab nav");
            } else {
                this.invokeTabNav();
            }
            return true;
        }

        if (item.getItemId() == R.id.menu_invoke_listnav){
            if (getNavMode() == ActionBar.NAVIGATION_MODE_LIST){
                this.reportBack(tag, "You are already in list nav");
            } else {
                this.invokeListNav();
            }
            return true;
        }

        if (item.getItemId() == R.id.menu_invoke_standardnav){
            if (getNavMode() == ActionBar.NAVIGATION_MODE_STANDARD){
                this.reportBack(tag, "You are already in standard nav");
            } else {
                this.invokeStandardNav();
            }
            return true;
        }

        return false;
    }

    private int getNavMode(){
        ActionBar bar = this.getSupportActionBar();
        return bar.getNavigationMode();
    }

    private void invokeTabNav(){
        Intent i = new Intent(this, TabNavigationActionBarActivity.class);
        startActivity(i);
    }

    private void invokeListNav(){
        Intent i = new Intent(this, ListNavigationActionBarActivity.class);
        startActivity(i);
    }

    private void invokeStandardNav(){
        Intent i = new Intent(this, StandardNavigationActionBarActivity.class);
        startActivity(i);
    }

    private void setupSearcherView(Menu menu) {
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        if (searchView == null) {
            this.reportBack(tag, "Failed to get search view");
            return;
        }
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        ComponentName cn = new ComponentName(this, SearchResultsActivity.class);
        SearchableInfo info = searchManager.getSearchableInfo(cn);
        if (info == null) {
            this.reportBack(tag, "Failed to get search info");
            return;
        }
        searchView.setSearchableInfo(info);

        searchView.setIconifiedByDefault(false);
    }
}
