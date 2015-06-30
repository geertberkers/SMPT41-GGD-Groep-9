package smpt.proftaak.ggd;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Geert on 18-6-2015
 */
public class Ramp implements Parcelable{

    private int id;
    private String titelRamp;
    private String laatsteUpdate;
    private String omschrijving;

    public Ramp(int id, String titelRamp, String laatsteUpdate, String omschrijving) {
        this.id = id;
        this.titelRamp = titelRamp;
        this.laatsteUpdate = laatsteUpdate;
        this.omschrijving = omschrijving;
    }

    public int getID() {
        return id;
    }

    public String getTitelRamp() {
        return titelRamp;
    }

    public String getLaatsteUpdate() {
        return laatsteUpdate;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public Ramp(Parcel read){
        this.id = read.readInt();
        this.titelRamp = read.readString();
        this.laatsteUpdate = read.readString();
        this.omschrijving = read.readString();
    }

    public static final Parcelable.Creator<Ramp> CREATOR =
            new Parcelable.Creator<Ramp>(){

                @Override
                public Ramp createFromParcel(Parcel source) {
                    return new Ramp(source);
                }

                @Override
                public Ramp[] newArray(int size) {
                    return new Ramp[size];
                }
            };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel arg0, int arg1) {
        arg0.writeInt(id);
        arg0.writeString(titelRamp);
        arg0.writeString(laatsteUpdate);
        arg0.writeString(omschrijving);
    }

}
