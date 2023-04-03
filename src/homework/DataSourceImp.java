package homework;

import org.postgresql.ds.PGSimpleDataSource;

public class DataSourceImp {
    public PGSimpleDataSource dataSource(){
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUser("postgres");
        dataSource.setPassword("reachh@414");
        dataSource.setDatabaseName("postgres");
        return dataSource;
    }

}
