package adeo.leroymerlin.cdp;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class EventServiceTest {

	private EventRepository repository;

	private EventService service;

	@BeforeEach
	void setUp() {
		repository = Mockito.mock(EventRepository.class);
		service = new EventService(repository);
	}

	@Test
	public void testGetFilteredEvents() {
		String query = "hard";

		Member member11 = new Member();
		member11.setName("Syd Barrett");
		Member member12 = new Member();
		member12.setName("Nick Mason");
		Member member13 = new Member();
		member13.setName("Roger Waters");
		Member member14 = new Member();
		member14.setName("Richard Wright");
		Band band1 = new Band();
		band1.setName("Pink Floyd");
		band1.setMembers(new HashSet<>(Arrays.asList(member11, member12, member13, member14)));
		Event event1 = new Event();
		event1.setTitle("Rock Festival");
		event1.setBands(Collections.singleton(band1));

		Member member21 = new Member();
		member21.setName("Simon James");
		Member member22 = new Member();
		member22.setName("Nick Webb");
		Band band2 = new Band();
		band2.setName("Acoustic Alchemy");
		band2.setMembers(new HashSet<>(Arrays.asList(member21, member22)));
		Event event2 = new Event();
		event2.setTitle("Jazz Festival");
		event2.setBands(Collections.singleton(band2));

		Mockito.doReturn(Arrays.asList(event2, event1)).when(repository).findAll();

		List<Event> filteredEvents = service.getFilteredEvents(query);

		Mockito.verify(repository).findAll();
		Assertions.assertEquals(1, filteredEvents.size());

		Event event = filteredEvents.get(0);
		Assertions.assertEquals("Rock Festival [1]", event.getTitle());

	}

}