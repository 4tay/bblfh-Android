package a4tay.xyz.brokebandslookingforhome;

import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import a4tay.xyz.brokebandslookingforhome.Util.LoaderManagers.LoginStatus;
import a4tay.xyz.brokebandslookingforhome.Util.OnDataSendToActivity;


public class TabActivity extends AppCompatActivity implements OnDataSendToActivity{

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private static final String LOG_TAG = TabActivity.class.getSimpleName();
    public static String baseURL = "http://dev.4tay.xyz:8080/Harbor/api/";
    public static boolean loggedIn = false;
    public static int inBand = -1;
    private static final String MY_PREFS = "harbor-preferences";
    private static final String NAME_KEY = "nameKey";
    private static final String PASS_KEY = "passKey";
    private static final String BAND_KEY = "bandKey";
    public static  String submittedEM;
    public static String submittedPW1;
    private BottomNavigationView bottomNavigationView;
    private EventList eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Harbor");
        toolbar.setLogo(R.drawable.anchor_logo);
        toolbar.setForegroundGravity(View.TEXT_ALIGNMENT_GRAVITY);
        final android.support.v7.app.ActionBar aBar = getSupportActionBar();
        aBar.setDisplayShowCustomEnabled(true);
        aBar.setDisplayShowTitleEnabled(false);
        aBar.setDisplayHomeAsUpEnabled(false);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        SharedPreferences prefs = getApplicationContext().getSharedPreferences(MY_PREFS, MODE_PRIVATE);

        submittedEM = prefs.getString(NAME_KEY, "");//defining an empty string as the default
        submittedPW1 = prefs.getString(PASS_KEY, ""); //defining an empty string as the default


        if(!loggedIn) {
            String loginUrl = baseURL + "fan/login/";

            loginUrl = loginUrl + submittedEM + "/" + submittedPW1;
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(BAND_KEY,"");

            editor.apply();

            Log.d(LOG_TAG,loginUrl);
            new LoginStatus(TabActivity.this, "EventList").execute(new String[]{loginUrl});


        }
        eventList = new EventList();

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        Intent intent = new Intent();
                        switch (item.getItemId()) {
                            case R.id.it_bottom_nav_one:

                                intent.setClass(getApplicationContext(),TabActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.it_bottom_nav_two:

                                break;
                            case R.id.it_bottom_nav_three:
                                if(inBand > 0) {
                                    intent.setClass(getApplicationContext(),BandTabs.class);
                                } else {
                                    intent.setClass(getApplicationContext(), HomeTabs.class);
                                }
                                startActivity(intent);

                                break;
                        }
                        return false;
                    }
                });



    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(eventList, "Events");
        adapter.addFragment(new AddBand(), "Add Band");
        //adapter.addFragment(new BandList(), "Bands");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            this.startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void sendData(String[] asyncReturn) {
        Log.d(LOG_TAG,"sendData");

        eventList.dealWithResponse(asyncReturn[1]);

        if(inBand > 0) {
            bottomNavigationView.getMenu().getItem(2).setTitle("Band");
        }

        Log.d(LOG_TAG,"Launch dealWithResponse");

    }

}
