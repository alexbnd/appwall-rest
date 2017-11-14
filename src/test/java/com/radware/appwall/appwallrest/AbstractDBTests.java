package com.radware.appwall.appwallrest;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AbstractDBTests {


    @Autowired
    private DataSource dataSource;

    @After
    @Before
    public void tearDown() {
        try {
            clearDatabase();
        } catch(Exception e) {
            //TODO
            //fail(e.getMessage());
        }
    }


    public void clearDatabase() throws Exception {

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            try {
                Statement stmt = connection.createStatement();
                try {
                    stmt.execute("TRUNCATE SCHEMA PUBLIC RESTART IDENTITY AND COMMIT NO CHECK");
                    connection.commit();
                } finally {
                    stmt.close();
                }
            } catch(SQLException e) {
                connection.rollback();
                throw new Exception(e);
            }
        } catch(SQLException e) {
            throw new Exception(e);
        } finally {
            if(connection != null) {
                connection.close();
            }
        }
    }
}
