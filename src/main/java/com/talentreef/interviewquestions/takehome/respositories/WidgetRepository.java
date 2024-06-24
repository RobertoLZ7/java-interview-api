package com.talentreef.interviewquestions.takehome.respositories;

import com.talentreef.interviewquestions.takehome.models.Widget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface WidgetRepository extends JpaRepository<Widget, String> {
    Optional<Widget> findByNameIgnoreCase(String name);
}
