package id.cybershift.kukamovie.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONObject;

public class TVShow implements Parcelable {
    public static final Creator<TVShow> CREATOR = new Creator<TVShow>() {
        @Override
        public TVShow createFromParcel(Parcel in) {
            return new TVShow(in);
        }

        @Override
        public TVShow[] newArray(int size) {
            return new TVShow[size];
        }
    };
    private int id;
    private String title;
    private String overview;
    private double rate;
    private String year;
    private String poster;

    public TVShow(JSONObject object) {
        try {
            this.id = object.getInt("id");
            this.title = object.getString("original_name");
            this.overview = object.getString("overview");
            this.rate = object.getDouble("vote_average");

            String temp = object.getString("first_air_date");
            String[] separated = temp.split("-");
            this.year = separated[0];
            this.poster = object.getString("poster_path");
        } catch (Exception e) {
            Log.d("Fakhri: TVSHOW", e.getMessage());
        }
    }

    protected TVShow(Parcel in) {
        id = in.readInt();
        title = in.readString();
        overview = in.readString();
        rate = in.readDouble();
        year = in.readString();
        poster = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(overview);
        parcel.writeDouble(rate);
        parcel.writeString(year);
        parcel.writeString(poster);
    }
}
