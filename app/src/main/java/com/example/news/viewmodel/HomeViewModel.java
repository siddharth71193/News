package com.example.news.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.example.news.base.BaseViewModel;
import com.example.news.model.News;
import com.example.news.network.Apis;
import com.example.news.network.RetrofitClient;

import java.net.SocketTimeoutException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class HomeViewModel extends BaseViewModel {
    MutableLiveData<News> getNewsData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> getNewsDataError = new MutableLiveData<>();

    public MutableLiveData<News> getNewDataFunction(){
        return getNewsData;
    }

    public MutableLiveData<Boolean> getNewsDataFunctionError(){
        return getNewsDataError;
    }

    public void getNews() {
        Apis api = RetrofitClient.getRetroOhm().create(Apis.class);

        getCompositeDisposable().add(api.getNews("6b52ffc57d0b48f1bc7df54cb611daa2", "in", "business").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<News>() {
                    @Override
                    public void onSuccess(News user) {
                        getNewsData.setValue(user);
                    }

                    @Override
                    public void onError(Throwable e) {

                        getNewsDataError.setValue(true);
                    }
                }));
    }
}
