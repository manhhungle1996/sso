package vn.com.atomi.openbanking.authservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.com.atomi.openbanking.authservice.entities.PrivateToken;

import java.util.Optional;

@Repository
public interface PrivateTokenRepository extends JpaRepository<PrivateToken, Long> {
    @Query(
            value = "SELECT e.* FROM private_token e WHERE e.consent_id = :consentId",
            nativeQuery = true)
    Optional<PrivateToken> finByConsentId(String consentId);
}