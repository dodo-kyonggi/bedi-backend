package com.deadline826.bedi.statistics.repository;

import com.deadline826.bedi.statistics.domain.Complete;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompleteRepository extends JpaRepository<Complete, Long>, CustomCompleteRepository {

}
