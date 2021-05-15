package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore primaryMockTS;
  private TorpedoStore secondaryMockTS;

  @BeforeEach
  public void init(){
    primaryMockTS = mock(TorpedoStore.class);
    secondaryMockTS = mock(TorpedoStore.class);
    this.ship = new GT4500(primaryMockTS, secondaryMockTS);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(primaryMockTS.fire(1)).thenReturn(true);

    // Act
    ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(primaryMockTS, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(primaryMockTS.fire(1)).thenReturn(true);
    // Act
    ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(primaryMockTS, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_PrimaryFiredLast(){
    // Arrange
    when(primaryMockTS.fire(1)).thenReturn(true);
    // Act
    ship.fireTorpedo(FiringMode.SINGLE);
    boolean result = ship.getPrimaryFiredLast();

    // Assert
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_All_PrimaryFiredLast(){
    // Arrange
    when(primaryMockTS.fire(1)).thenReturn(true);
    // Act
    ship.fireTorpedo(FiringMode.ALL);
    boolean result = ship.getPrimaryFiredLast();

    // Assert
    assertEquals(false, result);
  }


  @Test
  public void fireTorpedo_Empty(){
    // Arrange
    assertThrows(NullPointerException.class, ()->{
      ship.fireTorpedo(null);
    });

  }

  @Test
  public void fireTorpedo_EmptyPrimary_Single_Success(){
    // Arrange
    when(primaryMockTS.isEmpty()).thenReturn(true);
    when(secondaryMockTS.fire(1)).thenReturn(true);

    // Act
    ship.fireTorpedo(FiringMode.SINGLE);
    ship.fireTorpedo(FiringMode.SINGLE);
    ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(secondaryMockTS, times(3)).fire(1);
  }

  @Test
  public void fireTorpedo_LotOf_Single_Success(){
    // Arrange
    when(primaryMockTS.fire(0)).thenReturn(false);
    when(secondaryMockTS.fire(1)).thenReturn(true);

    // Act
    ship.fireTorpedo(FiringMode.SINGLE);
    ship.fireTorpedo(FiringMode.SINGLE);
    ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(primaryMockTS, times(2)).fire(1);
    verify(secondaryMockTS, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_EmptySecondary_Single_Success(){
    // Arrange
    when(primaryMockTS.fire(1)).thenReturn(true);
    when(secondaryMockTS.isEmpty()).thenReturn(true);

    // Act
    ship.fireTorpedo(FiringMode.SINGLE);
    ship.fireTorpedo(FiringMode.SINGLE);
    ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(primaryMockTS, times(3)).fire(1);
  }

}
