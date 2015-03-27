package se.ottomatech.marcusjacobsson.sverigesriksdag.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Marcus Jacobsson on 2015-03-25.
 */
public class MemberPojo implements Parcelable {

    private String yearBorn;
    private String sex;
    private String lastName;
    private String firstName;
    private String party;
    private String district;
    private String status;
    private String imageUrl;
    private String websiteUrl;
    private String emailAddress;
    private ArrayList<MemberAssignmentPojo> assignmentPojos;

    public MemberPojo() {
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getYearBorn() {
        return yearBorn;
    }

    public void setYearBorn(String yearBorn) {
        this.yearBorn = yearBorn;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ArrayList<MemberAssignmentPojo> getAssignmentPojos() {
        return assignmentPojos;
    }

    public void setAssignmentPojos(ArrayList<MemberAssignmentPojo> assignmentPojos) {
        this.assignmentPojos = assignmentPojos;
    }

    @Override
    public String toString() {
        return "MemberPojo{" +
                "yearBorn='" + yearBorn + '\'' +
                ", sex='" + sex + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", party='" + party + '\'' +
                ", district='" + district + '\'' +
                ", status='" + status + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", websiteUrl='" + websiteUrl + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", assignmentPojos=" + assignmentPojos +
                '}';
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.yearBorn);
        dest.writeString(this.sex);
        dest.writeString(this.lastName);
        dest.writeString(this.firstName);
        dest.writeString(this.party);
        dest.writeString(this.district);
        dest.writeString(this.status);
        dest.writeString(this.imageUrl);
        dest.writeString(this.websiteUrl);
        dest.writeString(this.emailAddress);
        dest.writeTypedList(this.assignmentPojos);
    }

    private MemberPojo(Parcel in) {
        this.yearBorn = in.readString();
        this.sex = in.readString();
        this.lastName = in.readString();
        this.firstName = in.readString();
        this.party = in.readString();
        this.district = in.readString();
        this.status = in.readString();
        this.imageUrl = in.readString();
        this.websiteUrl = in.readString();
        this.emailAddress = in.readString();
        this.assignmentPojos = in.createTypedArrayList(MemberAssignmentPojo.CREATOR);
    }

    public static final Parcelable.Creator<MemberPojo> CREATOR
            = new Parcelable.Creator<MemberPojo>() {
        @Override
        public MemberPojo createFromParcel(Parcel in) {
            return new MemberPojo(in);
        }

        @Override
        public MemberPojo[] newArray(int size) {
            return new MemberPojo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
}
