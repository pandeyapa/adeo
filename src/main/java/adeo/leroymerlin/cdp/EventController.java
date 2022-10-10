package adeo.leroymerlin.cdp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(final EventService eventService) {
        this.eventService = eventService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Event> findEvents() {
        return eventService.getEvents();
    }

    @RequestMapping(value = "/search/{query}", method = RequestMethod.GET)
    public List<Event> findEvents(@PathVariable final String query) {
        return eventService.getFilteredEvents(query);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteEvent(@PathVariable final Long id) {
        eventService.delete(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void updateEvent(@PathVariable final Long id, @RequestBody final Event event) {
    	eventService.save(event);
    }
}
