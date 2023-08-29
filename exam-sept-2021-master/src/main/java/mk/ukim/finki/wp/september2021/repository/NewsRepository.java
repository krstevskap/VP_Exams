package mk.ukim.finki.wp.september2021.repository;

import mk.ukim.finki.wp.september2021.model.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository  extends JpaRepository<News, Long> {
}
