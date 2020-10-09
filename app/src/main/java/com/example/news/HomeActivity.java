package com.example.news;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.news.model.Article;
import com.example.news.model.News;
import com.example.news.ui.NewsFragment;
import com.example.news.utils.NavigationUtils;
import com.example.news.viewmodel.HomeViewModel;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HomeActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private GoogleSignInClient mGoogleSignInClient;
    private HomeViewModel homeViewModel;
    TabLayout tabLayout;
    ViewPager viewPager;
    HashMap<String,ArrayList<Article>> articles = new HashMap<>();
    ArrayList<ArrayList<Article>> articleSorted = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        observeViewModel();

        homeViewModel.getNews();
    }

    private void init(){
        tabLayout = (TabLayout) findViewById(R.id.tab);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        FragmentPagerAdapter adapter =
                new FragmentPagerAdapter(getSupportFragmentManager()) {
                    @Override
                    public Fragment getItem(int position) {
                        NewsFragment newsFragment = new NewsFragment();
                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList("array",articleSorted.get(position));
                        newsFragment.setArguments(bundle);
                        return newsFragment;
                    }

                    @Override
                    public CharSequence getPageTitle(int position) {
                        return "tab " + position;
                    }

                    @Override
                    public int getCount() {
                        return articleSorted.size();
                    }
                };

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.item1:
                LoginManager.getInstance().logOut();
                signOut();
                NavigationUtils.INSTANCE.openMainActivity(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
    }

    private void observeViewModel(){
        homeViewModel.getNewDataFunction().observe(this, new Observer<News>() {
            @Override
            public void onChanged(News news) {
                if(news != null){
                    setUpData(news);
                }
            }
        });

        homeViewModel.getNewsDataFunctionError().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

            }
        });
    }

    private void setUpData(News news){
        for(int i = 0;i < news.getArticles().size();i++){
            String sourceName = news.getArticles().get(i).getSource().getName();
            Article article = news.getArticles().get(i);

            ArrayList<Article> articleArrayList = articles.get(sourceName);
            if(articleArrayList == null){
                articleArrayList = new ArrayList<>();
            }else {
                articleArrayList.add(article);
            }
            articles.put(sourceName,articleArrayList);
        }

        Iterator it = articles.entrySet().iterator();
        int i=0;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            articleSorted.add((ArrayList<Article>) pair.getValue());
        }
        init();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}