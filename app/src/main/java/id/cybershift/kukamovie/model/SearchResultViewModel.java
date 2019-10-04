package id.cybershift.kukamovie.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;
import id.cybershift.kukamovie.entity.Movie;
import id.cybershift.kukamovie.entity.TVShow;

import static id.cybershift.kukamovie.BuildConfig.API_KEY;

public class SearchResultViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Movie>> movieRes = new MutableLiveData<>();
    private MutableLiveData<ArrayList<TVShow>> tvshowRes = new MutableLiveData<>();

    public void setResult(final Context context, boolean isMovie, String query) {
        //Set Bahasa
        String lang = Locale.getDefault().getLanguage();
        if (lang.equals("in")) {
            lang = "id";
        } else {
            lang = "en";
        }

        if (isMovie) {
            AsyncHttpClient client = new AsyncHttpClient();
            final ArrayList<Movie> listItems = new ArrayList<>();
            String url = "https://api.themoviedb.org/3/search/movie?api_key=" + API_KEY + "&language=" + lang + "&query=" + query;
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
                        movieRes.postValue(listItems);
                    } catch (Exception e) {
                        Log.d("Fakhri Exception MOVIE", e.getMessage());
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.d("Fakhri onFailure", error.getMessage());
                }
            });
        } else if (!isMovie) {
            AsyncHttpClient client = new AsyncHttpClient();
            final ArrayList<TVShow> tvlistItems = new ArrayList<>();
            String url = "https://api.themoviedb.org/3/search/tv?api_key=" + API_KEY + "&language=" + lang + "&query=" + query;
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
                        tvshowRes.postValue(tvlistItems);
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
    }

    public LiveData<ArrayList<Movie>> getMovies() {
        return movieRes;
    }

    public LiveData<ArrayList<TVShow>> getTVShows() {
        return tvshowRes;
    }
}
