package net.tayebi.tp4axon.query.repository;

import net.tayebi.tp4axon.query.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}
