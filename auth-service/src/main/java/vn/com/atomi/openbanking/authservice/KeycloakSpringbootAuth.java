package vn.com.atomi.openbanking.authservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

@SpringBootApplication
@Slf4j
public class KeycloakSpringbootAuth implements CommandLineRunner {
	@Value("${server.port}")
	private int serverPort;
	public static void main(String[] args) {
		SpringApplication.run(KeycloakSpringbootAuth.class, args);
	}
	@Override
	public void run(String... arg) throws Exception {
		log.info("========================================= RUNNING PORT ==============================================");
		log.info(String.valueOf(serverPort));
		log.info("========================================= SWAGGER ===================================================");
		String ip;
		try(final DatagramSocket socket = new DatagramSocket()){
			socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
			ip = socket.getLocalAddress().getHostAddress();
		} catch (SocketException | UnknownHostException e) {
			throw new RuntimeException(e);
		}
		log.info("http://{}:{}/swagger-ui/index.html", ip, serverPort);
	}

}
