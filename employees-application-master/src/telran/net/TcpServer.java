package telran.net;
import java.io.IOException;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
// V.R. It looks OK.
public class TcpServer implements Runnable {
	private int port;
	private ApplProtocol protocol;
	private ServerSocket serverSocket;
	private ExecutorService executor;
	private int N_THREADS = 3;
	public TcpServer(int port, ApplProtocol protocol) throws Exception{
		this.port = port;
		this.protocol = protocol;
		serverSocket = new ServerSocket(port);
		executor = Executors.newFixedThreadPool(N_THREADS);
	}
	@Override
	public void run() {
		System.out.println("Server is listening on the port " + port);
		while (true) {
			try {
				Socket socket = serverSocket.accept();
				TcpClientServer client = new TcpClientServer(socket, protocol);
				executor.execute(client);
			} catch (Exception e) {
				e.printStackTrace();
				break;
			}
		}
		// V.R. Not bad place for shutdown. Really!
		executor.shutdown();
	}

}
