package repository;

import domain.sweets.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.sql.*;
import java.util.List;
import java.util.Properties;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SweetsRepositoryTest {

    @Mock Connection mockConn;
    @Mock PreparedStatement mockPs;
    @Mock ResultSet mockRs;

    @Test
    void loadSweetsFromDb() throws SQLException {
        try (MockedStatic<DbUtil> mockedDbUtil = Mockito.mockStatic(DbUtil.class)) {
            mockedDbUtil.when(DbUtil::getConnection).thenReturn(mockConn);
            when(mockConn.createStatement()).thenReturn(mock(Statement.class));
            when(mockConn.createStatement().executeQuery(anyString())).thenReturn(mockRs);

            when(mockRs.next()).thenReturn(true, true, false);
            when(mockRs.getString(anyString())).thenReturn("candy");

            List<Sweets> sweets = SweetsRepository.loadSweetsFromDb();
            assertEquals(2, sweets.size());
        }
    }

    @Test
    void saveSweetToDb_success() throws SQLException {
        Candy candy = new Candy(0, "CandyName", 10.0, 0.5, 20.0, "Manufacturer", "Type", null);

        try (MockedStatic<DbUtil> mockedDbUtil = Mockito.mockStatic(DbUtil.class)) {
            mockedDbUtil.when(DbUtil::getConnection).thenReturn(mockConn);
            when(mockConn.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(mockPs);
            when(mockConn.prepareStatement(anyString())).thenReturn(mockPs);
            when(mockPs.getGeneratedKeys()).thenReturn(mockRs);
            when(mockRs.next()).thenReturn(true);
            when(mockRs.getInt(1)).thenReturn(123);

            boolean result = SweetsRepository.saveSweetToDb(candy);
            assertTrue(result);
            assertEquals(123, candy.getCode());
            verify(mockPs, times(2)).executeUpdate();
        }
    }

    @Test
    void saveChocolateToDb_success() throws SQLException {
        Chocolate chocolate = new Chocolate(0, "Choco", 12.0, 0.6, 22.0, 85.0, "hazelnut", "BITTER", null);

        try (MockedStatic<DbUtil> mockedDbUtil = Mockito.mockStatic(DbUtil.class)) {
            mockedDbUtil.when(DbUtil::getConnection).thenReturn(mockConn);

            when(mockConn.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(mockPs);
            when(mockConn.prepareStatement(anyString())).thenReturn(mockPs);
            when(mockPs.getGeneratedKeys()).thenReturn(mockRs);
            when(mockRs.next()).thenReturn(true);
            when(mockRs.getInt(1)).thenReturn(456);

            boolean result = SweetsRepository.saveSweetToDb(chocolate);
            assertTrue(result);
            assertEquals(456, chocolate.getCode());
            verify(mockPs, times(2)).executeUpdate();
        }
    }

    @Test
    void saveJellyToDb_success() throws SQLException {
        Jelly jelly = new Jelly(0, "JellyBear", 7.0, 0.3, 10.0, "apple", "star", null);

        try (MockedStatic<DbUtil> mockedDbUtil = Mockito.mockStatic(DbUtil.class)) {
            mockedDbUtil.when(DbUtil::getConnection).thenReturn(mockConn);

            when(mockConn.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(mockPs);
            when(mockConn.prepareStatement(anyString())).thenReturn(mockPs);
            when(mockPs.getGeneratedKeys()).thenReturn(mockRs);
            when(mockRs.next()).thenReturn(true);
            when(mockRs.getInt(1)).thenReturn(789);

            boolean result = SweetsRepository.saveSweetToDb(jelly);
            assertTrue(result);
            assertEquals(789, jelly.getCode());
            verify(mockPs, times(2)).executeUpdate();
        }
    }

    @Test
    void saveGingerbreadToDb_success() throws SQLException {
        Gingerbread ginger = new Gingerbread(0, "GingerMan", 9.0, 0.4, 14.0, "tree", true, null);

        try (MockedStatic<DbUtil> mockedDbUtil = Mockito.mockStatic(DbUtil.class)) {
            mockedDbUtil.when(DbUtil::getConnection).thenReturn(mockConn);

            when(mockConn.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(mockPs);
            when(mockConn.prepareStatement(anyString())).thenReturn(mockPs);
            when(mockPs.getGeneratedKeys()).thenReturn(mockRs);
            when(mockRs.next()).thenReturn(true);
            when(mockRs.getInt(1)).thenReturn(321);

            boolean result = SweetsRepository.saveSweetToDb(ginger);
            assertTrue(result);
            assertEquals(321, ginger.getCode());
            verify(mockPs, times(2)).executeUpdate();
        }
    }

    @Test
    void deleteSweetFromDb_success() throws SQLException {
        try (MockedStatic<DbUtil> mockedDbUtil = Mockito.mockStatic(DbUtil.class)) {
            mockedDbUtil.when(DbUtil::getConnection).thenReturn(mockConn);

            when(mockConn.prepareStatement(anyString())).thenReturn(mockPs);
            when(mockPs.executeUpdate()).thenReturn(1);

            boolean result = SweetsRepository.deleteSweetFromDb(123);
            assertTrue(result);
            verify(mockPs, times(5)).executeUpdate();
        }
    }

    @Test
    void findSweetsByPrice_success() throws SQLException {
        try (MockedStatic<DbUtil> mockedDbUtil = Mockito.mockStatic(DbUtil.class)) {
            mockedDbUtil.when(DbUtil::getConnection).thenReturn(mockConn);

            when(mockConn.prepareStatement(anyString())).thenReturn(mockPs);
            when(mockPs.executeQuery()).thenReturn(mockRs);

            when(mockRs.next()).thenReturn(true, false);
            when(mockRs.getString(anyString())).thenReturn("candy");

            List<Sweets> result = SweetsRepository.findSweetsByPrice(10.0, 20.0);
            assertEquals(1, result.size());
            verify(mockPs).setDouble(1, 10.0);
            verify(mockPs).setDouble(2, 20.0);
        }
    }

    @Test
    void updateSweetInDb_success() throws SQLException {
        Candy candy = new Candy(0, "CandyName", 10.0, 0.5, 20.0, "Manufacturer",  "Type",null);

        try (MockedStatic<DbUtil> mockedDbUtil = Mockito.mockStatic(DbUtil.class)) {
            mockedDbUtil.when(DbUtil::getConnection).thenReturn(mockConn);

            when(mockConn.prepareStatement(anyString())).thenReturn(mockPs);

            boolean result = SweetsRepository.updateSweetInDb(candy);
            assertTrue(result);
            verify(mockPs, times(2)).executeUpdate();
        }
    }

    @Test
    void updateChocolateInDb_success() throws SQLException {
        Chocolate choco = new Chocolate(0, "Choco", 15.0, 0.8, 25.0, 70.0, "nuts", "DARK", null);

        try (MockedStatic<DbUtil> mockedDbUtil = Mockito.mockStatic(DbUtil.class)) {
            mockedDbUtil.when(DbUtil::getConnection).thenReturn(mockConn);
            when(mockConn.prepareStatement(anyString())).thenReturn(mockPs);

            boolean result = SweetsRepository.updateSweetInDb(choco);
            assertTrue(result);
            verify(mockPs, times(2)).executeUpdate();
        }
    }

    @Test
    void updateJellyInDb_success() throws SQLException {
        Jelly jelly = new Jelly(0, "JellyBear", 8.0, 0.3, 12.0, "strawberry", "round", null);

        try (MockedStatic<DbUtil> mockedDbUtil = Mockito.mockStatic(DbUtil.class)) {
            mockedDbUtil.when(DbUtil::getConnection).thenReturn(mockConn);
            when(mockConn.prepareStatement(anyString())).thenReturn(mockPs);

            boolean result = SweetsRepository.updateSweetInDb(jelly);
            assertTrue(result);
            verify(mockPs, times(2)).executeUpdate();
        }
    }

    @Test
    void updateGingerbreadInDb_success() throws SQLException {
        Gingerbread ginger = new Gingerbread(0, "Ginger", 11.0, 0.4, 14.0, "star", true, null);

        try (MockedStatic<DbUtil> mockedDbUtil = Mockito.mockStatic(DbUtil.class)) {
            mockedDbUtil.when(DbUtil::getConnection).thenReturn(mockConn);
            when(mockConn.prepareStatement(anyString())).thenReturn(mockPs);

            boolean result = SweetsRepository.updateSweetInDb(ginger);
            assertTrue(result);
            verify(mockPs, times(2)).executeUpdate();
        }
    }

    @Test
    void insertChocolateDetails_success() throws SQLException {
        Properties props = new Properties();
        props.setProperty("insert.chocolateDetails", "INSERT INTO chocolate_details VALUES (?, ?, ?, ?)");

        SweetsRepository repo = new SweetsRepository(props);
        Chocolate chocolate = new Chocolate(0, "Choco", 12.0, 0.3, 15.0, 85.5, "hazelnut", "bitter", null);

        when(mockConn.prepareStatement(anyString())).thenReturn(mockPs);

        repo.insertChocolateDetails(mockConn, 101, chocolate);

        verify(mockPs).setInt(1, 101);
        verify(mockPs).setDouble(2, 85.5);
        verify(mockPs).setString(3, "hazelnut");
        verify(mockPs).setString(4, "bitter");
        verify(mockPs).executeUpdate();
    }

    @Test
    void insertJellyDetails_success() throws SQLException {
        Properties props = new Properties();
        props.setProperty("insert.jellyDetails", "INSERT INTO jelly_details VALUES (?, ?, ?)");

        SweetsRepository repo = new SweetsRepository(props);
        Jelly jelly = new Jelly(0, "JellyBear", 6.0, 0.2, 5.0, "strawberry", "round", null);

        when(mockConn.prepareStatement(anyString())).thenReturn(mockPs);

        repo.insertJellyDetails(mockConn, 103, jelly);

        verify(mockPs).setInt(1, 103);
        verify(mockPs).setString(2, "strawberry");
        verify(mockPs).setString(3, "round");
        verify(mockPs).executeUpdate();
    }

    @Test
    void insertGingerbreadDetails_success() throws SQLException {
        Properties props = new Properties();
        props.setProperty("insert.gingerbreadDetails", "INSERT INTO gingerbread_details VALUES (?, ?, ?)");

        SweetsRepository repo = new SweetsRepository(props);
        Gingerbread gingerbread = new Gingerbread(0, "Ginger", 8.0, 0.4, 10.0, "tree", true, null);

        when(mockConn.prepareStatement(anyString())).thenReturn(mockPs);

        repo.insertGingerbreadDetails(mockConn, 102, gingerbread);

        verify(mockPs).setInt(1, 102);
        verify(mockPs).setString(2, "tree");
        verify(mockPs).setBoolean(3, true);
        verify(mockPs).executeUpdate();
    }

    @Test
    void updateChocolateDetails_success() throws SQLException {
        Properties props = new Properties();
        props.setProperty("update.chocolateDetails", "UPDATE chocolate_details SET cocoa = ?, filling = ?, type = ? WHERE code = ?");

        SweetsRepository repo = new SweetsRepository(props);
        Chocolate chocolate = new Chocolate(0, "Choco", 12.0, 0.3, 15.0, 85.5, "hazelnut", "bitter", null);

        when(mockConn.prepareStatement(anyString())).thenReturn(mockPs);

        repo.updateChocolateDetails(mockConn, 201, chocolate);

        verify(mockPs).setDouble(1, 85.5);
        verify(mockPs).setString(2, "hazelnut");
        verify(mockPs).setString(3, "bitter");
        verify(mockPs).setInt(4, 201);
        verify(mockPs).executeUpdate();
    }

    @Test
    void updateJellyDetails_success() throws SQLException {
        Properties props = new Properties();
        props.setProperty("update.jellyDetails", "UPDATE jelly_details SET taste = ?, shape = ? WHERE code = ?");

        SweetsRepository repo = new SweetsRepository(props);
        Jelly jelly = new Jelly(0, "JellyBear", 6.0, 0.2, 5.0, "strawberry", "circle", null);

        when(mockConn.prepareStatement(anyString())).thenReturn(mockPs);

        repo.updateJellyDetails(mockConn, 202, jelly);

        verify(mockPs).setString(1, "strawberry");
        verify(mockPs).setString(2, "circle");
        verify(mockPs).setInt(3, 202);
        verify(mockPs).executeUpdate();
    }

    @Test
    void updateGingerbreadDetails_success() throws SQLException {
        Properties props = new Properties();
        props.setProperty("update.gingerbreadDetails", "UPDATE gingerbread_details SET shape = ?, iced = ? WHERE code = ?");

        SweetsRepository repo = new SweetsRepository(props);
        Gingerbread gingerbread = new Gingerbread(0, "Ginger", 8.0, 0.4, 10.0, "tree", true, null);

        when(mockConn.prepareStatement(anyString())).thenReturn(mockPs);

        repo.updateGingerbreadDetails(mockConn, 203, gingerbread);

        verify(mockPs).setString(1, "tree");
        verify(mockPs).setBoolean(2, true);
        verify(mockPs).setInt(3, 203);
        verify(mockPs).executeUpdate();
    }

    @Test
    void testSaveSweetToDb_throwsSQLException() throws SQLException {
        Sweets sweet = new Candy(0, "Test", 1, 1, 1, "fill", "type", null);

        try (MockedStatic<DbUtil> mockedDb = Mockito.mockStatic(DbUtil.class)) {
            Connection mockConn = mock(Connection.class);
            mockedDb.when(DbUtil::getConnection).thenReturn(mockConn);
            when(mockConn.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenThrow(new SQLException("DB error"));

            boolean result = SweetsRepository.saveSweetToDb(sweet);
            assertFalse(result);
        }
    }

    @Test
    void testUpdateSweetInDb_throwsSQLException() throws SQLException {
        Sweets sweet = new Candy(0, "Test", 1, 1, 1, "fill", "type", null);

        try (MockedStatic<DbUtil> mockedDb = Mockito.mockStatic(DbUtil.class)) {
            Connection mockConn = mock(Connection.class);
            PreparedStatement ps = mock(PreparedStatement.class);

            mockedDb.when(DbUtil::getConnection).thenReturn(mockConn);
            when(mockConn.prepareStatement(anyString())).thenReturn(ps);
            doThrow(new SQLException("Update failed")).when(ps).executeUpdate();

            boolean result = SweetsRepository.updateSweetInDb(sweet);
            assertFalse(result);
        }
    }

    @Test
    void testDeleteSweetFromDb_throwsSQLException() throws SQLException {
        try (MockedStatic<DbUtil> mockedDb = Mockito.mockStatic(DbUtil.class)) {
            Connection mockConn = mock(Connection.class);
            mockedDb.when(DbUtil::getConnection).thenReturn(mockConn);

            when(mockConn.prepareStatement(anyString())).thenThrow(new SQLException("Delete error"));
            assertThrows(SQLException.class, () -> SweetsRepository.deleteSweetFromDb(999));
        }
    }

    @Test
    void testLoadSweetsFromDb_throwsSQLException() throws SQLException {
        try (MockedStatic<DbUtil> mockedDb = Mockito.mockStatic(DbUtil.class)) {
            Connection mockConn = mock(Connection.class);
            Statement mockStatement = mock(Statement.class);

            mockedDb.when(DbUtil::getConnection).thenReturn(mockConn);
            when(mockConn.createStatement()).thenReturn(mockStatement);
            when(mockStatement.executeQuery(anyString())).thenThrow(new SQLException("Load error"));

            List<Sweets> result = SweetsRepository.loadSweetsFromDb();
            assertTrue(result.isEmpty());
        }
    }
}