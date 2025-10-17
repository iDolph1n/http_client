import org.junit.jupiter.api.Test;
import ru.netology.ClientHandler;
import ru.netology.Request;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.Socket;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ServerTest {

    @Test
    public void testRequestParsingWithQuery() {
        Request request = new Request("/messages?last=10&user=42&user=7");

        // Проверяем путь
        assertEquals("/messages", request.getPath());

        // Проверяем одиночный параметр
        assertEquals("10", request.getQueryParam("last"));

        // Проверяем параметр с несколькими значениями
        List<String> users = request.getQueryParams().get("user");
        assertNotNull(users);
        assertEquals(2, users.size());
        assertTrue(users.contains("42"));
        assertTrue(users.contains("7"));

        // Параметр отсутствует
        assertNull(request.getQueryParam("missing"));
    }

    @Test
    public void testClientHandlerDoesNotThrow() {
        assertDoesNotThrow(() -> {
            // Создаем "фейковый" сокет с пустым InputStream
            InputStream fakeInput = new ByteArrayInputStream("GET /test HTTP/1.1\n".getBytes());
            Socket fakeSocket = new Socket() {
                @Override
                public InputStream getInputStream() {
                    return fakeInput;
                }
            };

            ClientHandler handler = new ClientHandler(fakeSocket);
            new Thread(handler).start();
        });
    }
}