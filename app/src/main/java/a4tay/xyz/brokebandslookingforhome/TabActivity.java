package a4tay.xyz.brokebandslookingforhome;

import android.content.Intent;
import android.content.SharedPreferences;
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

import a4tay.xyz.brokebandslookingforhome.Util.OnDataSendToActivity;

import static a4tay.xyz.brokebandslookingforhome.EventList.loggedIn;

public class TabActivity extends AppCompatActivity implements OnDataSendToActivity{

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private static final String LOG_TAG = TabActivity.class.getSimpleName();
    public static String baseURL = "http://192.168.1.66:8080/Harbor/api/";

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

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        Intent intent = new Intent();
                        switch (item.getItemId()) {
                            case R.id.events:
                                Toast.makeText(getApplicationContext(),"First",Toast.LENGTH_LONG).show();
                                intent.setClass(getApplicationContext(),TabActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.offers:
                                Toast.makeText(getApplicationContext(),"Second",Toast.LENGTH_LONG).show();
                                intent.setClass(getApplicationContext(),HomeTabs.class);
                                startActivity(intent);
                                break;
                            case R.id.homes:
                                Toast.makeText(getApplicationContext(),"Third",Toast.LENGTH_LONG).show();
                                break;
                        }
                        return false;
                    }
                });



    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new EventList(), "Events");
        adapter.addFragment(new AddBand(), "Add Band");
        adapter.addFragment(new BandList(), "Bands");
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


            if (asyncReturn[1] == null || asyncReturn[1].equals("")) {
                asyncReturn[1] = "Comm failure...";
            }
            if (asyncReturn[1].substring(0, 1).equals("{")) {

                if(asyncReturn[0].equals("EventList")) {
                    EventList.dealWithResponse(asyncReturn[1]);

                    Log.d(LOG_TAG,"Launch dealWithResponse");
                }
                else {
                    Toast.makeText(getApplicationContext(),asyncReturn[1],Toast.LENGTH_LONG).show();
                }

            } else {
                Log.d(LOG_TAG, asyncReturn[0]);
                Toast.makeText(getApplicationContext(), asyncReturn[1], Toast.LENGTH_LONG).show();
            }

    }

}
