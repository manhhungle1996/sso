package vn.com.atomi.openbanking.authservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vn.com.atomi.openbanking.authservice.entities.Consent;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

@Repository
public interface ConsentRepository extends JpaRepository<Consent, Long> {
    @Query(
            value = "SELECT e.* FROM ob_consent e WHERE e.consent_id = :consentId",
            nativeQuery = true)
    Optional<Consent> finByConsentId(String consentId);
    @Transactional
    @Modifying
    @Query(
            value = "UPDATE ob_consent SET status =:status WHERE consent_id =:consentId",
            nativeQuery = true)
    int updateStatus(String status, String consentId);
}