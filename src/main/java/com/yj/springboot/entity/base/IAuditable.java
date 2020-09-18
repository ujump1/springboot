package com.yj.springboot.entity.base;


import java.util.Date;

/**
 * 实现功能：业务审计属性特征接口
 *
 * @author YJ(Vision.Mac)
 * @version 1.0.00  2020/5/27
 */
public interface IAuditable {

    /**
     * Returns the user who created this entity.
     *
     * @return the createdBy
     */
    String getCreatorId();

    void setCreatorId(String creatorId);

    String getCreatorAccount();

    void setCreatorAccount(String creatorAccount);

    String getCreatorName();

    void setCreatorName(String creatorName);

    Date getCreatedDate();

    void setCreatedDate(Date createdDate);

    String getLastEditorId();

    void setLastEditorId(String lastEditorId);

    String getLastEditorAccount();

    void setLastEditorAccount(String lastEditorAccount);

    String getLastEditorName();

    void setLastEditorName(String lastEditorName);

    Date getLastEditedDate();

    void setLastEditedDate(Date lastEditedDate);
}

