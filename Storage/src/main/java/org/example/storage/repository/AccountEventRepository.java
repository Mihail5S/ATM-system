package org.example.storage.repository;

import org.example.storage.model.AccountEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountEventRepository extends JpaRepository<AccountEventEntity, String> {
}