package vn.com.atomi.openbanking.authservice.common.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import vn.com.atomi.openbanking.authservice.utils.Constants;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;

@Data
@MappedSuperclass
public abstract class BaseEntity {
    @JsonProperty(value = "createdBy", access = JsonProperty.Access.READ_ONLY)
    @Column(updatable = false)
    private Long createdBy;
    @Column(updatable = false)
    @CreationTimestamp
    @JsonProperty(value = "createdAt", access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone= Constants.TIMEZONE)
    private Timestamp createdAt;
    @Column(updatable = false)
    @JsonProperty(value = "updatedBy", access = JsonProperty.Access.READ_ONLY)
    private Long updatedBy;
    @UpdateTimestamp
    @JsonProperty(value = "updatedAt", access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone=Constants.TIMEZONE)
    private Timestamp updatedAt;
}
