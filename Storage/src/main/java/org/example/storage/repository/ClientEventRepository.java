package org.example.storage.repository;

import org.example.storage.model.ClientEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientEventRepository extends JpaRepository<ClientEventEntity, String> {
}
