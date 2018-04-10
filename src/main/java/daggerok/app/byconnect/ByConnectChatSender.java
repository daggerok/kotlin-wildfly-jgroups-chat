package daggerok.app.byconnect;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jgroups.JChannel;
import org.jgroups.Message;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.net.URL;
import java.util.Objects;

@Slf4j
//@Startup
//@Singleton
public class ByConnectChatSender {

  final static ClassLoader classLoader = Objects.requireNonNull(ByConnectChatSender.class.getClassLoader());
  final static URL config = Objects.requireNonNull(classLoader.getResource("/conf/udp.xml"));

  @SneakyThrows
  public void send(final String message) {

    final JChannel channel = new JChannel(config);

    channel.connect("ChatCluster");
    channel.send(new Message(null, message));
    channel.close();
  }
}
