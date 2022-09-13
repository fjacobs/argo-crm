package nl.argo360.crm.repo;

import nl.argo360.crm.doa.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}
