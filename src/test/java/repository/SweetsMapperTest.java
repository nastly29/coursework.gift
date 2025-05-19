package repository;

import domain.sweets.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.ResultSet;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SweetsMapperTest {

    private SweetsMapper mapper;
    private ResultSet rs;

    @BeforeEach
    void setUp() {
        mapper = new SweetsMapper();
        rs = mock(ResultSet.class);
    }

    private void commonResultSetStubbing(String type) throws SQLException {
        when(rs.getInt("Code")).thenReturn(42);
        when(rs.getString("TypeName")).thenReturn(type);
        when(rs.getString("Name")).thenReturn("TestName");
        when(rs.getDouble("Price")).thenReturn(10.5);
        when(rs.getDouble("Weight")).thenReturn(2.0);
        when(rs.getDouble("SugarContent")).thenReturn(1.2);
        when(rs.getBytes("ImageData")).thenReturn(new byte[] {1,2,3});
    }

    @Test
    void mapCandy() throws SQLException {
        commonResultSetStubbing("candy");
        when(rs.getString("Filling")).thenReturn("Caramel");
        when(rs.getString("CandyType")).thenReturn("Hard");

        Sweets sweet = mapper.map(rs);
        assertTrue(sweet instanceof Candy);
        Candy c = (Candy) sweet;
        assertEquals(42, c.getCode());
        assertEquals("TestName", c.getName());
        assertEquals(2.0, c.getWeight());
        assertEquals(1.2, c.getSugarContent());
        assertEquals(10.5, c.getPrice());
        assertEquals("Caramel", c.getFilling());
        assertEquals("Hard", c.getType());
        assertArrayEquals(new byte[]{1,2,3}, c.getImageData());
    }

    @Test
    void mapChocolate() throws SQLException {
        commonResultSetStubbing("Chocolate");
        when(rs.getDouble("CocoaPercent")).thenReturn(70.0);
        when(rs.getString("ChocFill")).thenReturn("Nut");
        when(rs.getString("ChocolateType")).thenReturn("Dark");

        Sweets sweet = mapper.map(rs);
        assertTrue(sweet instanceof Chocolate);
        Chocolate c = (Chocolate) sweet;
        assertEquals(70.0, c.getCocoaPercentage());
        assertEquals("Nut", c.getFilling());
        assertEquals("Dark", c.getType());
    }

    @Test
    void mapJelly() throws SQLException {
        commonResultSetStubbing("jelly");
        when(rs.getString("FruityTaste")).thenReturn("Strawberry");
        when(rs.getString("JellyShape")).thenReturn("Bear");

        Sweets sweet = mapper.map(rs);
        assertTrue(sweet instanceof Jelly);
        Jelly j = (Jelly) sweet;
        assertEquals("Strawberry", j.getFruityTaste());
        assertEquals("Bear", j.getShape());
    }

    @Test
    void mapGingerbread() throws SQLException {
        commonResultSetStubbing("gingerbread");
        when(rs.getString("GingerbreadShape")).thenReturn("Heart");
        when(rs.getBoolean("IsIced")).thenReturn(true);

        Sweets sweet = mapper.map(rs);
        assertTrue(sweet instanceof Gingerbread);
        Gingerbread g = (Gingerbread) sweet;
        assertEquals("Heart", g.getShape());
        assertTrue(g.isIced());
    }

    @Test
    void mapUnknownTypeThrows() throws SQLException {
        commonResultSetStubbing("unknownType");
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> mapper.map(rs)
        );
        assertTrue(ex.getMessage().contains("Невідомий вид солодощів"));
    }

    @Test
    void mapPropagatesSQLException() throws SQLException {
        when(rs.getInt("Code")).thenThrow(new SQLException("DB error"));
        SQLException ex = assertThrows(
                SQLException.class,
                () -> mapper.map(rs)
        );
        assertEquals("DB error", ex.getMessage());
    }
}