import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;


public class MatchTest {
    Match match;
    
    @Before
    public void setUp() throws Exception {
       match = new Match(1, 800, 600, 5);
    }
    
    @Test
    public void testConstructor() {
        assertEquals(1, match.id);
        assertEquals(800, match.width);
        assertEquals(600, match.height);
        assertEquals(5, match.numRounds);
        assertEquals(0, match.entries.size());
    }
    
    @Test
    public void testAddEntry() {
        match.addEntry(new Entry(1, "foo"));
        match.addEntry(new Entry(2, "bar"));
        assertEquals(2, match.entries.size());
    }

    @Test
    public void testGetBotString() {
        match.addEntry(new Entry(1, "foo_bar.foo_1.0"));
        match.addEntry(new Entry(2, "bar"));
        match.addEntry(new Entry(3, "baz"));
        assertEquals("foo_bar.foo 1.0,bar,baz", match.getBotString());
    }
}
