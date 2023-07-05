package vn.com.atomi.openbanking.authservice.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "private_token")
@Data
public class PrivateToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String consentId;
    @Column(length = 3000)
    private String authorization;
}
