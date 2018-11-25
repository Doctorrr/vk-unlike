package ru.babak.unlike;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Этот класс будет получать ссылки из папки "like", скачанной из ВК https://vk.com/data_protection?section=rules&scroll_to_archive=1
 */
public class LinksGetter {


    public void start( DB db ) throws IOException, InterruptedException, SQLException, ClassNotFoundException {

        ArrayList< vkObj > vkObjs = new ArrayList< vkObj >();

        //TODO: Здесь можно обойтись одной строкой, но уже оставлю как есть, на будущее
        vkObjs.add( new vkObj( "video", "https://vk.com/video" ) );
        vkObjs.add( new vkObj( "photo", "https://vk.com/photo" ) );
        vkObjs.add( new vkObj( "market", "https://vk.com/market" ) );
        vkObjs.add( new vkObj( "note", "https://vk.com/note" ) );
        vkObjs.add( new vkObj( "wall", "https://vk.com/wall" ) );

        Navigator navigator = new Navigator();

        for( vkObj obj : vkObjs ) {
            //Получаем ссылки
            ArrayList<String> links = parce( obj );

            //снимаем лайки
            Navigator.unlike( links, obj, db );

        }

    }


    public ArrayList<String> parce( vkObj obj ) throws IOException {
        ArrayList<String> links = new ArrayList<String>();

        Pattern p = Pattern.compile("href=\""+ obj.urlPattern +"(.*?)\"");
//        Files.newDirectoryStream( Paths.get("F:\\WORK\\JAVA\\unlike\\data\\likes\\" + obj.folderName ), path -> path.toString().endsWith(".html") )
        Files.newDirectoryStream( Paths.get("likes\\" + obj.folderName ), path -> path.toString().endsWith(".html") )
                .forEach( filename -> {

                    try {
                        String fileContent = new String( Files.readAllBytes( Paths.get( String.valueOf(filename) ) ), "UTF-8");
                        Matcher m = p.matcher( fileContent );
                        String url = null;

                        while (m.find()) {
                            url = obj.urlPattern + m.group(1); // this variable should contain the link URL
                            links.add( url );
                        }

                    } catch (IOException e) {
                        e.printStackTrace();

                    }

                });

        return links;

    }

}
