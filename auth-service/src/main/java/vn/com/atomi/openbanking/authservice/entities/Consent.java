package vn.com.atomi.openbanking.authservice.entities;

import lombok.Data;
import vn.com.atomi.openbanking.authservice.common.entities.BaseEntity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "ob_consent")
@Data
public class Consent extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String consentId;
    private String data;
    private String customerId;
    @Column(length = 3000)
    private String authReqId;
    private String status;
    private Timestamp expirationDateTime;
    private Timestamp transactionFromDateTime;
    private Timestamp transactionToDateTime;

}