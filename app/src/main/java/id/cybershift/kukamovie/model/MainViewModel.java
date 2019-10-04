package id.cybershift.kukamovie.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;
import id.cybershift.kukamovie.BuildConfig;
import id.cybershift.kukamovie.entity.Movie;
import id.cybershift.kukamovie.entity.TVShow;

public class MainViewModel extends ViewModel {
    private static final String API_KEY = BuildConfig.API_KEY;
    private String lang;
    private MutableLiveData<ArrayList<Movie>> listMovies = new MutableLiveData<>();
    private MutableLiveData<ArrayList<TVShow>> listTVShows = new MutableLiveData<>();

    public void setMovie() {
        //Set Bahasa
        lang = Locale.getDefault().getLanguage();
        if (lang.equals("in")) {
            lang = "id";
        } else {
            lang = "en";
        }

        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Movie> listItems = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/movie/popular?api_key=" + API_KEY + "&language=" + lang + "&page=1";
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject movie = list.getJSONObject(i);
                        Movie movieItem = new Movie(movie);
                        listItems.add(movieItem);
                    }
                    listMovies.postValue(listItems);
                } catch (Exception e) {
                    Log.d("Fakhri Exception MOVIE", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("Fakhri onFailure", error.getMessage());
            }
        });
    }

    public void setTVShows() {
        //Set Bahasa
        lang = Locale.getDefault().getLanguage();
        if (lang.equals("in")) {
            lang = "id";
        } else {
            lang = "en";
        }
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<TVShow> tvlistItems = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/tv/popular?api_key=" + API_KEY + "&language="+lang+"&page=1";
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject tvshow = list.getJSONObject(i);
                        TVShow tvshowItem = new TVShow(tvshow);
                        tvlistItems.add(tvshowItem);
                    }
                    listTVShows.postValue(tvlistItems);
                } catch (Exception e) {
                    Log.d("Fakhri Exception TVSHOW", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("Fakhri onFailure", error.getMessage());
            }
        });
    }

    public LiveData<ArrayList<Movie>> getMovies() {
        return listMovies;
    }

    public LiveData<ArrayList<TVShow>> getTVShows() {
        return listTVShows;
    }
}
