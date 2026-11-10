package net.tayebi.tp4axon.query.repository;

//import net.tayebi.tp4axon.query.entities.Account;
import net.tayebi.tp4axon.query.entities.AccountOperation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountOperationRepository extends JpaRepository<AccountOperation, Long> {
    List<AccountOperation> findByAccountId(String id);
}
