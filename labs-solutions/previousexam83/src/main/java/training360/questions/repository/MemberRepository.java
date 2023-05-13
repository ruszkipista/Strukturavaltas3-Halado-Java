package training360.questions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import training360.questions.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {


}
