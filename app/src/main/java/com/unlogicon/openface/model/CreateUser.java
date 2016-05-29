package com.unlogicon.openface.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Nik on 24.04.2016.
 */
public class CreateUser implements Serializable {

    private static final long serialVersionUID = 591244841828904315L;
    @SerializedName("success")
    private String success;
    @SerializedName("user_id")
    private int userId;

    public String getSuccess()
    {
        return this.success;
    }

    public int getUserId()
    {
        return this.userId;
    }

    public void setSuccess(String paramString)
    {
        this.success = paramString;
    }

    public void setUserId(int paramInt)
    {
        this.userId = paramInt;
    }

}
