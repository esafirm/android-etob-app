package com.etob.android.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by esafirm on 8/13/16.
 */
public class BaseResponse {
  /**
   * message : success
   */

  @SerializedName("message") private String message;

  public String getMessage() { return message;}

  public void setMessage(String message) { this.message = message;}
}
