package daggerok.app.byconnect;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jgroups.JChannel;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.net.URL;
import java.util.Objects;

@Slf4j
//@Startup
//@Singleton
public class ByConnectChatReceiver {

  private JChannel channel;

  @SneakyThrows
  @PostConstruct
  public void initReceiver() {

    final ClassLoader classLoader = Objects.requireNonNull(ByConnectChatReceiver.class.getClassLoader());
    final URL config = Objects.requireNonNull(classLoader.getResource("/conf/udp.xml"));

    channel = new JChannel(config);
    channel.setReceiver(msg -> {
      log.info("{}: {}", msg.getSrc(), msg.getObject());
    });
    channel.connect("ChatCluster");
  }

  @PreDestroy
  public void bye() {
    if (null != channel) channel.close();
  }
}
