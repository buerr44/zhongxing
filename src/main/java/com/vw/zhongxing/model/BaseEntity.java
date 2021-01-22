package com.vw.zhongxing.model;

import org.springframework.boot.autoconfigure.condition.ConditionalOnCloudPlatform;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Buer44
 */
public class BaseEntity implements Serializable {
    @Column(name = "create_by")
    private String createBy;
    @Column(name = "create_date")
    private Date createDate;
    @Column(name = "last_update_by")
    private String lastUpdateBy;
    @Column(name = "last_update_date")
    private Date lastUpdateDate;
    @Column(name = "row_version")
    private Integer rowVersion;
    @Column(name = "is_valid")
    private Integer isValid = 1;

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Integer getRowVersion() {
        return rowVersion;
    }

    public void setRowVersion(Integer rowVersion) {
        this.rowVersion = rowVersion;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "createBy='" + createBy + '\'' +
                ", createDate=" + createDate +
                ", lastUpdateBy='" + lastUpdateBy + '\'' +
                ", lastUpdateDate=" + lastUpdateDate +
                ", rowVersion=" + rowVersion +
                ", isValid=" + isValid +
                '}';
    }
}
