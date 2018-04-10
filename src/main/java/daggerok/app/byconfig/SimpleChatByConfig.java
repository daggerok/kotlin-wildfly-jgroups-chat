package daggerok.app.byconfig;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jgroups.JChannel;
import org.jgroups.Message;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.URL;
import java.util.Objects;

@Slf4j
//@Startup
//@Singleton
public class SimpleChatByConfig {

  private JChannel channel;

  @SneakyThrows
  @PostConstruct
  public void init() {

    final ClassLoader classLoader = Objects.requireNonNull(SimpleChatByConfig.class.getClassLoader());
    final URL config = Objects.requireNonNull(classLoader.getResource("/conf/udp.xml"));

    channel = new JChannel(config);

    channel.setReceiver(msg -> {
      log.info("{}: {}", msg.getSrc(), msg.getObject());
    });
    channel.connect("ChatCluster");
  }

  @SneakyThrows
  public void send(final String message) {

    final String state = channel.getState();

    channel.send(new Message(null, message));
    log.info("state: {}", state);
  }

  @PreDestroy
  public void bye() {
    if (channel != null) channel.close();
  }
}
