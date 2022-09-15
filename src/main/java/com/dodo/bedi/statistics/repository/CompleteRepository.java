package com.dodo.bedi.statistics.repository;

import com.dodo.bedi.statistics.domain.Complete;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompleteRepository extends JpaRepository<Complete, Long>, CustomCompleteRepository {

}
