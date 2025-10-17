import org.junit.jupiter.api.Test;
import ru.netology.ClientHandler;

import java.net.Socket;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class ServerTest {

    @Test
    public void testClientHandlerDoesNotThrow() {
        assertDoesNotThrow(() -> {
            Socket fakeSocket = new Socket(); // пустой сокет для теста
            ClientHandler handler = new ClientHandler(fakeSocket);
            new Thread(handler).start();
        });
    }
}