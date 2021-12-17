import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JSoupRun {
    public static void main(String[] args) throws IOException {

        List<String[]> csvData = createCsvDataSimple();

        // default all fields are enclosed in double quotes
        // default separator is a comma
        try (CSVWriter writer = new CSVWriter(new FileWriter("c:\\test\\test.csv"))) {
            writer.writeAll(csvData);
        }

    }

    private static List<String[]> createCsvDataSimple() throws IOException{
        String[] header = {"Nomor", "Nama Barang", "Image Link", "Price", "Rating", "Merchant"};
        List<String[]> list = new ArrayList<>();



        list.add(header);



        Integer count = 0 ;
        outerloop:
        for (int i = 1; i <= 10; i++) {
            Document doc = Jsoup.connect("https://www.tokopedia.com/search?navsource=home&page="+i+"&q=Handphone&st=product").timeout(12000).get();
            Elements css = doc.getElementsByClass("css-12sieg3");

            /*String Title = doc.getElementsByClass("css-12sieg3").attr("css-12fc2sy");
            System.out.println(Title);*/


//            Elements link = css.select("css-12fc2sy");
//            String linkText = link.text();

//            Elements css = doc.getElementsByClass("css-12fc2sy");
//            System.out.println(css);
//            System.out.println(linkText);

//            System.out.println("Looping ke-"+i);
            Integer k = 0;
            for (Element rowcss : css) {
                // Extract the title

//            String ImageLink = rowcss.getElementsByClass("img[src$=.jpg.webp?ect=4g]").text();
//            String ImageLink = rowcss.getElementsByClass("css-1sfomcl").attr("src");

                String Rate = rowcss.getElementsByClass("css-1ffszw6").text();

                if (!Rate.equals(""))
                {
                    String Title = rowcss.getElementsByClass("css-12fc2sy").text();

//                String Title = rowcss.getElementsByClass("css-12fc2sy").text();
                    // Format and print the information to the console
//            Elements ImageLink = rowcss.select("img[src$=.jpg.webp?ect=4g]");
                    Element Link = rowcss.select("img[class='success fade']").first();
                    String url;
                    if (Link == null)
                    {
                        url = "Image not Found";
                    }
                    else
                    {
                        url = Link.absUrl("src");
                    }
                    String Price = rowcss.getElementsByClass("css-a94u6c").text();
                    String Store = rowcss.getElementsByClass("css-qjiozs flip").text();

//                System.out.println(url);
                    count = count+1;
                    k = k+1;
                    /*System.out.println(count);
                    System.out.println(k);
                    System.out.println("\t"+Title);
                    System.out.println("\t"+ url);
                    System.out.println("\t"+Price);
                    System.out.println("\t"+Rate);
                    System.out.println("\t"+Store);
                    System.out.println("\n");*/
                    if (count == 101)
                    {
                        break outerloop;
                    }
                    String[] record= new String[6];
                    record[0] = count.toString();
                    record[1] = Title;
                    record[2] = url;
                    record[3] = Price;
                    record[4] = Rate;
                    record[5] = Store;
                    list.add(record);
                }


            }
        }

        return list;
    }
}
