package se.ottomatech.marcusjacobsson.sverigesriksdag.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Marcus Jacobsson on 2015-03-21.
 */
public class CalendarPojo implements Parcelable{

    private String categories;
    private String location;
    private String uid;
    private String transp;
    private String dtStart;
    private String dtEnd;
    private String created;
    private String lastModified;
    private String summary;
    private String description;
    private String comment;

    public CalendarPojo(String categories, String location, String uid, String transp, String dtStart,
                        String dtEnd, String created, String lastModified, String summary,
                        String description, String comment) {
        this.categories = categories;
        this.location = location;
        this.uid = uid;
        this.transp = transp;
        this.dtStart = dtStart;
        this.dtEnd = dtEnd;
        this.created = created;
        this.lastModified = lastModified;
        this.summary = summary;
        this.description = description;
        this.comment = comment;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTransp() {
        return transp;
    }

    public void setTransp(String transp) {
        this.transp = transp;
    }

    public String getDtStart() {
        return dtStart;
    }

    public void setDtStart(String dtStart) {
        this.dtStart = dtStart;
    }

    public String getDtEnd() {
        return dtEnd;
    }

    public void setDtEnd(String dtEnd) {
        this.dtEnd = dtEnd;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "\nCalendar{" +
                "categories='" + categories + '\'' +
                ", location='" + location + '\'' +
                ", uid='" + uid + '\'' +
                ", transp='" + transp + '\'' +
                ", dtStart='" + dtStart + '\'' +
                ", dtEnd='" + dtEnd + '\'' +
                ", created='" + created + '\'' +
                ", lastModified='" + lastModified + '\'' +
                ", summary='" + summary + '\'' +
                ", description='" + description + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(categories);
        dest.writeString(location);
        dest.writeString(uid);
        dest.writeString(transp);
        dest.writeString(dtStart);
        dest.writeString(dtEnd);
        dest.writeString(created);
        dest.writeString(lastModified);
        dest.writeString(summary);
        dest.writeString(description);
        dest.writeString(comment);
    }

    private CalendarPojo(Parcel in){
        this.categories = in.readString();
        this.location = in.readString();
        this.uid = in.readString();
        this.transp = in.readString();
        this.dtStart = in.readString();
        this.dtEnd = in.readString();
        this.created = in.readString();
        this.lastModified = in.readString();
        this.summary = in.readString();
        this.description = in.readString();
        this.comment = in.readString();
    }

    public static final Parcelable.Creator<CalendarPojo> CREATOR
            = new Parcelable.Creator<CalendarPojo>() {
        @Override
        public CalendarPojo createFromParcel(Parcel in) {
            return new CalendarPojo(in);
        }

        @Override
        public CalendarPojo[] newArray(int size) {
            return new CalendarPojo[size];
        }
    };
}
