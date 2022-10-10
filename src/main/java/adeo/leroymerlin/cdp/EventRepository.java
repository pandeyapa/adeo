package adeo.leroymerlin.cdp;

import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface EventRepository extends Repository<Event, Long> {

    void deleteById(Long eventId);

    List<Event> findAll();
    
    Event save(Event event);
}
