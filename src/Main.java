import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String url;

        while (true) {
            System.out.println("Введите URL ресурса:");
            url = scanner.nextLine();

            if (isValidURL(url)) {
                try {
                    readContent(url);
                    break;
                } catch (Exception e) {
                    System.out.println("Ошибка при подключении: " + e.getMessage());
                }
            } else {
                System.out.println("Неверный формат URL. Попробуйте снова.");
            }
        }
        scanner.close();
    }

    public static void readContent(String urlString) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new Exception("Невозможно подключиться к ресурсу. Код ответа: " + responseCode);
        }
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
            }
        }
    }

    public static boolean isValidURL(String urlString) {
        String regex = "^(http|https)://[a-zA-Z0-9.-]+\\.[a-z]{2,}.*$";
        return urlString.matches(regex);
    }

}