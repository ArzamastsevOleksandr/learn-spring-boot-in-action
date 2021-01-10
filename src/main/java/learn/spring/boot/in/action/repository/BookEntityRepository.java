package learn.spring.boot.in.action.repository;

import learn.spring.boot.in.action.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookEntityRepository extends JpaRepository<BookEntity, Long> {

    List<BookEntity> findByReader(String reader);

}
