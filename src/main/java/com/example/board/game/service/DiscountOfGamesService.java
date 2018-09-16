package com.example.board.game.service;

import com.example.board.game.model.dto.GameOffersDTO;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


@Service
public class DiscountOfGamesService {
    private static final String BASE_URL = "https://www.gry-planszowe.pl/forum/viewtopic.php?f=34&t=28633&start=4975";


    private Date parseDate(String date)  {
        String s = date.replaceAll(",", "");
        DateFormat fmt = new SimpleDateFormat("dd MMMM yyyy hh:mm", Locale.getDefault());
        Date d= null;
        try {
            d = fmt.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }


    public List<GameOffersDTO> getContentFromWeb() throws IOException {
        List<String> content = new ArrayList<>();
        URLConnection connection = new URL(BASE_URL).openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        connection.connect();
        BufferedReader r = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = r.readLine()) != null) {
            sb.append(line);
            content.add(line);
        }
        return findAllPost(content);

    }

    private List<GameOffersDTO> findAllPost(List<String> content) throws IOException {
        List<GameOffersDTO> gameOffers = new ArrayList<>();
        Date date = null;
        for (String line : content) {
            int startIndexOfPost = 0;
            if (line.contains("<div class=\"content\"")) {
                GameOffersDTO gameOffer = new GameOffersDTO();
                String offer = "";
                String s = fixLine(line);
                offer = new StringBuilder(offer).append(s).toString();
                startIndexOfPost = content.indexOf(line);
                for (int i = startIndexOfPost + 1; i < startIndexOfPost + 15; i++) {
                    String s1 = fixLine(content.get(i));
                    if (!s1.equals("")) {
                        if (s1.contains("<div id=\"sig") || s1.contains("<div class=\"back2top\"")) {
                            break;
                        } else {
                            offer = new StringBuilder(offer).append(s1).toString();
                        }
                    }
                }
                gameOffer.setOffer(offer);
                gameOffer.setDate(date);
                gameOffers.add(gameOffer);
            } else if (line.contains("class=\"username\">") && line.contains("</span>")) {
                String dateToFormat = line.substring(line.length() - 18, line.length());
                date = parseDate(dateToFormat);
            }
        }
        return gameOffers;
    }

    private String fixLine(String string) {
        return string
                .replaceAll("<div class=\"content\">", " ")
                .replaceAll("</div>", " ")
                .replaceAll("<br>", " ")
                .replaceAll("<a href=\"", " ")
                .replaceAll("\" class.*</a>", " ")
                .replaceAll("<blockquote>.*</cite>", "Cytat: ")
                .replaceAll("</blockquote>", " ")
                .replaceAll("<img.*>", " ");
    }
}
