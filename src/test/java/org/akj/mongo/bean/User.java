package org.akj.mongo.bean;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.io.Serializable;
import java.util.Date;

@Data
public class User implements Serializable {
    @BsonId
    @BsonProperty("_id")
    private String id;

    private UserInfo userInfo;

    private String userName;

    private String authType;

    private String password;

    @BsonProperty("createDate")
    private Date createDate;

    private Date lastUpdateDate;
}
