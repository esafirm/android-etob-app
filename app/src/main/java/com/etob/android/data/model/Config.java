package com.etob.android.data.model;

import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public class Config implements Parcelable {

  private static final int DEFAULT_COLOR = Color.RED;

  @SerializedName("icon") private String icon;
  @SerializedName("profile") private ProfileEntity profile;

  public String getIcon() {
    return icon;
  }

  public int getColor() {
    if (icon == null) return DEFAULT_COLOR;
    if (icon.equalsIgnoreCase("red")) return Color.RED;
    if (icon.equalsIgnoreCase("green")) return Color.GREEN;
    if (icon.equalsIgnoreCase("blue")) return Color.BLUE;
    return DEFAULT_COLOR;
  }

  public ProfileEntity getProfile() {
    return profile;
  }

  public static class ProfileEntity implements Parcelable {

    @SerializedName("name") private String name;
    @SerializedName("photo") private String photo;
    @SerializedName("balance") private String balance;

    public String getName() { return name;}

    public void setName(String name) { this.name = name;}

    public String getPhoto() { return photo;}

    public void setPhoto(String photo) { this.photo = photo;}

    public String getBalance() { return balance;}

    public void setBalance(String balance) { this.balance = balance;}

    @Override public int describeContents() { return 0; }

    @Override public void writeToParcel(Parcel dest, int flags) {
      dest.writeString(this.name);
      dest.writeString(this.photo);
      dest.writeString(this.balance);
    }

    public ProfileEntity() {}

    protected ProfileEntity(Parcel in) {
      this.name = in.readString();
      this.photo = in.readString();
      this.balance = in.readString();
    }

    public static final Creator<ProfileEntity> CREATOR = new Creator<ProfileEntity>() {
      @Override public ProfileEntity createFromParcel(Parcel source) {
        return new ProfileEntity(source);
      }

      @Override public ProfileEntity[] newArray(int size) {return new ProfileEntity[size];}
    };
  }

  @Override public int describeContents() { return 0; }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.icon);
    dest.writeParcelable(this.profile, flags);
  }

  public Config() {}

  protected Config(Parcel in) {
    this.icon = in.readString();
    this.profile = in.readParcelable(ProfileEntity.class.getClassLoader());
  }

  public static final Creator<Config> CREATOR = new Creator<Config>() {
    @Override public Config createFromParcel(Parcel source) {return new Config(source);}

    @Override public Config[] newArray(int size) {return new Config[size];}
  };
}
