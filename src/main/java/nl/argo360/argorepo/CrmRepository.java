package nl.argo360.argorepo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CrmRepository extends JpaRepository<Account, String> {
}
