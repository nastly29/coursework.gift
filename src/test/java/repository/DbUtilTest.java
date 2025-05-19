package repository;

import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class DbUtilTest {

    private MockedStatic<DriverManager> driverManagerMock;

    @BeforeEach
    void openMock() {
        driverManagerMock = Mockito.mockStatic(DriverManager.class);
    }

    @AfterEach
    void closeMock() {
        driverManagerMock.close();
    }

    @Test
    void testGetConnectionSuccess() throws SQLException {
        Connection fakeConn = mock(Connection.class);

        driverManagerMock
                .when(() -> DriverManager.getConnection(
                        "jdbc:sqlserver://localhost:1433;databaseName=SweetsDb;encrypt=false",
                        "anastasia",
                        "12345"))
                .thenReturn(fakeConn);
        Connection conn = DbUtil.getConnection();
        assertSame(fakeConn, conn);
    }

    @Test
    void testGetConnectionFailure() {
        SQLException sqlEx = new SQLException("Connection failed");

        driverManagerMock
                .when(() -> DriverManager.getConnection(
                        "jdbc:sqlserver://localhost:1433;databaseName=SweetsDb;encrypt=false",
                        "anastasia",
                        "12345"))
                .thenThrow(sqlEx);
        SQLException thrown = assertThrows(SQLException.class, DbUtil::getConnection);
        assertEquals("Connection failed", thrown.getMessage());
    }
}