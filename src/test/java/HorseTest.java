import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class HorseTest {
    @Test
    public void nullException() {
        assertThrows(IllegalArgumentException.class, () -> new Horse(null, 2.0, 2.0));
    }

    @Test
    public void nullAssertEqualsName() {
        try {
            Horse horse = new Horse(null, 3, 5);
        } catch (IllegalArgumentException exception) {
            assertEquals("Name cannot be null.", exception.getMessage());
        }

    }

//    @ParameterizedTest
//    @CsvSource({
//            ",2.3,4.0",
//            " ,2.3,4.0",
//            "   ,2.3,4.0",
//            "\n\n\n\n,2.3,4.0",
//            "\t\t\t\t\t,2.3,4.0",
//            "   ,2.3,4.0"
//    })
//    public void whiteSpaceCharactersException(String name, double speed, double distance){
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()-> new Horse(name,speed,distance));
//        assertEquals( "Name cannot be blank.", exception.getMessage());
//
//    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   ", "\n\n\n\n", "\t\t\t\t\t", "   "})
    public void whiteSpaceCharactersException(String name) {
        assertThrows(IllegalArgumentException.class, () -> new Horse(name, 2, 4));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   ", "\n\n\n\n", "\t\t\t\t\t", "   "})
    public void whitespaceCharactersExceptionName(String name) {
        try {
            new Horse(name, 2, 4);
            fail();
        } catch (IllegalArgumentException exception) {
            assertEquals("Name cannot be blank.", exception.getMessage());
        }

    }

    @Test
    public void negativeSpeed() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Horse("Dodge", -2.0, 8));
        assertEquals("Speed cannot be negative.", exception.getMessage());

    }

    @Test
    public void negativeDistance() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Horse("VAG", 4, -8));
        assertEquals("Distance cannot be negative.", exception.getMessage());
    }

    @Test
    public void getName() {
        String expectedName = "vaG";
        Horse horse = new Horse(expectedName, 8, 9);
        assertEquals(expectedName, horse.getName());
    }

    @Test
    public void getSpeed() {
        Double expectedSpeed = 8.1;
        Horse horse = new Horse("vaG", expectedSpeed, 9);
        assertEquals(expectedSpeed, horse.getSpeed());
    }

    @Test
    public void getDistance() {
        Double expectedDistance = 8.1;
        Double expectedDistanceNull = 0d;
        Horse horse = new Horse("vaG", 8, expectedDistance);
        assertEquals(expectedDistance, horse.getDistance());
        Horse horseNoDistance = new Horse("vaG", 8);
        assertEquals(expectedDistanceNull, horseNoDistance.getDistance());
    }

    @Test
    public void move() {
        try (MockedStatic<Horse> mockedStatic = Mockito.mockStatic(Horse.class)) {
            new Horse("vaG", 8, 4).move();
            mockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.1, 0.2, 0.4, 0.5, 0.6, 0.7, 0.0})
    public void moveParameterized(Double random) {
        try (MockedStatic<Horse> mockedStatic = Mockito.mockStatic(Horse.class)) {
            Horse horse = new Horse("vaG", 8, 4);
            mockedStatic.when(()-> Horse.getRandomDouble(0.2,0.9)).thenReturn(random);

            horse.move();

            assertEquals(4+8*random, horse.getDistance());
        }
    }


}
