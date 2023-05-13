package training360.questions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import training360.questions.domain.Question;
import training360.questions.dto.NameQuestionPair;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query("select new training360.questions.dto.NameQuestionPair(m.name, q.questionText) from Member m inner join m.questions q where q.answered = false order by m.name, q.questionText")
    List<NameQuestionPair> findPairs();
}
