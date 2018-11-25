package ru.babak.unlike;
import java.io.IOException;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException, SQLException {

        DB db = new DB();
        db.connect( "likes.db" );

        db.delete( "test" );
        db.isDeleted( "test" );


        LinksGetter linksGetter = new LinksGetter();
        linksGetter.start( db );

        System.in.read();

    }

}
