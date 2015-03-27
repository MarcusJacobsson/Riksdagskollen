package se.ottomatech.marcusjacobsson.sverigesriksdag.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Marcus Jacobsson on 2015-03-25.
 */
public class MemberAssignmentPojo implements Parcelable {

    private String role;
    private String type;
    private String status;
    private String dtStart;
    private String dtEnd;
    private String task;

    public MemberAssignmentPojo(){}

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    @Override
    public String toString() {
        return "MemberAssignmentPojo{" +
                "role='" + role + '\'' +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                ", dtStart='" + dtStart + '\'' +
                ", dtEnd='" + dtEnd + '\'' +
                ", task='" + task + '\'' +
                '}';
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.role);
        dest.writeString(this.type);
        dest.writeString(this.status);
        dest.writeString(this.dtStart);
        dest.writeString(this.dtEnd);
        dest.writeString(this.task);
    }

    private MemberAssignmentPojo(Parcel in) {
        this.role = in.readString();
        this.type = in.readString();
        this.status = in.readString();
        this.dtStart = in.readString();
        this.dtEnd = in.readString();
        this.task = in.readString();
    }

    public static final Parcelable.Creator<MemberAssignmentPojo> CREATOR = new Parcelable.Creator<MemberAssignmentPojo>() {
        @Override
        public MemberAssignmentPojo createFromParcel(Parcel in) {
            return new MemberAssignmentPojo(in);
        }

        @Override
        public MemberAssignmentPojo[] newArray(int size) {
            return new MemberAssignmentPojo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
}
