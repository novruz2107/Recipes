package com.eazi.recipes;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.eazi.recipes.Helpers.CustomSuggestionsAdapter;
import com.eazi.recipes.Helpers.DBHelper;
import com.eazi.recipes.Helpers.Recipe;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MaterialSearchBar.OnSearchActionListener, AdapterView.OnItemClickListener{

    private Fragment fragment = null;
    private FragmentManager fragmentManager;

    MaterialSearchBar searchBar;
    private List<Recipe> suggestions;
    private CustomSuggestionsAdapter customSuggestionsAdapter;
    ListView listView;
    RecyclerView mRecyclerView;
    public DBHelper dbHelper;
    public static String passingValue = "";
    private ImageView image;
    private TextView name, email;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.list_view);
        mRecyclerView = (RecyclerView) findViewById(R.id.mt_recycler);
        suggestions = new ArrayList<>();
        dbHelper = new DBHelper(getApplicationContext());
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        image = headerView.findViewById(R.id.imageView);
        name = headerView.findViewById(R.id.nameInHeader);
        email = headerView.findViewById(R.id.emailInHeader);
        prefs = getSharedPreferences("Profile", 0);
        if(prefs.getBoolean("isMale", true)){
            image.setImageResource(R.drawable.male);
        }else{
            image.setImageResource(R.drawable.female);
        }
        name.setText(prefs.getString("name", "Name") + " " + prefs.getString("surname", "Surname"));
        email.setText(prefs.getString("email", "Email address"));

        navigationView.setNavigationItemSelectedListener(this);

        searchBar = (MaterialSearchBar) findViewById(R.id.searchBar);
        searchBar.setOnSearchActionListener(this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        customSuggestionsAdapter = new CustomSuggestionsAdapter(inflater, dbHelper);
        customSuggestionsAdapter.setSuggestions(suggestions);

        searchBar.setCustomSuggestionAdapter(customSuggestionsAdapter);
        searchBar.setCardViewElevation(10);
        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        if(searchBar.getText().length()>2) {
                            suggestions = dbHelper.findByWord(searchBar.getText());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    customSuggestionsAdapter.setSuggestions(suggestions);
                                    customSuggestionsAdapter.notifyDataSetChanged();
                                }
                            });
                        }
                    }
                });


            }

        });

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        PagerAdapter pagerAdapter=new FragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
        viewPagerTab.setViewPager(viewPager);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    private void fillDataFromDatabase(){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                suggestions.add(new Recipe("Recipe 0", "snack"));
                suggestions.add(new Recipe("Recipe 1", "sauce"));
                suggestions.add(new Recipe("Recipe 2", "cookie"));
                suggestions.add(new Recipe("Recipe 3", "cookie"));
                suggestions.add(new Recipe("Recipe 4", "cake"));
            }
        });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        dbHelper.close();
        suggestions.clear();
        customSuggestionsAdapter.clearSuggestions();

    }

    @Override
    protected void onResume() {
        super.onResume();
        fillDataFromDatabase();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

//        if (id == R.id.nav_settings) {
//
//        } else
        if (id == R.id.nav_share) {

        }
//        else if (id == R.id.nav_coupon) {
//
//        }
        else if(id == R.id.nav_fav){
            startActivity(new Intent(getApplicationContext(), FavoritesFragment.class));
        }else if(id == R.id.nav_profile){
            if(FirebaseAuth.getInstance().getCurrentUser() == null){
                startActivity(new Intent(getApplicationContext(), LogInActivity.class));
            }else {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            }
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onSearchStateChanged(boolean enabled) {
    }

    @Override
    public void onSearchConfirmed(CharSequence text) {

    }

    @Override
    public void onButtonClicked(int buttonCode) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        switch (buttonCode){
            case MaterialSearchBar.BUTTON_NAVIGATION:
                drawer.openDrawer(Gravity.LEFT);
                break;
            case MaterialSearchBar.BUTTON_SPEECH:
                break;
            case MaterialSearchBar.BUTTON_BACK:
                searchBar.disableSearch();
                break;
        }
    }

    class FragmentAdapter extends FragmentPagerAdapter {

        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new HomeFragment();
                case 1:
                    return new CategoriesFragment();
                case 2:
                    return new IngSearch();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                //
                //Your tab titles
                //
                case 0:return "Home";
                case 1:return "Categories";
                case 2:return "IngSearch";
                default:return null;
            }
        }
    }
}
