package smpt.proftaak.ggd;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Joep on 25-6-2015.
 */
public class TijdlijnItem implements Parcelable
{
    private String title;
    private String imgSrc;
    private String timestamp;
    private String description;
    private AnimationPermission animate;

    public TijdlijnItem(String title, String imgSrc, String timestamp, String description) {
        this.title = title;
        this.imgSrc = imgSrc;
        this.timestamp = timestamp;
        this.description = description;
        this.animate = AnimationPermission.ANIMATE;
    }

    public String getTitle()
    {
        return title;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getDescription() {
        return description;
    }

    public AnimationPermission getAnimationPermission() {
        return animate;
    }

    public void revokeAnimationPermission()
    {
        animate = AnimationPermission.NO_ANIMATION;
    }

    public TijdlijnItem(Parcel read){
        title = read.readString();
        imgSrc = read.readString();
        timestamp = read.readString();
        description = read.readString();
        animate = (AnimationPermission) read.readValue(AnimationPermission.class.getClassLoader());
    }

    public static final Parcelable.Creator<TijdlijnItem> CREATOR =
            new Parcelable.Creator<TijdlijnItem>(){

                @Override
                public TijdlijnItem createFromParcel(Parcel source) {
                    return new TijdlijnItem(source);
                }

                @Override
                public TijdlijnItem[] newArray(int size) {
                    return new TijdlijnItem[size];
                }
            };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(imgSrc);
        dest.writeString(timestamp);
        dest.writeString(description);
        dest.writeValue(animate);
    }
}
