package adeo.leroymerlin.cdp;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(final EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getEvents() {
        return eventRepository.findAll();
    }

    public void delete(final Long id) {
        eventRepository.deleteById(id);
    }
    
    public void save(final Event event) {
    	eventRepository.save(event);
    }

    public List<Event> getFilteredEvents(final String query) {
        List<Event> events = eventRepository.findAll();
        
        Predicate<Member> memberMatches = member -> member.getName().contains(query);
		List<Event> filteredEvents = events.stream()
				.filter(event -> event.getBands().stream()
						.flatMap(band -> band.getMembers().stream())
						.anyMatch(memberMatches))
				.map(this::updateWithChildCount)
				.collect(Collectors.toList());

        return filteredEvents;
    }
    
	private Event updateWithChildCount(final Event event) {
		event.getBands().stream().forEach(band -> {
			String count = " [" + band.getMembers().size() + "]";
			band.setName(band.getName() + count);
		});

		String count = " [" + event.getBands().size() + "]";
		event.setTitle(event.getTitle() + count);

		return event;
	}

}
