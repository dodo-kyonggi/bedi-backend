package com.deadline826.bedi.Statistics.Repository;

import com.deadline826.bedi.Statistics.Domain.Complete;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompleteRepository extends JpaRepository<Complete, Long>, CustomCompleteRepository {

}
