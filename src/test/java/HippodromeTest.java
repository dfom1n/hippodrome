import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class HippodromeTest {
    @Test
    public void nullException(){
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()->new Hippodrome(null));
        assertEquals("Horses cannot be null.", exception.getMessage());
    }
    @Test
    public void emptyListToConstructor(){
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()->new Hippodrome(List.of()));
        assertEquals("Horses cannot be empty.", exception.getMessage());
    }

    @Test
    public void getHorses(){
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            horses.add(new Horse("Horse", ThreadLocalRandom.current().nextDouble(1,10), ThreadLocalRandom.current().nextDouble(1,10)));
        }

        Hippodrome hippodrome = new Hippodrome(horses);
        assertEquals(horses, hippodrome.getHorses());
    }

    @Test
    public void move(){
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            horses.add(mock(Horse.class));
        }
        Hippodrome hippodrome = new Hippodrome(horses);
        hippodrome.move();

        for (Horse hors : horses) {
        Mockito.verify(hors).move();
        }
    }

    @Test
    public void getWinner(){
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            horses.add(new Horse("Horse", i, i));
        }
        assertSame(horses.get(29), new Hippodrome(horses).getWinner());
    }
}
