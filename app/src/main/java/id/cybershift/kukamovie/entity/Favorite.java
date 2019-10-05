package id.cybershift.kukamovie.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Favorite implements Parcelable {
    public static final Creator<Favorite> CREATOR = new Creator<Favorite>() {
        @Override
        public Favorite createFromParcel(Parcel in) {
            return new Favorite(in);
        }

        @Override
        public Favorite[] newArray(int size) {
            return new Favorite[size];
        }
    };
    private int id;
    private int idFromAPI;
    private String title;
    private String overview;
    private double rate;
    private String year;
    private String poster;
    private String type;

    public Favorite() {
    }

    protected Favorite(Parcel in) {
        id = in.readInt();
        idFromAPI = in.readInt();
        title = in.readString();
        overview = in.readString();
        rate = in.readDouble();
        year = in.readString();
        poster = in.readString();
        type = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdFromAPI() {
        return idFromAPI;
    }

    public void setIdFromAPI(int idFromAPI) {
        this.idFromAPI = idFromAPI;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(idFromAPI);
        parcel.writeString(title);
        parcel.writeString(overview);
        parcel.writeDouble(rate);
        parcel.writeString(year);
        parcel.writeString(poster);
        parcel.writeString(type);
    }
}
