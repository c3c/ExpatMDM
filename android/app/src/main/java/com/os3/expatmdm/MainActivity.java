package com.os3.expatmdm;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Locale;


public class MainActivity extends ActionBarActivity implements ActionBar.TabListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.os3.expatmdm.R.layout.activity_main);

        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(com.os3.expatmdm.R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }

        Expat.SetContext(getApplicationContext());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(com.os3.expatmdm.R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == com.os3.expatmdm.R.id.action_settings) {
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(com.os3.expatmdm.R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(com.os3.expatmdm.R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(com.os3.expatmdm.R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(com.os3.expatmdm.R.layout.fragment_main, container, false);
            final TextView textView = (TextView) rootView.findViewById(com.os3.expatmdm.R.id.section_label);
            //textView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));

            switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
                case 0: {
                    HashMap<String, String> gather = Sysinfo.Gather();
                    String tpl = "<h2>System properties</h2>";
                    tpl += "Kernel version: " + gather.get("os.version");
                    String magic = gather.get("kernel.vermagic");
                    tpl += "<br>Magic: " + (magic.length() > 2 ? (magic.substring(magic.indexOf(" ") + 1, magic.lastIndexOf(" "))) : "N/A");
                    tpl += "<br>Architecture: " + gather.get("os.arch");
                    tpl += "<br>CPU ABI: " + gather.get("build.cpu_abi");
                    tpl += "<br>SDK level: " + gather.get("android.sdk");
                    tpl += "<br>Android release: " + gather.get("android.release");
                    tpl += "<br>Hardware: " + gather.get("build.hardware");
                    tpl += "<br>Device: " + gather.get("build.manufacturer") +
                            " " + gather.get("build.brand") +
                            " " + gather.get("build.model");
                    tpl += "<br>Runtime: " + gather.get("runtime.real");
                    tpl += "<br>Verity: " + tr(gather.get("kernel.verity"));
                    tpl += "<br>Module support: " + tr(gather.get("methods.modules"));
                    tpl += "<br>Kallsyms: " + tr(gather.get("methods.kallsyms"));
                    tpl += "<br>Kmem: " + tr(gather.get("methods.devkmem"));
                    tpl += "<br>Unix98 PTYs: " + tr(gather.get("methods.devptmx"));

                    textView.setText(Html.fromHtml(tpl));
                    Expat.FetchPatches(gather);

                    break;
                }
                case 1: {
                    String tpl = "<h2>Exploit & patch</h2>";
                    tpl += "The following exploits were found for your device:";
                    tpl += "<br>&nbsp;&nbsp;- Kernel exploits: <b>3</b>"; // those are still arbitrary, ofc... :-)
                    tpl += "<br>&nbsp;&nbsp;- Root exploits: <b>7</b>";
                    tpl += "<br>";
                    tpl += "<br>The following patches were found for your device:";
                    tpl += "<br>&nbsp;&nbsp;- Kernel patches: <b>4</b>";
                    tpl += "<br>&nbsp;&nbsp;- Runtime patches: <b>25</b>";
                    tpl += "<br><br>Not all vulnerabilities give root/kernel access, still it may be important to patch them, such as weak RNGs.";

                    textView.setText(Html.fromHtml(tpl));

                    final ProgressBar progress = (ProgressBar) rootView.findViewById(com.os3.expatmdm.R.id.progressBar);
                    final Button button = (Button) rootView.findViewById(com.os3.expatmdm.R.id.button);

                    button.setVisibility(View.VISIBLE);

                    final String finalTpl = tpl;
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            button.setVisibility(View.INVISIBLE);
                            progress.setVisibility(View.VISIBLE);

                            // Ofc it's not needed to actually do the sleep, but we want some interactiveness...
                            new Handler().postDelayed(new Runnable() {
                                public void run() {
                                    final String tpl = finalTpl + "<br><br>&#187; Gaining kernel access...";
                                    textView.setText(Html.fromHtml(tpl));

                                    new Handler().postDelayed(new Runnable() {
                                        public void run() {
                                            Expat.Exploit();
                                            final String tpl2 = tpl+ "<br>&#187; Got kernel access, skipping root exploit...";
                                            textView.setText(Html.fromHtml(tpl2));

                                            new Handler().postDelayed(new Runnable() {
                                                public void run() {
                                                    final String tpl3 = tpl2 + "<br>&#187; Applying patches... prepare for crashes :)";
                                                    textView.setText(Html.fromHtml(tpl3));

                                                    new Handler().postDelayed(new Runnable() {
                                                        public void run() {
                                                            Expat.Patch();
                                                            progress.setVisibility(View.INVISIBLE);
                                                            textView.setText(Html.fromHtml(tpl3 + "<br><br><b><font color='green'>System patched!<font></b>"));
                                                        }
                                                    }, 5000);
                                                }
                                            }, 1000);
                                        }
                                    }, 2000);
                                }
                            }, 1000);
                        }
                    });
                    break;
                }
            }

            return rootView;
        }

        private String tr(String gathered) {
            int x;
            try
            {
                x = Integer.parseInt(gathered);
            } catch(NumberFormatException nfe)
            {
                return null;
            }
            return x > 0 ? "enabled" : "disabled";
        }

    }

}
