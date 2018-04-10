package daggerok.app.bydefault;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jgroups.JChannel;
import org.jgroups.Message;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.net.URL;
import java.util.Objects;

@Slf4j
//@Startup
//@Singleton
public class SimpleChatByDefault {

  private JChannel channel;

  @SneakyThrows
  @PostConstruct
  public void init() {

    channel = new JChannel();
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
